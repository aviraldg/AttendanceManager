package dotinc.attendancemanager2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateChangedListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GoToDateOldActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private MaterialCalendarView calendarView;
    private SimpleDateFormat format;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_to_date_old);
        instantiate();
        calendarView.setOnDateChangedListener(new OnDateChangedListener() {
            @Override
            public void onDateChanged(MaterialCalendarView widget, CalendarDay date) {
                int day = date.getDay();
                int month = date.getMonth()+1;
                int year = date.getYear();
                String myDate = day + "-" + month + "-" + year;
                Date dateNew = new Date();
                try {
                    dateNew = format.parse(myDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String day_selected = new SimpleDateFormat("EE").format(dateNew);
                Intent intent = new Intent(GoToDateOldActivity.this, GoToDateActivity.class);
                intent.putExtra("date", myDate);
                intent.putExtra("day_name", day_selected);
                startActivity(intent);

            }
        });
    }

    private void instantiate() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Go to Date");
        format= new SimpleDateFormat("d-M-yyyy");
        calendarView= (MaterialCalendarView) findViewById(R.id.calendarView);
        Date dateSelected=new Date();
        calendarView.setMaximumDate(dateSelected);
    }



}
