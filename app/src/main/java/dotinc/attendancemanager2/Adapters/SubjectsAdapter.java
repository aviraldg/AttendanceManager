package dotinc.attendancemanager2.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import dotinc.attendancemanager2.R;

/**
 * Created by vellapanti on 17/1/16.
 */
public class SubjectsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<String> arrayList;
    LayoutInflater inflater;
    public SubjectsAdapter(Context context, ArrayList<String> arrayList){
        this.context=context;
        this.arrayList=arrayList;
        inflater=LayoutInflater.from(context);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.custom_subjects,parent,false);
        SubjectsViewHolder viewHolder = new SubjectsViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SubjectsViewHolder viewHolder= (SubjectsViewHolder)holder;
        viewHolder.sub_name.setText(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    static class SubjectsViewHolder extends RecyclerView.ViewHolder{
        TextView sub_name;
        public SubjectsViewHolder(View itemView) {
            super(itemView);
            sub_name= (TextView) itemView.findViewById(R.id.subject);
        }
    }
}
