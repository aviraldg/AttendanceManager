package dotinc.attendancemanager2;

import android.graphics.Typeface;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import dotinc.attendancemanager2.Adapters.MainPageAdapter;
import dotinc.attendancemanager2.Objects.TimeTableList;
import dotinc.attendancemanager2.Utils.AttendanceDatabase;
import dotinc.attendancemanager2.Utils.Helper;
import dotinc.attendancemanager2.Utils.SubjectDatabase;
import dotinc.attendancemanager2.Utils.TimeTableDatabase;

public class ExtraClassActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private Date date;
    private String day;
    private int dayCode;
    private CoordinatorLayout root;
    private AttendanceDatabase database;
    private TimeTableDatabase timeTableDatabase;
    private TimeTableList timeTableList;
    private SubjectDatabase subjectDatabase;

    private RelativeLayout extraEmptyView;
    private TextView extraEmptyTitle;


    private MainPageAdapter exadapter;
    private ArrayList<TimeTableList> arrayList;
    private ArrayList<TimeTableList> allSubjectsArrayList;

    private void instantiate() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("d-M-yyyy");
        day = format.format(date);


        recyclerView = (RecyclerView) findViewById(R.id.extra_subjects);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        extraEmptyView = (RelativeLayout) findViewById(R.id.empty_view_extra);
        extraEmptyTitle = (TextView) findViewById(R.id.empty_text_extra);
        extraEmptyTitle.setTypeface(Typeface.createFromAsset(getAssets(), Helper.OXYGEN_BOLD));


        database = new AttendanceDatabase(this);
        timeTableDatabase = new TimeTableDatabase(this);
        timeTableList = new TimeTableList();
        subjectDatabase = new SubjectDatabase(this);
        root = (CoordinatorLayout) findViewById(R.id.root);
        arrayList = timeTableDatabase.getSubjects(timeTableList);
        dayCode = getdaycode();
        extraClass();
        if (allSubjectsArrayList.size() == 0)
            extraEmptyView.setVisibility(View.VISIBLE);
        else
            extraEmptyView.setVisibility(View.INVISIBLE);

    }

    private int getdaycode() {
        int day_code = 1;
        Date date = new Date();
        String myDate;
        SimpleDateFormat format = new SimpleDateFormat("EEE");
        myDate = format.format(date.getTime());
        switch (myDate) {
            case "Mon":
                day_code = 1;
                setTitle(getResources().getString(R.string.monday));
                break;
            case "Tue":
                day_code = 2;
                setTitle(getResources().getString(R.string.tuesday));
                break;
            case "Wed":
                day_code = 3;
                setTitle(getResources().getString(R.string.wednesday));
                break;
            case "Thu":
                day_code = 4;
                setTitle(getResources().getString(R.string.thursday));
                break;
            case "Fri":
                setTitle(getResources().getString(R.string.friday));
                day_code = 5;
                break;
            case "Sat":
                day_code = 6;
                setTitle(getResources().getString(R.string.saturday));
                break;
            case "Sun":
                setTitle(getResources().getString(R.string.sunday));
                break;
        }
        return day_code;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra_class);
        instantiate();
        extraClass();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.go_to_date_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }

    private void extraClass() {
        timeTableList.setDayCode(dayCode);
        arrayList = timeTableDatabase.getSubjects(timeTableList);
        allSubjectsArrayList = subjectDatabase.getAllSubjectsForExtra();
        for (int i = 0; i < arrayList.size(); i++) {
            for (int j = 0; j < allSubjectsArrayList.size(); j++) {
                if ((allSubjectsArrayList.get(j).getSubjectName().equals(arrayList.get(i).getSubjectName())))
                    allSubjectsArrayList.remove(j);
            }
        }
        exadapter = new MainPageAdapter(this, allSubjectsArrayList, day, "ExtraClassActivity");
        recyclerView.setAdapter(exadapter);
    }
    public void showSnackbar(String message) {
        Snackbar.make(root, message, Snackbar.LENGTH_SHORT).show();
    }
}
