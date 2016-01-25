package dotinc.attendancemanager2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import dotinc.attendancemanager2.Adapters.AttendanceAdapter;
import dotinc.attendancemanager2.Objects.TimeTableList;
import dotinc.attendancemanager2.Utils.AttendanceDatabase;
import dotinc.attendancemanager2.Utils.TimeTableDatabase;

public class AttendanceActivity extends AppCompatActivity {
    private RecyclerView view;
    private ArrayList<TimeTableList> arrayList;
    private Toolbar toolbar;
    private TextView day;
    AttendanceAdapter adapter;
    AttendanceDatabase database;
    TimeTableDatabase timeTableDatabase;
    TimeTableList timeTableList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        instantiate();

    }

    private void timetableDisplay() {
        Date date = new Date();
        String myDate;
        SimpleDateFormat format = new SimpleDateFormat("EEE");
        myDate = format.format(date.getTime());
        switch (myDate) {
            case "Mon":
                day.setText("Monday");
                getSubjects(1);
                break;

            case "Tue":
                day.setText("Tuesday");
                getSubjects(2);
                break;
            case "Wed":
                day.setText("Wednesday");
                getSubjects(3);
                break;
            case "Thu":
                day.setText("Thursday");
                getSubjects(4);
                break;
            case "Fri":
                day.setText("Friday");
                getSubjects(5);
                break;
            case "Sat":
                day.setText("Saturday");
                getSubjects(6);
                break;
        }
    }

    private void getSubjects(int dayCode) {
        timeTableList.setDayCode(dayCode);
        arrayList = timeTableDatabase.getSubjects(timeTableList);
    }

    private void instantiate() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        day = (TextView) findViewById(R.id.day);
        timeTableList = new TimeTableList();
        database = new AttendanceDatabase(this);
        timeTableDatabase = new TimeTableDatabase(this);
        arrayList = new ArrayList<>();
        timetableDisplay();
        view = (RecyclerView) findViewById(R.id.days_subjects);
        view.setHasFixedSize(true);
        view.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AttendanceAdapter(this, arrayList);
        view.setAdapter(adapter);

    }
}
