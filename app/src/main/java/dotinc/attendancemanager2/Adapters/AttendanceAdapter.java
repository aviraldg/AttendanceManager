package dotinc.attendancemanager2.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import dotinc.attendancemanager2.Objects.AttendanceList;
import dotinc.attendancemanager2.Objects.TimeTableList;
import dotinc.attendancemanager2.R;
import dotinc.attendancemanager2.Utils.AttendanceDatabase;
import sun.bob.mcalendarview.mCalendarView;

/**
 * Created by vellapanti on 21/1/16.
 */
public class AttendanceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<TimeTableList> arrayList;
    ArrayList<AttendanceList> attendanceObject;
    LayoutInflater inflater;
    AttendanceDatabase database;
    AttendanceList attendanceList;
    String myDate;

    public AttendanceAdapter(Context context, ArrayList<TimeTableList> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        inflater = LayoutInflater.from(context);
        attendanceList = new AttendanceList();
        database = new AttendanceDatabase(context);
        attendanceObject = new ArrayList<>();
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        myDate = format.format(date);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.custom_daywise_subjects, parent, false);
        AttendanceViewHolder viewHolder = new AttendanceViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final AttendanceViewHolder viewHolder = (AttendanceViewHolder) holder;
        viewHolder.subject.setText(arrayList.get(position).getSubjectName());
        viewHolder.subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("ADD");
                builder.setPositiveButton("Attended", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addAttendance(1,position);
                        database.addAttendance(attendanceList);
                    }
                });
                builder.setNegativeButton("Bunked", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addAttendance(0,position);
                        int i = database.totalBunked(attendanceList);
                        Log.d("option_va", String.valueOf(i));
                    }
                });
                builder.create().show();

            }
        });
        viewHolder.subject.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder calendar = new AlertDialog.Builder(context);
                View view = LayoutInflater.from(context).inflate(R.layout.calendarexample, null);
                calendar.setView(view);
                attendanceObject=database.getDates(attendanceList);
                for (int i = 0 ;i<attendanceObject.size();i++){

                }
                viewHolder.calendarView.
                        calendar.create().show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    private void addAttendance(int action , int position){
        attendanceList.setId(arrayList.get(position).getId());
        attendanceList.setPosition(position);
        attendanceList.setAction(action);
        attendanceList.setDate(myDate);
        attendanceObject.add(attendanceList);
        database.addAttendance(attendanceList);
    }
    static class AttendanceViewHolder extends RecyclerView.ViewHolder {
        private TextView subject;
        private mCalendarView calendarView;

        public AttendanceViewHolder(View itemView) {
            super(itemView);
            subject = (TextView) itemView.findViewById(R.id.daywise_subject);
            calendarView = (mCalendarView) itemView.findViewById(R.id.calendar);

        }
    }
}
