package dotinc.attendancemanager2.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import dotinc.attendancemanager2.DetailedAnalysisActivity;
import dotinc.attendancemanager2.MainActivity;
import dotinc.attendancemanager2.Objects.AttendanceList;
import dotinc.attendancemanager2.Objects.TimeTableList;
import dotinc.attendancemanager2.R;
import dotinc.attendancemanager2.Utils.AttendanceDatabase;

/**
 * Created by vellapanti on 21/1/16.
 */

//**********Adapter of MainActivity**************//

public class AttendanceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    ArrayList<TimeTableList> arrayList;
    ArrayList<AttendanceList> attendanceObject;
    LayoutInflater inflater;
    AttendanceDatabase database;
    AttendanceList attendanceList;
    String myDate;
    TimeTableList list;
    private int lastPosition = -1;

    public AttendanceAdapter(Context context, ArrayList<TimeTableList> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        inflater = LayoutInflater.from(context);
        attendanceList = new AttendanceList();
        database = new AttendanceDatabase(context);
        attendanceObject = new ArrayList<>();
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        myDate = format.format(date);
        list = new TimeTableList();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.custom_main_row, parent, false);
        AttendanceViewHolder viewHolder = new AttendanceViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final AttendanceViewHolder viewHolder = (AttendanceViewHolder) holder;
        int id = arrayList.get(position).getId();
        list.setId(id);
        attendanceList.setDate(myDate);
        attendanceObject = database.getMarker(list, myDate);
        if (attendanceObject.size() != 0 && attendanceObject.get(0).getAction() == 1)
            setMarker(1, viewHolder);

        else if (attendanceObject.size() != 0 && attendanceObject.get(0).getAction() == 0)
            setMarker(0, viewHolder);

        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right,
                viewHolder.swipeLayout.findViewById(R.id.bottom_wrapper));
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left,
                viewHolder.swipeLayout.findViewById(R.id.bottom_wrapper1));
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


        viewHolder.subject.setText(arrayList.get(position).getSubjectName());
        viewHolder.attended.setText("Attended: " + database.totalPresent(id));
        viewHolder.total.setText("Total: " + database.totalClasses(id));
//      viewHolder.subject_percentage.setText(" "+ new Float(database.totalPresent(id) / database.totalClasses(id)) * 100);

        setAnimation(viewHolder.swipeLayout, position);

        viewHolder.attendedbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addAttendance(1, position, "Attended Class");
                setMarker(1, viewHolder);


            }
        });

        viewHolder.bunkedbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAttendance(0, position, "Bunked Class");
                setMarker(0, viewHolder);

            }
        });

        viewHolder.noClassbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAttendance(-1, position, "No Class");
            }
        });
        viewHolder.subject.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(context, DetailedAnalysisActivity.class);
                context.startActivity(intent);
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    private void setMarker(int marker, AttendanceViewHolder viewHolder) {
        switch (marker) {
            case 0:
                viewHolder.check_mark.setVisibility(View.VISIBLE);
                viewHolder.check_mark.setImageResource(R.mipmap.ic_highlight_off_black_36dp);
                viewHolder.check_mark.setColorFilter(ContextCompat.getColor(context, android.R.color.holo_red_light));
                break;
            case 1:
                viewHolder.check_mark.setVisibility(View.VISIBLE);
                viewHolder.check_mark.setImageResource(R.mipmap.ic_check_circle_black_36dp);
                viewHolder.check_mark.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary));
                break;
        }
    }

    private void addAttendance(int action, int position, String message) {
        attendanceList.setId(arrayList.get(position).getId());
        attendanceList.setPosition(position);
        attendanceList.setAction(action);
        attendanceList.setDate(myDate);
        attendanceObject.add(attendanceList);
        database.addAttendance(attendanceList);
        this.notifyDataSetChanged();
        ((MainActivity) context).showSnackbar(message);

    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    static class AttendanceViewHolder extends RecyclerView.ViewHolder {
        private TextView subject;
        private TextView attended;
        private TextView total;
        private TextView subject_percentage;
        private SwipeLayout swipeLayout;
        private ImageButton attendedbtn;
        private ImageButton bunkedbtn;
        private ImageButton noClassbtn;
        private ImageView check_mark;

        public AttendanceViewHolder(View itemView) {
            super(itemView);
            subject = (TextView) itemView.findViewById(R.id.subject_name);
            attended = (TextView) itemView.findViewById(R.id.attended);
            total = (TextView) itemView.findViewById(R.id.total);
            subject_percentage = (TextView) itemView.findViewById(R.id.sub_perc);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            attendedbtn = (ImageButton) itemView.findViewById(R.id.attended_class);
            bunkedbtn = (ImageButton) itemView.findViewById(R.id.bunk_class);
            noClassbtn = (ImageButton) itemView.findViewById(R.id.no_class);
            check_mark = (ImageView) itemView.findViewById(R.id.check_mark);
        }
    }
}
