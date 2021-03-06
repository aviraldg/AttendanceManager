package dotinc.attendancemanager2.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.daimajia.swipe.implments.SwipeItemRecyclerMangerImpl;

import java.util.ArrayList;

import dotinc.attendancemanager2.Objects.SubjectsList;
import dotinc.attendancemanager2.R;
import dotinc.attendancemanager2.SubjectsActivity;
import dotinc.attendancemanager2.Utils.AttendanceDatabase;
import dotinc.attendancemanager2.Utils.Helper;
import dotinc.attendancemanager2.Utils.SubjectDatabase;
import dotinc.attendancemanager2.Utils.TimeTableDatabase;

/**
 * Created by vellapanti on 17/1/16.
 */

//*********Adapter of Subjects activity************//

public class SubjectsAdapter extends RecyclerSwipeAdapter<SubjectsAdapter.SubjectsViewHolder> {

    private Context context;
    private ArrayList<SubjectsList> arrayList;
    private LayoutInflater inflater;
    private SubjectDatabase database;
    private EditText subject;
    private TimeTableDatabase timeTableDatabase;
    private AttendanceDatabase attendanceDatabase;
    protected SwipeItemRecyclerMangerImpl mItemManger;

    public SubjectsAdapter(Context context, ArrayList<SubjectsList> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        inflater = LayoutInflater.from(context);
        database = new SubjectDatabase(context);
        timeTableDatabase = new TimeTableDatabase(context);
        attendanceDatabase = new AttendanceDatabase(context);
        mItemManger = new SwipeItemRecyclerMangerImpl(this);
    }

    @Override
    public SubjectsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_subjects, parent, false);
        SubjectsViewHolder viewHolder = new SubjectsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SubjectsViewHolder viewHolder, final int position) {

        viewHolder.subName.setText(arrayList.get(position).getSubjectName());
        viewHolder.subName.setTypeface(Typeface.createFromAsset(context.getAssets(), Helper.JOSEFIN_SANS_BOLD));

        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, viewHolder.swipeLayout.findViewById(R.id.right_swipe));
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, viewHolder.swipeLayout.findViewById(R.id.left_swipe));

        mItemManger.bindView(viewHolder.itemView, position);


        viewHolder.delSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subject = arrayList.get(position).getSubjectName();
                database.deleteSubject(subject);
                timeTableDatabase.deleteSubject(arrayList.get(position).getId());
                attendanceDatabase.deleteSubjects(arrayList.get(position).getId());
                arrayList.remove(position);
                ((SubjectsActivity) context).setEmptyView(arrayList.size());
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, arrayList.size());
                mItemManger.closeAllItems();

            }
        });

        viewHolder.editSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View v = inflater.inflate(R.layout.add_subject, null);
                subject = (EditText) v.findViewById(R.id.subject_name);
                subject.setTypeface(Typeface.createFromAsset(context.getAssets(), Helper.JOSEFIN_SANS_BOLD));
                subject.setText(arrayList.get(position).getSubjectName());
                final String old_subject = arrayList.get(position).getSubjectName();
                builder.setView(v);
                builder.setPositiveButton(context.getResources().getString(R.string.option_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String subjectName = subject.getText().toString().trim();
                        int flag = ((SubjectsActivity) context).checkIfAlreadyEntered(subjectName);
                        if (flag == 0) {
                            database.editSubject(subjectName, old_subject);
                            timeTableDatabase.editSubject(subjectName, old_subject);
                            arrayList.clear();
                            arrayList.addAll(database.getAllSubjects());
                            notifyDataSetChanged();
                            mItemManger.closeAllItems();
                        } else {
                            ((SubjectsActivity) context).showSnackbar(context.getResources().getString(R.string.subject_entry_same));
                            mItemManger.closeAllItems();
                        }
                    }
                });
                builder.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    static class SubjectsViewHolder extends RecyclerView.ViewHolder {

        private TextView subName;
        private SwipeLayout swipeLayout;
        private ImageButton editSub, delSub;

        public SubjectsViewHolder(View itemView) {
            super(itemView);
            subName = (TextView) itemView.findViewById(R.id.subject);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            editSub = (ImageButton) itemView.findViewById(R.id.edit);
            delSub = (ImageButton) itemView.findViewById(R.id.delete_subject);
        }
    }
}

