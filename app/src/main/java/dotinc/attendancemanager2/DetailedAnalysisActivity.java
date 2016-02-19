package dotinc.attendancemanager2;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import dotinc.attendancemanager2.Adapters.CustomSpinnerAdapter;
import dotinc.attendancemanager2.Objects.AttendanceList;
import dotinc.attendancemanager2.Objects.SubjectsList;
import dotinc.attendancemanager2.Utils.AttendanceDatabase;
import dotinc.attendancemanager2.Utils.Helper;
import dotinc.attendancemanager2.Utils.SubjectDatabase;

public class DetailedAnalysisActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Spinner spinnerNav;
    private LinearLayout root;
    private Context context;

    private TextView totClass, attClass, bunkedClass, presentView, absentView, noClassView;

    private ArrayList<String> subjects;
    private ArrayList<SubjectsList> subjectsLists;
    private SubjectDatabase subjectDatabase;
    private ArrayList<AttendanceList> attendanceObject;
    private AttendanceDatabase attendancedb;
    private SimpleDateFormat formatter;
    private int subId;
    private CaldroidFragment caldroidFragment;


    private void instantiate() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        spinnerNav = (Spinner) findViewById(R.id.spinner_nav);
        root = (LinearLayout) findViewById(R.id.display_root);

        context = DetailedAnalysisActivity.this;
        subjectDatabase = new SubjectDatabase(context);
        attendancedb = new AttendanceDatabase(context);
        subjectsLists = subjectDatabase.getAllSubjects();
        formatter = new SimpleDateFormat("d-M-yyyy");
        subjects = new ArrayList<>();
        totClass = (TextView) findViewById(R.id.tot_class);
        totClass.setTypeface(Typeface.createFromAsset(getAssets(), Helper.JOSEFIN_SANS_REGULAR));
        totClass.setText("Click on date for attendance");
        bunkedClass = (TextView) findViewById(R.id.bunk_class);
        bunkedClass.setTypeface(Typeface.createFromAsset(getAssets(), Helper.JOSEFIN_SANS_REGULAR));
        bunkedClass.setVisibility(View.GONE);
        attClass = (TextView) findViewById(R.id.att_class);
        attClass.setTypeface(Typeface.createFromAsset(getAssets(), Helper.JOSEFIN_SANS_REGULAR));
        attClass.setVisibility(View.GONE);
        presentView = (TextView) findViewById(R.id.present_view_text);
        presentView.setTypeface(Typeface.createFromAsset(getAssets(), Helper.OXYGEN_BOLD));
        absentView = (TextView) findViewById(R.id.absent_view_text);
        absentView.setTypeface(Typeface.createFromAsset(getAssets(), Helper.OXYGEN_BOLD));
        noClassView = (TextView) findViewById(R.id.no_class_text);
        noClassView.setTypeface(Typeface.createFromAsset(getAssets(), Helper.OXYGEN_BOLD));


        for (int pos = 0; pos < subjectsLists.size(); pos++)
            subjects.add(subjectsLists.get(pos).getSubjectName());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_analysis);

        instantiate();
        addItemsToSpinner();

    }

    public void addItemsToSpinner() {

        spinnerNav.setAdapter(new CustomSpinnerAdapter(this, subjects));
        spinnerNav.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapter, View v,
                                       int position, long id) {
                fetchFromDatabase(subjectsLists.get(position).getId());
                subId = subjectsLists.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

    }

    private void setCustomResourceForDates() {
        Date greenDate;

        for (int i = 0; i < attendanceObject.size(); i++) {
            try {
                String date = attendanceObject.get(i).getDate();
                Log.d("option_date",date);
                greenDate = formatter.parse(date);
                if (attendanceObject.get(i).getAction() == 1) {
                    caldroidFragment.setBackgroundResourceForDate(R.color.attendedColor,
                            greenDate);
                    caldroidFragment.setTextColorForDate(R.color.white, greenDate);
                } else if (attendanceObject.get(i).getAction() == 0) {
                    caldroidFragment.setBackgroundResourceForDate(R.color.absentColor,
                            greenDate);
                    caldroidFragment.setTextColorForDate(R.color.white, greenDate);
                } else if (attendanceObject.get(i).getAction() == -1) {
                    caldroidFragment.setBackgroundResourceForDate(R.color.noClassColor, greenDate);
                    caldroidFragment.setTextColorForDate(R.color.white, greenDate);
                }
            } catch (ParseException e) {
                e.printStackTrace();
                Log.d("option_error_detailed", e.toString());
            }
        }
    }

    private void fetchFromDatabase(int id) {
        attendanceObject = attendancedb.getAllDates(id);
        setUpCalendar();
    }

    private void setUpCalendar() {


        caldroidFragment = new CaldroidFragment();
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
        args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);
        caldroidFragment.setArguments(args);
        caldroidFragment.setMaxDate(new Date());
        setCustomResourceForDates();
        customize();
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.display_root, caldroidFragment);
        t.commit();

    }

    private void customize() {
        final CaldroidListener listener = new CaldroidListener() {
            @Override
            public void onSelectDate(Date date, View view) {
                String myDate = formatter.format(date);
                totClass.setText("Total Lectures:" + attendancedb.totalDayWiseClasses(subId, myDate));
                attClass.setVisibility(View.VISIBLE);
                attClass.setText("Attended Lectures:" + attendancedb.totalDayWisePresent(subId, myDate));
                bunkedClass.setVisibility(View.VISIBLE);
                bunkedClass.setText("Bunked Lectures:" + attendancedb.totalDayWiseBunked(subId, myDate));
            }

            @Override
            public void onCaldroidViewCreated() {
                super.onCaldroidViewCreated();

                Button leftButton = caldroidFragment.getLeftArrowButton();
                Button rightButton = caldroidFragment.getRightArrowButton();
                TextView textView = caldroidFragment.getMonthTitleTextView();

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    leftButton.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorPrimaryDark));
                    rightButton.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorPrimaryDark));
                    textView.setTextColor(ContextCompat.getColorStateList(context, R.color.colorPrimaryDark));
                }
                textView.setTypeface(Typeface.createFromAsset(getAssets(), Helper.OXYGEN_BOLD));
            }
        };
        caldroidFragment.setCaldroidListener(listener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.go_to_date_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
