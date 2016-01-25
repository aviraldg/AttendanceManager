
//package dotinc.attendancemanager2;
//
//import android.app.AlertDialog;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.Toolbar;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.ImageButton;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//
//import dotinc.attendancemanager2.Adapters.TimeTableAdapter;
//import dotinc.attendancemanager2.Objects.SubjectsList;
//import dotinc.attendancemanager2.Objects.TimeTableList;
//import dotinc.attendancemanager2.Utils.SubjectDatabase;
//import dotinc.attendancemanager2.Utils.TimeTableDatabase;
//
//public class TimeTableActivity extends AppCompatActivity {
//    private Toolbar toolbar;
//    private RecyclerView view;
//    private TextView day;
//    private FloatingActionButton add_subject;
//    private ImageButton next;
//    private ArrayAdapter<String> arrayAdapter;
//    private ArrayList<SubjectsList> subjectsNameList;
//    private int day_code;
//    ArrayList<String> subjects;
//    ArrayList<TimeTableList> arrayList;
//    TimeTableAdapter adapter;
//    SubjectDatabase subjectDatabase;
//    TimeTableList timeTableList;
//    TimeTableDatabase database;
//    SubjectsList subjectsList;
//
//
//    private void instantiate() {
//        day_code = 1;
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        timeTableList = new TimeTableList();
//        database = new TimeTableDatabase(this);
//        subjectsList = new SubjectsList();
//        arrayList = new ArrayList<>();
//        subjects = new ArrayList<>();
//        subjectsNameList = new ArrayList<>();
//        subjectDatabase = new SubjectDatabase(this);
//        add_subject = (FloatingActionButton) findViewById(R.id.add_subjects);
//        view = (RecyclerView) findViewById(R.id.timetable);
//        next = (ImageButton) findViewById(R.id.next_day);
//        day = (TextView) findViewById(R.id.day);
//        view.setHasFixedSize(true);
//        view.setLayoutManager(new LinearLayoutManager(this));
//        timeTableList.setDayCode(day_code);
//        arrayList = database.getSubjects(timeTableList);
//        //adapter = new TimeTableAdapter(this, arrayList, timeTableList);
//        //view.setAdapter(adapter);
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_time_table);
//        instantiate();
//        add_subject.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addTimetable();
//            }
//        });
//
//        next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                updateDayCode();
//            }
//        });
//    }
//
//    private void updateDayCode() {
//        day_code++;
//        switch (day_code) {
//            case 1:
//                day.setText("Monday");
//                updateList(day_code);
//                break;
//            case 2:
//                day.setText("Tuesday");
//                updateList(day_code);
//                break;
//            case 3:
//                day.setText("Wednesday");
//                updateList(day_code);
//                break;
//            case 4:
//                day.setText("Thursday");
//                updateList(day_code);
//                break;
//            case 5:
//                day.setText("Friday");
//                updateList(day_code);
//                break;
//            case 6:
//                day.setText("Saturday");
//                updateList(day_code);
//                break;
//            case 7:
//                Intent in = new Intent(this, AttendanceActivity.class);
//                startActivity(in);
//        }
//
//    }
//
//    private void updateList(int dayCode) {
//        arrayList.clear();
//        timeTableList.setDayCode(dayCode);
//        arrayList = database.getSubjects(timeTableList);
//        // adapter = new TimeTableAdapter(this, arrayList, timeTableList);
//        view.setAdapter(adapter);
//    }
//
//    private void addTimetable() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(TimeTableActivity.this);
//        LayoutInflater layoutInflater = getLayoutInflater();
//        View view = layoutInflater.inflate(R.layout.subjects_list, null);
//        ListView subjects_view = (ListView) view.findViewById(R.id.subjectList);
//        subjectsNameList.clear();
//        subjectsNameList = subjectDatabase.getAllSubjects();
//        subjects.clear();
//        for (int i = 0; i < subjectsNameList.size(); i++)
//            subjects.add(subjectsNameList.get(i).getSubjectName().toString());
//
//        arrayAdapter = new ArrayAdapter<>(TimeTableActivity.this, android.R.layout.simple_list_item_1, subjects);
//        subjects_view.setAdapter(arrayAdapter);
//        builder.setView(view);
//        subjects_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                addSubjectToTimetable(position);
//            }
//        });
//        builder.create().show();
//    }
//
//    private void addSubjectToTimetable(int position) {
//        String subjectSelected = subjects.get(position).toString();
//        int id = subjectsNameList.get(position).getId();
//        timeTableList.setId(id);
//        timeTableList.setDayCode(day_code);
//        timeTableList.setPosition(arrayList.size());
//        timeTableList.setSubjectName(subjectSelected);
//        database.addTimeTable(timeTableList);
//        arrayList = database.getSubjects(timeTableList);
//        //database.toast();
//        adapter.notifyDataSetChanged();
//    }
//}