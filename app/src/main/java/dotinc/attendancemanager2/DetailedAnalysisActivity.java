package dotinc.attendancemanager2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import dotinc.attendancemanager2.Objects.AttendanceList;
import dotinc.attendancemanager2.Utils.AttendanceDatabase;
import sun.bob.mcalendarview.MarkStyle;
import sun.bob.mcalendarview.listeners.OnMonthChangeListener;
import sun.bob.mcalendarview.mCalendarView;

public class DetailedAnalysisActivity extends AppCompatActivity {
    ArrayList<AttendanceList> attendanceObject;
    AttendanceDatabase database;
    String[] date;
    int[] flag;
    mCalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendarexample);
        instantiate();
        addSubjects();
        calendarView.setOnMonthChangeListener(new OnMonthChangeListener() {
            @Override
            public void onMonthChange(int i, int i1) {
                addSubjects();
            }
        });
    }

    private void instantiate() {
        attendanceObject = new ArrayList<>();
        attendanceObject.clear();
        database = new AttendanceDatabase(this);
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        Log.d("option_id", String.valueOf(id));
        calendarView = (mCalendarView) findViewById(R.id.calendarview);
        attendanceObject = database.getDates(id);
        Log.d("option_size", String.valueOf(attendanceObject.size()));
        date = new String[3];
        flag = new int[3];
        addSubjects();
    }

    private void addSubjects() {
        for (int i = 0; i < attendanceObject.size(); i++) {
            String string = attendanceObject.get(i).getDate();
            date = string.split("-");
            for (int j = 0; j < 3; j++)
                flag[j] = Integer.parseInt(date[j]);
            Log.d("option_dates", String.valueOf(attendanceObject.get(i).getAction()));
            if(attendanceObject.get(i).getAction()==1) {
                calendarView.setMarkedStyle(MarkStyle.LEFTSIDEBAR, getResources().getColor(R.color.colorPrimaryDark));
                calendarView.markDate(flag[0], flag[1], flag[2]);
            }
            else {
                Log.d("option_el", "inside");
                Log.d("option_date",flag[0]+" "+flag[1]+" "+flag[2]);
                calendarView.setMarkedStyle(MarkStyle.LEFTSIDEBAR, getResources().getColor(R.color.absentColor));
                calendarView.markDate(flag[0], flag[1], flag[2]);
            }
            calendarView.setBackgroundColor(getResources().getColor(R.color.backgroundColor));

        }

    }
}
