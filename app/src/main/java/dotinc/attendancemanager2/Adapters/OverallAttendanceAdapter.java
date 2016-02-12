package dotinc.attendancemanager2.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;

import java.util.ArrayList;

import dotinc.attendancemanager2.Objects.AttendanceList;
import dotinc.attendancemanager2.Objects.SubjectsList;
import dotinc.attendancemanager2.R;
import dotinc.attendancemanager2.Utils.AttendanceDatabase;

/**
 * Created by vellapanti on 8/2/16.
 */
public class OverallAttendanceAdapter extends RecyclerView.Adapter<OverallAttendanceAdapter.OverallViewHolder> {

    private Context context;
    private ArrayList<SubjectsList> arrayList;
    private ArrayList<AttendanceList> attendanceObject;
    private LayoutInflater inflater;
    private AttendanceDatabase database;

    public OverallAttendanceAdapter(Context context, ArrayList<SubjectsList> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        inflater = LayoutInflater.from(context);
        database = new AttendanceDatabase(context);
    }

    @Override
    public OverallViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_main_row, parent, false);
        OverallViewHolder viewHolder = new OverallViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(OverallViewHolder viewHolder, int position) {
        final int id = arrayList.get(position).getId();
        int attendedClasses = database.totalPresent(id);
        int totalClasses = database.totalClasses(id);
        viewHolder.checkMark.setVisibility(View.GONE);

        float percentage = ((float) attendedClasses / (float) totalClasses) * 100;
        classesNeeded(attendedClasses, totalClasses, percentage, viewHolder);
        viewHolder.subject.setText(arrayList.get(position).getSubjectName());
        viewHolder.attended.setText("Attended: " + attendedClasses);
        viewHolder.total.setText("Total: " + totalClasses);
        viewHolder.subject_percentage.setText(" " +
                String.format("%.1f", percentage));

        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, null);
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, null);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    private void classesNeeded(int attendedClass, int totalClass, float percentage, OverallViewHolder viewHolder) {
        int flag = 0;
        int originalAttended = attendedClass;
        if (percentage >= 75)
            flag = 1;
        else if (attendedClass == 0 && totalClass == 0)
            flag = 2;
        while (percentage < 75) {
            flag = 3;
            attendedClass++;
            totalClass++;
            percentage = ((float) attendedClass / (float) totalClass) * 100;
        }
        switch (flag) {
            case 1:
                viewHolder.needClassDetail.setVisibility(View.VISIBLE);
                viewHolder.needClassDetail.setText("You are on track !");
                break;

            case 2:
                viewHolder.needClassDetail.setVisibility(View.INVISIBLE);
                break;

            case 3:
                viewHolder.needClassDetail.setVisibility(View.VISIBLE);
                viewHolder.needClassDetail.setText("You need " + (attendedClass - originalAttended) +
                        " more classes");
                break;
        }

    }

    static class OverallViewHolder extends RecyclerView.ViewHolder {
        private TextView subject;
        private TextView attended;
        private TextView total;
        private TextView subject_percentage;
        private TextView needClassDetail;
        private SwipeLayout swipeLayout;
        private ImageView checkMark;

        public OverallViewHolder(View itemView) {
            super(itemView);
            subject = (TextView) itemView.findViewById(R.id.subject_name);
            attended = (TextView) itemView.findViewById(R.id.attended);
            total = (TextView) itemView.findViewById(R.id.total);
            subject_percentage = (TextView) itemView.findViewById(R.id.sub_perc);
            needClassDetail = (TextView) itemView.findViewById(R.id.sub_detail);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            checkMark = (ImageView) itemView.findViewById(R.id.check_mark);
        }
    }
}
