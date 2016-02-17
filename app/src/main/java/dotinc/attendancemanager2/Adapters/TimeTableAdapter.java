package dotinc.attendancemanager2.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;

import java.util.ArrayList;

import dotinc.attendancemanager2.Fragements.WeeklySubjectsFragment;
import dotinc.attendancemanager2.Objects.SubjectsList;
import dotinc.attendancemanager2.Objects.TimeTableList;
import dotinc.attendancemanager2.R;
import dotinc.attendancemanager2.Utils.Helper;
import dotinc.attendancemanager2.Utils.TimeTableDatabase;
import dotinc.attendancemanager2.WeeklySubjectsActivity;

/**
 * Created by vellapanti on 18/1/16.
 */

//Adapter of WeeklySubjectsActivity*******************//

public class TimeTableAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<TimeTableList> arrayList;
    private ArrayList<SubjectsList> subjectsLists;
    private TimeTableDatabase database;
    private TimeTableList timeTableList;
    private int view_timetable;
    private int pageNumber;
    private WeeklySubjectsFragment fragment;

    public TimeTableAdapter
            (Context context, ArrayList<TimeTableList> arrayList, TimeTableList timeTableList,
             WeeklySubjectsFragment fragment, int view_timetable, int pageNumber) {
        this.context = context;
        this.timeTableList = timeTableList;
        this.fragment = fragment;
        this.arrayList = arrayList;
        this.view_timetable = view_timetable;
        this.pageNumber = pageNumber;
        inflater = LayoutInflater.from(context);
        database = new TimeTableDatabase(context);
        subjectsLists = new ArrayList<>();
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
        viewHolder.subject.setTypeface(Typeface.createFromAsset(context.getAssets(), Helper.JOSEFIN_SANS_BOLD));

        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        if (view_timetable == 0) {
            viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, viewHolder.swipeLayout.findViewById(R.id.right_swipe));
            viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, null);
        } else {
            viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, null);
            viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, null);
        }


        viewHolder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {

            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onClose(SwipeLayout layout) {

            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

            }
        });

        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteSubject(position);
            }
        });

    }

    private void deleteSubject(int position) {
        TimeTableList timeTableList = new TimeTableList();
        timeTableList.setDayCode(pageNumber);
        timeTableList.setSubjectName(arrayList.get(position).getSubjectName());
        timeTableList.setId(arrayList.get(position).getId());
        timeTableList.setPosition(arrayList.get(position).getPosition());
        database.deleteTimeTable(timeTableList);
        String deleted_subject =arrayList.get(position).getSubjectName();
        arrayList.remove(position);
        this.notifyDataSetChanged();
        ((WeeklySubjectsActivity)context).showSnackbar(deleted_subject+" Deleted");
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    static class TimeTableViewHolder extends RecyclerView.ViewHolder {

        private TextView subject;
        private SwipeLayout swipeLayout;
        private ImageButton delete;

        public TimeTableViewHolder(View itemView) {
            super(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            subject = (TextView) itemView.findViewById(R.id.timetable_subject);
            delete = (ImageButton) itemView.findViewById(R.id.delete_subject);
        }
    }
}
