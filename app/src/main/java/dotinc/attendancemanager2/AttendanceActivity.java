//package dotinc.attendancemanager2;
//
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.Toolbar;
//import android.util.Log;
//import android.widget.TextView;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//
//import dotinc.attendancemanager2.Adapters.AttendanceAdapter;
//import dotinc.attendancemanager2.Objects.SubjectsList;
//import dotinc.attendancemanager2.Objects.TimeTableList;
//import dotinc.attendancemanager2.Utils.AttendanceDatabase;
//import dotinc.attendancemanager2.Utils.SubjectDatabase;
//import dotinc.attendancemanager2.Utils.TimeTableDatabase;
//
//public class AttendanceActivity extends AppCompatActivity {
//    private RecyclerView view;
//    private ArrayList<TimeTableList> arrayList;
//    private Toolbar toolbar;
//    private TextView day;
//    private ArrayList<TimeTableList> allSubjectsArrayList;      //add
//    AttendanceAdapter adapter;
//    AttendanceDatabase database;
//    TimeTableDatabase timeTableDatabase;
//    TimeTableList timeTableList;
//    SubjectDatabase subjectDatabase;                            //add
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_attendance);
//        instantiate();
//
//    }
//
//    private void timetableDisplay() {
//        Date date = new Date();
//        String myDate;
//        SimpleDateFormat format = new SimpleDateFormat("EEE");
//        myDate = format.format(date.getTime());
//        switch (myDate) {
//            case "Mon":
//                day.setText("Monday");
//                getSubjects(1);
//                break;
//
//            case "Tue":
//                day.setText("Tuesday");
//                getSubjects(2);
//                break;
//            case "Wed":
//                day.setText("Wednesday");
//                getSubjects(3);
//                break;
//            case "Thu":
//                day.setText("Thursday");
//                getSubjects(4);
//                break;
//            case "Fri":
//                day.setText("Friday");
//                getSubjects(5);
//                break;
//            case "Sat":
//                day.setText("Saturday");
//                getSubjects(6);
//                break;
//        }
//    }
//
//    private void getSubjects(int dayCode) {
//        timeTableList.setDayCode(dayCode);
//        arrayList = timeTableDatabase.getSubjects(timeTableList);
//    }
//
//    private void extraClass() {
//        timeTableList.setDayCode(6);                //daycode
//        arrayList = timeTableDatabase.getSubjects(timeTableList);
//        allSubjectsArrayList=subjectDatabase.getAllSubjectsForExtra();
//        for (int i =0;i<arrayList.size();i++)
//            Log.d("option_arl", arrayList.get(i).getSubjectName());
//        for (int i =0;i<allSubjectsArrayList.size();i++)
//            Log.d("option_all",allSubjectsArrayList.get(i).getSubjectName());
//
//        for (int i = 0; i < arrayList.size(); i++) {
//            for (int j=0;j<allSubjectsArrayList.size();j++){
//                if ((allSubjectsArrayList.get(j).getSubjectName().equals(arrayList.get(i).getSubjectName())))
//                    allSubjectsArrayList.remove(j);
//            }
//
//        }
//        for (int i =0;i<allSubjectsArrayList.size();i++)
//            Log.d("option_extra",allSubjectsArrayList.get(i).getSubjectName());
//
//    }
//
//    private void instantiate() {
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        day = (TextView) findViewById(R.id.day);
//        timeTableList = new TimeTableList();
//        database = new AttendanceDatabase(this);
//        timeTableDatabase = new TimeTableDatabase(this);
//        arrayList = new ArrayList<>();
//        timetableDisplay();
//        view = (RecyclerView) findViewById(R.id.days_subjects);
//        view.setHasFixedSize(true);
//        view.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new AttendanceAdapter(this, arrayList,);
//        view.setAdapter(adapter);
//        allSubjectsArrayList = new ArrayList<>();               //add
//        subjectDatabase = new SubjectDatabase(this);            //add
//        extraClass();
//    }
//}
