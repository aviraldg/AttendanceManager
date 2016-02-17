package dotinc.attendancemanager2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import dotinc.attendancemanager2.Adapters.WeeklySubjectsAdapter;
import dotinc.attendancemanager2.Fragements.WeeklySubjectsFragment;
import dotinc.attendancemanager2.Objects.SubjectsList;
import dotinc.attendancemanager2.Objects.TimeTableList;
import dotinc.attendancemanager2.Utils.Helper;
import dotinc.attendancemanager2.Utils.SubjectDatabase;
import dotinc.attendancemanager2.Utils.TimeTableDatabase;

public class WeeklySubjectsActivity extends AppCompatActivity {

    private Context context;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView title, helpText;
    private FloatingActionButton fab;
    private View view1;

    private String[] tabTitles;
    private ArrayList<Fragment> fragments;
    private ArrayList<TimeTableList> arrayList;
    private ArrayList<SubjectsList> subjectsNameList;
    private ArrayList<String> subjects;
    private Typeface oxyBold, josefinReg;

    private SubjectDatabase subjectDatabase;
    private TimeTableDatabase database;
    private WeeklySubjectsAdapter pagerAdapter;
    private int timetableFlag;
    private int view_timetable = 0;
    private static int pageNumber = 1;
    private Boolean fromSettings;
    private CoordinatorLayout main_layout;
    void instantiate() {
        context = WeeklySubjectsActivity.this;
        oxyBold = Typeface.createFromAsset(getAssets(), Helper.OXYGEN_BOLD);
        josefinReg = Typeface.createFromAsset(getAssets(), Helper.JOSEFIN_SANS_REGULAR);
        Intent intent = getIntent();
        view_timetable = intent.getIntExtra("view_timetable", 0);
        fromSettings = intent.getBooleanExtra("Settings", false);
        title = (TextView) findViewById(R.id.page_title);
        title.setTypeface(oxyBold);
        helpText = (TextView) findViewById(R.id.help_text);
        helpText.setTypeface(josefinReg);
        fab = (FloatingActionButton) findViewById(R.id.add_subjects);
        fab.hide();
        view1 = findViewById(R.id.view1);

        if (view_timetable == 1) {
            title.setText(getResources().getString(R.string.timetable_activity));
            helpText.setVisibility(View.GONE);
            view1.setVisibility(View.GONE);
        } else
            title.setText(getResources().getString(R.string.weekly_subjects));

        timetableFlag = intent.getIntExtra("timetableFlag", 0);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.pager);
        main_layout= (CoordinatorLayout) findViewById(R.id.main_content);
        fab = (FloatingActionButton) findViewById(R.id.add_subjects);

        fragments = new ArrayList<>();
        for (int i = 0; i < 7; i++)
            fragments.add(new WeeklySubjectsFragment());

        tabTitles = getResources().getStringArray(R.array.tabs);
        pagerAdapter =
                new WeeklySubjectsAdapter(getSupportFragmentManager(), fragments, tabTitles, view_timetable);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setTabsFromPagerAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new PageListener());

        subjectsNameList = new ArrayList<>();
        subjectDatabase = new SubjectDatabase(this);
        database = new TimeTableDatabase(this);
        subjects = new ArrayList<>();
        arrayList = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_subjects);
        instantiate();
        if (view_timetable == 1)
            fab.setVisibility(View.INVISIBLE);
        else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    fab.show();
                }
            }, 500);
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTimetable();
            }
        });
    }

    public void doneAddTimetable(View view) {
        if (fromSettings || view_timetable == 1)
            finish();
        else {
            if (view_timetable != 1)
                Helper.saveToPref(context, Helper.COMPLETED, "completed");
            startActivity(new Intent(WeeklySubjectsActivity.this,
                    MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            finish();
        }
    }

    private void addTimetable() {
        AlertDialog.Builder builder = new AlertDialog.Builder(WeeklySubjectsActivity.this);
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.subjects_list, null);
        ListView subjects_view = (ListView) view.findViewById(R.id.subjectList);
        subjectsNameList.clear();
        subjectsNameList = subjectDatabase.getAllSubjects();
        subjects.clear();
        for (int i = 0; i < subjectsNameList.size(); i++)
            subjects.add(subjectsNameList.get(i).getSubjectName().toString());

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(WeeklySubjectsActivity.this, android.R.layout.simple_list_item_1, subjects);
        subjects_view.setAdapter(arrayAdapter);
        builder.setView(view);
        subjects_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                addSubjectToTimetable(position);
            }
        });
        builder.create().show();
    }

    private void addSubjectToTimetable(int position) {
        String subjectSelected = subjects.get(position).toString();
        int id = subjectsNameList.get(position).getId();
        TimeTableList timeTableList = new TimeTableList();
        timeTableList.setId(id);
        timeTableList.setDayCode(pageNumber);
        timeTableList.setSubjectName(subjectSelected);
        arrayList = database.getSubjects(timeTableList);
        timeTableList.setPosition(arrayList.size());
        database.addTimeTable(timeTableList);
        //database.toast();
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(pageNumber - 1);
        showSnackbar(subjectSelected+" Added");
    }
    public void showSnackbar(String message){
        Snackbar.make(main_layout,message,Snackbar.LENGTH_SHORT).show();
    }
    private class PageListener extends ViewPager.SimpleOnPageChangeListener {
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            pageNumber = position + 1;
            arrayList.clear();
        }
    }
}
