package dotinc.attendancemanager2;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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

public class DetailedAnalysis extends AppCompatActivity {

    private boolean undo = false;
    private CaldroidFragment caldroidFragment;
    private CaldroidFragment dialogCaldroidFragment;
    private Spinner spinner;
    private Toolbar toolbar;
    ArrayList<AttendanceList> attendanceObject;
    AttendanceDatabase database;
    SubjectDatabase subjectDatabase;
    private void setCustomResourceForDates() {
        Date greenDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        for (int i = 0; i < attendanceObject.size(); i++) {
            try {
                String date = attendanceObject.get(i).getDate();
                greenDate = formatter.parse(date);
                if(attendanceObject.get(i).getAction()==1){
                    caldroidFragment.setBackgroundResourceForDate(R.color.colorPrimary,
                            greenDate);
                    caldroidFragment.setTextColorForDate(R.color.backgroundColor, greenDate);
                }
                else if (attendanceObject.get(i).getAction()==0){
                    caldroidFragment.setBackgroundResourceForDate(R.color.absentColor,
                            greenDate);
                    caldroidFragment.setTextColorForDate(R.color.backgroundColor, greenDate);
                }
                Log.d("option_date",date);
                Log.d("option_date", String.valueOf(attendanceObject.get(i).getAction()));

            } catch (ParseException e) {

                e.printStackTrace();
                Log.d("option_da", e.toString());
            }
        }
    }

    private void instantiate() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        database = new AttendanceDatabase(this);
        subjectDatabase = new SubjectDatabase(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        attendanceObject = new ArrayList<>();
        attendanceObject.clear();
        attendanceObject = database.getAllDates(id);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_analysis);
        instantiate();
        caldroidFragment = new CaldroidFragment();
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
        args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);
        caldroidFragment.setArguments(args);
        setCustomResourceForDates();
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendar1, caldroidFragment);
        t.commit();
    }
}
