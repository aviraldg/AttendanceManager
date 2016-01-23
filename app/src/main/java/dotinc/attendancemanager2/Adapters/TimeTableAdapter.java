package dotinc.attendancemanager2.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.sql.Time;
import java.util.ArrayList;

import dotinc.attendancemanager2.Objects.TimeTableList;
import dotinc.attendancemanager2.R;
import dotinc.attendancemanager2.Utils.TimeTableDatabase;

/**
 * Created by vellapanti on 18/1/16.
 */
public class TimeTableAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    LayoutInflater inflater;
    ArrayList<TimeTableList> arrayList;
    TimeTableDatabase database;
    TimeTableList timeTableList;

    public TimeTableAdapter(Context context, ArrayList<TimeTableList> arrayList, TimeTableList timeTableList) {
        this.context = context;
        this.arrayList = arrayList;
        inflater = LayoutInflater.from(context);
        database = new TimeTableDatabase(context);
        this.timeTableList = timeTableList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_timetable, parent, false);
        TimeTableViewHolder viewHolder = new TimeTableViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final TimeTableViewHolder viewHolder = (TimeTableViewHolder) holder;
        viewHolder.subject.setText(arrayList.get(position).getSubjectName());

        viewHolder.subject.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
               // database.toast();
                timeTableList.setSubjectName(viewHolder.subject.getText().toString());
                timeTableList.setId(arrayList.get(position).getId());
                timeTableList.setPosition(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("DELETE");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        database.deleteTimeTable(timeTableList);
                        //database.toast();
                    }
                });
                builder.create().show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    static class TimeTableViewHolder extends RecyclerView.ViewHolder {
        private TextView subject;

        public TimeTableViewHolder(View itemView) {

            super(itemView);
            subject = (TextView) itemView.findViewById(R.id.timetable_subject);
        }
    }
}
