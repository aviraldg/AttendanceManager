package dotinc.attendancemanager2.Adapters;

import android.content.ContentValues;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import dotinc.attendancemanager2.R;
import dotinc.attendancemanager2.Utils.Helper;

/**
 * Created by vellapanti on 16/2/16.
 */
public class HelpAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<String> topicList;
    private ArrayList<String> descriptionList;
    private LayoutInflater inflater;

    public HelpAdapter(Context context, ArrayList<String> topicList, ArrayList<String> descriptionList) {
        this.context = context;
        this.topicList = topicList;
        this.descriptionList = descriptionList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_helper,parent, false);
        HelperViewHolder viewHolder = new HelperViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final HelperViewHolder viewHolder = (HelperViewHolder) holder;
        viewHolder.topic.setText(topicList.get(position));
        viewHolder.description.setText(descriptionList.get(position));
    }

    @Override
    public int getItemCount() {
        return topicList.size();
    }

    static class HelperViewHolder extends RecyclerView.ViewHolder {
        TextView topic;
        TextView description;
        public HelperViewHolder(View itemView) {
            super(itemView);
            topic= (TextView) itemView.findViewById(R.id.topic);
            description= (TextView) itemView.findViewById(R.id.description);

        }
    }
}
