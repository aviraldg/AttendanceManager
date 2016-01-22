package dotinc.attendancemanager2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import dotinc.attendancemanager2.Adapters.AttendanceAdapter;
import dotinc.attendancemanager2.Objects.TimeTableList;
import dotinc.attendancemanager2.Utils.AttendanceDatabase;
import dotinc.attendancemanager2.Utils.TimeTableDatabase;

public class AttendanceActivity extends AppCompatActivity {
    private RecyclerView view;
    private ArrayList<TimeTableList> arrayList;
    private Toolbar toolbar;
    AttendanceAdapter adapter;
    AttendanceDatabase database;
    TimeTableDatabase timeTableDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
    }

}
