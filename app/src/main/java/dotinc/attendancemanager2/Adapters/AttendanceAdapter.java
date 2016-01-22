package dotinc.attendancemanager2.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;

import dotinc.attendancemanager2.Objects.TimeTableList;
import dotinc.attendancemanager2.R;

/**
 * Created by vellapanti on 21/1/16.
 */
public class AttendanceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<TimeTableList> arrayList;
    LayoutInflater inflater;

    public AttendanceAdapter(Context context, ArrayList<TimeTableList> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.custom_daywise_subjects, parent, false);
        AttendanceViewHolder viewHolder = new AttendanceViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AttendanceViewHolder viewHolder = (AttendanceViewHolder) holder;
        viewHolder.subject.setText(arrayList.get(position).getSubjectName());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    static class AttendanceViewHolder extends RecyclerView.ViewHolder {
        private TextView subject;

        public AttendanceViewHolder(View itemView) {
            super(itemView);
            subject = (TextView) itemView.findViewById(R.id.daywise_subject);

        }
    }
}
