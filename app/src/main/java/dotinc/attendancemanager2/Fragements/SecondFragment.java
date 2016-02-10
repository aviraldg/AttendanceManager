package dotinc.attendancemanager2.Fragements;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import dotinc.attendancemanager2.GoToDateActivity;
import dotinc.attendancemanager2.OverallAttendanceActivity;
import dotinc.attendancemanager2.R;
import dotinc.attendancemanager2.WeeklySubjectsActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SecondFragment extends Fragment {
    private ImageButton viewTimeTable;
    private ImageButton go_to_date;
    private ImageButton overall_attendance;
    int year, month, day;
    SimpleDateFormat formatter;
    Date myDate;
    Calendar calendar;

    public SecondFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        viewTimeTable = (ImageButton) view.findViewById(R.id.view_timetable);
        go_to_date = (ImageButton) view.findViewById(R.id.go_to_date);
        overall_attendance = (ImageButton) view.findViewById(R.id.complete_attendance);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        formatter = new SimpleDateFormat("dd-MM-yyyy");
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewTimeTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WeeklySubjectsActivity.class);
                intent.putExtra("view_timetable", 1);
                startActivity(intent);
            }
        });
        go_to_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), R.style.DialogTheme, pickerListener, year, month, day);
                dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                dialog.show();
            }
        });
        overall_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OverallAttendanceActivity.class);
                startActivity(intent);
            }
        });
    }

    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            int day = selectedDay;
            int month = selectedMonth + 1;
            int year = selectedYear;
            String date = day + "-" + month + "-" + year;
            try {
                myDate = formatter.parse(date);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            String day_selected = new SimpleDateFormat("EE").format(myDate);
            Intent intent = new Intent(getActivity(), GoToDateActivity.class);
            intent.putExtra("date", date);
            Log.d("Options_date", date + "\n" + day_selected);
            intent.putExtra("day_name", day_selected);
            startActivity(intent);
        }
    };
}
