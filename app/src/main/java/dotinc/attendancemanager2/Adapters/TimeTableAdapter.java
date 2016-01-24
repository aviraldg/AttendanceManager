package dotinc.attendancemanager2.Adapters;

import android.content.Context;
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
import dotinc.attendancemanager2.Utils.TimeTableDatabase;

/**
 * Created by vellapanti on 18/1/16.
 */
public class TimeTableAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    LayoutInflater inflater;
    ArrayList<TimeTableList> arrayList;
    ArrayList<SubjectsList> subjectsLists;
    TimeTableDatabase database;
    TimeTableList timeTableList;
    private WeeklySubjectsFragment fragment;

    public TimeTableAdapter
            (Context context, ArrayList<TimeTableList> arrayList, TimeTableList timeTableList, WeeklySubjectsFragment fragment) {
        this.context = context;
        this.arrayList = arrayList;
        inflater = LayoutInflater.from(context);
        database = new TimeTableDatabase(context);
        this.timeTableList = timeTableList;
        this.fragment = fragment;
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

        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, viewHolder.swipeLayout.findViewById(R.id.right_swipe));
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, null);

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
                fragment.deleteItem(position);
            }
        });
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
