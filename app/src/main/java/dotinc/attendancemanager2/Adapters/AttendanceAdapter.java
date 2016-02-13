package dotinc.attendancemanager2.Adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;

import java.util.ArrayList;git
import dotinc.attendancemanager2.ExtraClassActivity;
import dotinc.attendancemanager2.GoToDateActivity;
import dotinc.attendancemanager2.MainActivity;
import dotinc.attendancemanager2.Objects.AttendanceList;
import dotinc.attendancemanager2.Objects.TimeTableList;
import dotinc.attendancemanager2.R;
import dotinc.attendancemanager2.Utils.AttendanceDatabase;
import dotinc.attendancemanager2.Utils.Helper;

//import dotinc.attendancemanager2.GraphActivity;

/**
 * Created by vellapanti on 21/1/16.
 */

//**********Adapter of MainActivity**************//

public class AttendanceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<TimeTableList> arrayList;
    private ArrayList<AttendanceList> attendanceObject;
    private LayoutInflater inflater;
    private AttendanceDatabase database;
    private AttendanceList attendanceList;
    private String myDate;
    private TimeTableList list;
    private String activityName;
    private int attendance_criteria;
    private int markerValue;
    private int lastPosition = -1;

    public AttendanceAdapter(Context context, ArrayList<TimeTableList> arrayList, String myDate, String activityName) {
        this.context = context;
        this.arrayList = arrayList;
        this.myDate = myDate;
        this.activityName = activityName;
        inflater = LayoutInflater.from(context);
        attendanceList = new AttendanceList();
        database = new AttendanceDatabase(context);
        attendanceObject = new ArrayList<>();
        list = new TimeTableList();
        markerValue = 2;
        attendance_criteria = Integer.parseInt(Helper.getFromPref(context, Helper.ATTENDANCE_CRITERIA, String.valueOf(0)));
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
        final int id = arrayList.get(position).getId();

        list.setId(id);
        attendanceList.setDate(myDate);
        markerValue = database.setMarker(myDate, position,id);
        if (markerValue == 1) {
            viewHolder.check_mark.setImageResource(R.mipmap.ic_check_circle_black_36dp);
            viewHolder.check_mark.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary));
        } else if (markerValue == 0) {
            viewHolder.check_mark.setImageResource(R.mipmap.ic_check_circle_black_36dp);
            viewHolder.check_mark.setColorFilter(ContextCompat.getColor(context, R.color.absentColor));
        } else {
            viewHolder.check_mark.setImageResource(R.mipmap.ic_check_circle_black_36dp);
            viewHolder.check_mark.setColorFilter(ContextCompat.getColor(context, R.color.backgroundColor));
        }
        markerValue = 2;
        int attendedClasses = database.totalPresent(id);
        int totalClasses = database.totalClasses(id);

        float percentage = ((float) attendedClasses / (float) totalClasses) * 100;
        classesNeeded(attendedClasses, totalClasses, percentage, viewHolder);


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
        viewHolder.attended.setText(context.getResources().getString(R.string.attended) + ": " + attendedClasses);
        viewHolder.total.setText(context.getResources().getString(R.string.total) + ": " + totalClasses);
        viewHolder.subject_percentage.setText(" " +
                String.format("%.1f", percentage));

        setAnimation(viewHolder.swipeLayout, position);

        viewHolder.attendedbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAttendance(1, position, context.getResources().getString(R.string.attended_message));
                markerValue = database.setMarker(myDate, position,id);

            }
        });

        viewHolder.bunkedbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAttendance(0, position, context.getResources().getString(R.string.bunked_message));
                markerValue = database.setMarker(myDate, position,id);


            }
        });

        viewHolder.noClassbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAttendance(-1, position, context.getResources().getString(R.string.noclass_message));
            }
        });

        viewHolder.resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetAttendance(id, viewHolder, position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    private void classesNeeded(int attendedClass, int totalClass, float percentage, AttendanceViewHolder viewHolder) {
        int flag = 0;
        int originalAttended = attendedClass;
        if (percentage >= attendance_criteria) {
            flag = 1;
            viewHolder.subject_percentage.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark
            ));
        } else if (attendedClass == 0 && totalClass == 0)
            flag = 2;
        while (percentage < attendance_criteria) {
            flag = 3;
            attendedClass++;
            totalClass++;
            viewHolder.subject_percentage.setTextColor(context.getResources().getColor(R.color.absentColor));
            percentage = ((float) attendedClass / (float) totalClass) * 100;
        }
        switch (flag) {
            case 1:
                viewHolder.needClassDetail.setVisibility(View.VISIBLE);
                viewHolder.needClassDetail.setText(context.getResources().getString(R.string.on_track_message));
                break;

            case 2:
                viewHolder.needClassDetail.setVisibility(View.INVISIBLE);
                break;

            case 3:
                int need = 0;
                viewHolder.needClassDetail.setVisibility(View.VISIBLE);
                need = attendedClass - originalAttended;
                if (need == 1)
                    viewHolder.needClassDetail.setText(Html.fromHtml("Attend next <b><font color='#E64A19'>" + need + "</font></b> class"));
                else
                    viewHolder.needClassDetail.setText(Html.fromHtml("Attend next <b><font color='#E64A19'>" + need + "</font></b> classes"));
                break;
        }

    }

    private void showSnackBar(String message) {


        if (activityName.equals("MainActivity")) {

            ((MainActivity) context).showSnackbar(message);
            ((MainActivity) context).updateOverallPerc();
        }
        else if (activityName.equals("ExtraClassActivity"))
            ((ExtraClassActivity) context).showSnackbar(message);
        else
            ((GoToDateActivity) context).showSnackbar(message);
    }

    private void resetAttendance(int id, AttendanceViewHolder viewHolder, int position) {
        database.resetAttendance(id, myDate, position);
        viewHolder.resetbtn.setImageResource(R.mipmap.ic_restore_black_36dp);
        this.notifyDataSetChanged();
        showSnackBar(context.getResources().getString(R.string.reset_attendance));
    }

    private void addAttendance(int action, int position, String message) {
        attendanceList.setId(arrayList.get(position).getId());
        attendanceList.setPosition(position);
        attendanceList.setAction(action);
        attendanceList.setDate(myDate);
        attendanceObject.add(attendanceList);
        database.addAttendance(attendanceList);
        this.notifyDataSetChanged();
        showSnackBar(message);

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
        private TextView needClassDetail;
        private SwipeLayout swipeLayout;
        private ImageButton attendedbtn;
        private ImageButton bunkedbtn;
        private ImageButton noClassbtn;
        private ImageButton resetbtn;
        private ImageView check_mark;
        private CardView cardView;

        public AttendanceViewHolder(View itemView) {
            super(itemView);
            subject = (TextView) itemView.findViewById(R.id.subject_name);
            attended = (TextView) itemView.findViewById(R.id.attended);
            total = (TextView) itemView.findViewById(R.id.total);
            subject_percentage = (TextView) itemView.findViewById(R.id.sub_perc);
            needClassDetail = (TextView) itemView.findViewById(R.id.sub_detail);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            attendedbtn = (ImageButton) itemView.findViewById(R.id.attended_class);
            bunkedbtn = (ImageButton) itemView.findViewById(R.id.bunk_class);
            noClassbtn = (ImageButton) itemView.findViewById(R.id.no_class);
            resetbtn = (ImageButton) itemView.findViewById(R.id.reset_attendance);
            check_mark = (ImageView) itemView.findViewById(R.id.check_mark);
            cardView = (CardView) itemView.findViewById(R.id.root_card);
        }
    }
}
