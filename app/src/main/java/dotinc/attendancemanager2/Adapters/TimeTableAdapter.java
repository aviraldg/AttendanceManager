package dotinc.attendancemanager2.Adapters;

import android.content.Context;
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

/**
 * Created by vellapanti on 18/1/16.
 */
public class TimeTableAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    LayoutInflater inflater;
    ArrayList<TimeTableList> arrayList;

    public TimeTableAdapter(Context context,ArrayList<TimeTableList> arrayList){
        this.context=context;
        this.arrayList=arrayList;
        Log.d("option_Arr",arrayList.get(0).getSubjectName());
        inflater=LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_timetable,parent,false);
        TimeTableViewHolder viewHolder = new TimeTableViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TimeTableViewHolder viewHolder=(TimeTableViewHolder)holder;
        viewHolder.subject.setText(arrayList.get(position).getSubjectName());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    static class TimeTableViewHolder extends RecyclerView.ViewHolder{
        private TextView subject;
        public TimeTableViewHolder(View itemView) {

            super(itemView);
            subject= (TextView) itemView.findViewById(R.id.timetable_subject);
        }
    }
}
