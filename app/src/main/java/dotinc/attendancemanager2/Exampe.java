package dotinc.attendancemanager2;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.roomorama.caldroid.CaldroidFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import dotinc.attendancemanager2.Objects.AttendanceList;
import dotinc.attendancemanager2.Objects.SubjectsList;
import dotinc.attendancemanager2.Utils.AttendanceDatabase;
import dotinc.attendancemanager2.Utils.SubjectDatabase;

//public class Exampe extends AppCompatActivity {
//
//    private Toolbar toolbar;
//    private Spinner spinnerNav;
//    private LinearLayout root;
//    private Context context;
//
//    private ArrayList<String> subjects;
//    private ArrayList<SubjectsList> subjectsLists;
//    private SubjectDatabase subjectDatabase;
//    ArrayList<AttendanceList> attendanceObject;
//    private AttendanceDatabase attendancedb;
//    private SimpleDateFormat formatter;
//
//    private CaldroidFragment caldroidFragment;
//
//
//    private void instantiate() {
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        spinnerNav = (Spinner) findViewById(R.id.spinner_nav);
//        root = (LinearLayout) findViewById(R.id.display_root);
//
//        context = Exampe.this;
//        subjectDatabase = new SubjectDatabase(context);
//        attendancedb = new AttendanceDatabase(context);
//        subjectsLists = subjectDatabase.getAllSubjects();
//        formatter = new SimpleDateFormat("dd-MM-yyyy");
//        subjects = new ArrayList<>();
//
//        for (int pos = 0; pos < subjectsLists.size(); pos++)
//            subjects.add(subjectsLists.get(pos).getSubjectName());
//
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_exampe);
//
//        instantiate();
//        addItemsToSpinner();
//
//    }
//
//    public void addItemsToSpinner() {
//
//        spinnerNav.setAdapter(new CustomSpinnerAdapter(this, subjects));
//        spinnerNav.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//            @Override
//            public void onItemSelected(AdapterView<?> adapter, View v,
//                                       int position, long id) {
//                fetchFromDatabase(subjectsLists.get(position).getId());
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> arg0) {
//            }
//        });
//
//    }
//
//    private void setCustomResourceForDates() {
//        Date greenDate = new Date();
//
//        for (int i = 0; i < attendanceObject.size(); i++) {
//            try {
//                String date = attendanceObject.get(i).getDate();
//                greenDate = formatter.parse(date);
//                if (attendanceObject.get(i).getAction() == 1) {
//                    caldroidFragment.setBackgroundResourceForDate(R.color.colorPrimary,
//                            greenDate);
//                    caldroidFragment.setTextColorForDate(R.color.backgroundColor, greenDate);
//                } else if (attendanceObject.get(i).getAction() == 0) {
//                    caldroidFragment.setBackgroundResourceForDate(R.color.absentColor,
//                            greenDate);
//                    caldroidFragment.setTextColorForDate(R.color.backgroundColor, greenDate);
//                } else {
////--------------for no class , color not decided yet--------------------//
////                    caldroidFragment.setBackgroundResourceForDate(R.color.absentColor,
////                            greenDate);
////                    caldroidFragment.setTextColorForDate(R.color.backgroundColor, greenDate);
//                }
////                Log.d("option_date",date);
////                Log.d("option_date", String.valueOf(attendanceObject.get(i).getAction()));
//
//            } catch (ParseException e) {
//
//                e.printStackTrace();
//                Log.d("option_da", e.toString());
//            }
//        }
//    }
//
//    private void fetchFromDatabase(int id) {
//        attendanceObject = attendancedb.getAllDates(id);
//        setUpCalendar();
//    }
//
//    private void setUpCalendar() {
//
//        caldroidFragment = new CaldroidFragment();
//        Bundle args = new Bundle();
//        Calendar cal = Calendar.getInstance();
//        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
//        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
//        args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
//        args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);
//        caldroidFragment.setArguments(args);
//        setCustomResourceForDates();
//        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
//        t.replace(R.id.display_root, caldroidFragment);
//        t.commit();
//
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.go_to_date_menu, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        if (item.getItemId() == android.R.id.home)
//            finish();
//        return super.onOptionsItemSelected(item);
//    }
//}
//
//
//
//
