package dotinc.attendancemanager2.Fragements;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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
import dotinc.attendancemanager2.GoToDateOldActivity;
import dotinc.attendancemanager2.MainActivity;
import dotinc.attendancemanager2.OverallAttendanceActivity;
import dotinc.attendancemanager2.PredictorActivity;
import dotinc.attendancemanager2.R;
import dotinc.attendancemanager2.WeeklySubjectsActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SecondFragment extends Fragment {
    private ImageButton viewTimeTable;
    private ImageButton go_to_date;
    private ImageButton overall_attendance;
    private ImageButton predictor;
    private FragmentActivity fragmentActivity;
    int year, month, day;
    SimpleDateFormat formatter;
    Date myDate;
    Calendar calendar;

    public SecondFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        fragmentActivity=(FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        viewTimeTable = (ImageButton) view.findViewById(R.id.view_timetable);
        go_to_date = (ImageButton) view.findViewById(R.id.go_to_date);
        overall_attendance = (ImageButton) view.findViewById(R.id.complete_attendance);
        predictor= (ImageButton) view.findViewById(R.id.predictor);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        formatter = new SimpleDateFormat("d-M-yyyy");
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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    DatePickerDialog dialog = new DatePickerDialog(getActivity(), R.style.DialogTheme, pickerListener, year, month, day);
                    dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                    dialog.show();
                    }
                else{
                    Intent intent = new Intent(getActivity(), GoToDateOldActivity.class);
                    startActivity(intent);
                }

            }
        });
        overall_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OverallAttendanceActivity.class);
                startActivity(intent);
            }
        });
        predictor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PredictorActivity.class);
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
            intent.putExtra("day_name", day_selected);
            startActivity(intent);
        }
    };
}
