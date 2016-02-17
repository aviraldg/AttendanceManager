package dotinc.attendancemanager2.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;

import dotinc.attendancemanager2.Objects.SubjectsList;
import dotinc.attendancemanager2.R;
import dotinc.attendancemanager2.Utils.AttendanceDatabase;
import dotinc.attendancemanager2.Utils.Helper;

/**
 * Created by vellapanti on 16/2/16.
 */
public class PredictorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    int flag;
    int percentagePredicted;
    int attendedOrMiss;
    private LayoutInflater inflater;
    private ArrayList<SubjectsList> subjects;
    private AttendanceDatabase database;

    public PredictorAdapter(Context context, int flag, int attendedOrMiss, ArrayList<SubjectsList> subjects) {
        this.context = context;
        this.flag = flag;
        this.attendedOrMiss = attendedOrMiss;
        this.subjects = subjects;
        inflater = LayoutInflater.from(context);
        database = new AttendanceDatabase(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_predictor, parent, false);
        PredictorViewHolder viewHolder = new PredictorViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final PredictorViewHolder viewHolder = (PredictorViewHolder) holder;

        viewHolder.subject.setTypeface(Typeface.createFromAsset(context.getAssets(), Helper.OXYGEN_BOLD));
        viewHolder.currentPerc.setTypeface(Typeface.createFromAsset(context.getAssets(), Helper.JOSEFIN_SANS_BOLD));
        viewHolder.predictedPerc.setTypeface(Typeface.createFromAsset(context.getAssets(), Helper.JOSEFIN_SANS_BOLD));
        viewHolder.subject.setText(subjects.get(position).getSubjectName());

        final int id = subjects.get(position).getId();

        int attendedClasses = database.totalPresent(id);
        int totalClasses = database.totalClasses(id);
        float percentage = ((float) attendedClasses / (float) totalClasses) * 100;

        if (!Float.isNaN(percentage)) {
            BigDecimal decimal = new BigDecimal(String.format("%.1f", percentage));
            if (percentage >= Integer.parseInt(Helper.getFromPref(context, Helper.ATTENDANCE_CRITERIA, "0"))) {
                viewHolder.currentPerc.setTextColor(ContextCompat.getColor(context, R.color.attendedColor));
                viewHolder.currentPerc.setText(decimal.stripTrailingZeros().toPlainString() + "%");
            } else {
                viewHolder.currentPerc.setTextColor(ContextCompat.getColor(context, R.color.absentColor));
                viewHolder.currentPerc.setText(decimal.stripTrailingZeros().toPlainString() + "%");
            }
        } else {
            viewHolder.currentPerc.setText("0%");
        }


        if (flag == 1) {
            attendedClasses = attendedClasses + attendedOrMiss;
            totalClasses = totalClasses + attendedOrMiss;
            percentage = ((float) attendedClasses / (float) totalClasses) * 100;

            if (!Float.isNaN(percentage)) {
                BigDecimal decimal = new BigDecimal(String.format("%.1f", percentage));
                if (percentage >= Integer.parseInt(Helper.getFromPref(context, Helper.ATTENDANCE_CRITERIA, "0"))) {
                    viewHolder.predictedPerc.setTextColor(ContextCompat.getColor(context, R.color.attendedColor));
                    viewHolder.predictedPerc.setText(decimal.stripTrailingZeros().toPlainString() + "%");
                } else {
                    viewHolder.predictedPerc.setTextColor(ContextCompat.getColor(context, R.color.absentColor));
                    viewHolder.predictedPerc.setText(decimal.stripTrailingZeros().toPlainString() + "%");
                }
            } else {
                viewHolder.predictedPerc.setText("0%");
            }

        } else {
            totalClasses = totalClasses + attendedOrMiss;
            percentage = ((float) attendedClasses / (float) totalClasses) * 100;
            if (!Float.isNaN(percentage)) {
                BigDecimal decimal = new BigDecimal(String.format("%.1f", percentage));

                if (percentage >= Integer.parseInt(Helper.getFromPref(context, Helper.ATTENDANCE_CRITERIA, "0"))) {
                    viewHolder.predictedPerc.setTextColor(ContextCompat.getColor(context, R.color.attendedColor));
                    viewHolder.predictedPerc.setText(decimal.stripTrailingZeros().toPlainString() + "%");
                } else {
                    viewHolder.predictedPerc.setTextColor(ContextCompat.getColor(context, R.color.absentColor));
                    viewHolder.predictedPerc.setText(decimal.stripTrailingZeros().toPlainString() + "%");
                }


            } else {
                viewHolder.predictedPerc.setText("0%");
            }
        }


    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    static class PredictorViewHolder extends RecyclerView.ViewHolder {

        private TextView subject;
        private TextView currentPerc;
        private TextView predictedPerc;

        public PredictorViewHolder(View itemView) {
            super(itemView);
            subject = (TextView) itemView.findViewById(R.id.subject_name);
            currentPerc = (TextView) itemView.findViewById(R.id.current_percentage);
            predictedPerc = (TextView) itemView.findViewById(R.id.predicted_percentage);
        }
    }
}
