package dotinc.attendancemanager2;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import dotinc.attendancemanager2.Adapters.WeeklySubjectsAdapter;
import dotinc.attendancemanager2.Fragements.WeeklySubjectsFragment;
import dotinc.attendancemanager2.Objects.SubjectsList;
import dotinc.attendancemanager2.Objects.TimeTableList;
import dotinc.attendancemanager2.Utils.SubjectDatabase;
import dotinc.attendancemanager2.Utils.TimeTableDatabase;

public class WeeklySubjectsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FloatingActionButton fab;
    private ArrayList<Fragment> fragments;
    private String[] tabTitles;
    private ArrayList<TimeTableList> arrayList;

    private ArrayList<SubjectsList> subjectsNameList;
    private ArrayList<String> subjects;
    private SubjectDatabase subjectDatabase;
    private TimeTableDatabase database;

    private static int pageNumber = 1;

    void instantiate() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Weekly Subjects");

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.pager);
        fab = (FloatingActionButton) findViewById(R.id.add_subjects);

        fragments = new ArrayList<>();
        for (int i = 0; i < 6; i++)
            fragments.add(new WeeklySubjectsFragment());

        tabTitles = getResources().getStringArray(R.array.tabs);

        WeeklySubjectsAdapter pagerAdapter =
                new WeeklySubjectsAdapter(getSupportFragmentManager(), fragments, tabTitles);
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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTimetable();
            }
        });


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
        timeTableList.setPosition(arrayList.size());
        timeTableList.setSubjectName(subjectSelected);
        database.addTimeTable(timeTableList);
        arrayList = database.getSubjects(timeTableList);
        database.toast();
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
