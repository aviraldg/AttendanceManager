package dotinc.attendancemanager2;

import android.app.AlertDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import dotinc.attendancemanager2.Adapters.TimeTableAdapter;
import dotinc.attendancemanager2.Objects.SubjectsList;
import dotinc.attendancemanager2.Objects.TimeTableList;
import dotinc.attendancemanager2.Utils.SubjectDatabase;
import dotinc.attendancemanager2.Utils.TimeTableDatabase;

public class TimeTableActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView view;
    private TextView day;
    private FloatingActionButton add_subject;
    private ImageButton next;
    private ArrayAdapter<String> arrayAdapter;
    private  ArrayList<SubjectsList> subjectsListArrayList;
    ArrayList<String> subjects;
    ArrayList<TimeTableList> arrayList;
    TimeTableAdapter adapter;
    SubjectDatabase subjectDatabase;
    TimeTableList timeTableList;
    TimeTableDatabase database;
    SubjectsList subjectsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);
        instantiate();
        add_subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTimetable();
            }
        });
    }

    private void addTimetable() {
        AlertDialog.Builder builder = new AlertDialog.Builder(TimeTableActivity.this);
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.subjects_list, null);
        ListView subjects_view = (ListView) view.findViewById(R.id.subjectList);
        subjectsListArrayList = subjectDatabase.getAllSubjects();


        for (int i = 0; i < subjectsListArrayList.size(); i++)
            subjects.add(subjectsListArrayList.get(i).getSubjectName().toString());

        arrayAdapter = new ArrayAdapter<String>(TimeTableActivity.this, android.R.layout.simple_list_item_1, subjects);
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
        int id =subjectsListArrayList.get(position).getId();
        Log.d("option_position", String.valueOf(position));
        timeTableList.setId(id);
        timeTableList.setDayCode(0);
        timeTableList.setSubjectName(subjectSelected);
        database.addTimeTable(timeTableList);
        arrayList = database.getSubjects(timeTableList);
        database.toast();
//        for(int i =0 ;i<arrayList.size();i++)
//            Log.d("option_name",arrayList.get(i).getSubjectName());
        Log.d("option_timetablearr",arrayList.get(0).getSubjectName());
        adapter.notifyDataSetChanged();
    }

    private void instantiate() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        timeTableList = new TimeTableList();
        database = new TimeTableDatabase(this);
        subjectsList = new SubjectsList();
        add_subject = (FloatingActionButton) findViewById(R.id.add_subjects);
        view = (RecyclerView) findViewById(R.id.timetable);
        view.setHasFixedSize(true);
        view.setLayoutManager(new LinearLayoutManager(this));
        arrayList = new ArrayList<>();
        subjectDatabase = new SubjectDatabase(this);
        subjects = new ArrayList<String>();
        subjectsListArrayList= new ArrayList<>();
        arrayList = database.getSubjects(timeTableList);
        adapter = new TimeTableAdapter(this, arrayList);
        view.setAdapter(adapter);
    }
}
