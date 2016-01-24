package dotinc.attendancemanager2.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dotinc.attendancemanager2.R;

/**
 * Created by ddvlslyr on 24/1/16.
 */
public class WeeklyFragmentAdapter extends RecyclerView.Adapter<WeeklyFragmentAdapter.WeeklyViewHolder> {

    Context context;

    public WeeklyFragmentAdapter() {


    }

    @Override
    public WeeklyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_subjects, parent, false);
        return new WeeklyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WeeklyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class WeeklyViewHolder extends RecyclerView.ViewHolder {
        public WeeklyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
