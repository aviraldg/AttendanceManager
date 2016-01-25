package dotinc.attendancemanager2.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;

import java.util.ArrayList;

import dotinc.attendancemanager2.Objects.SubjectsList;
import dotinc.attendancemanager2.R;
import dotinc.attendancemanager2.SubjectsActivity;
import dotinc.attendancemanager2.Utils.SubjectDatabase;

/**
 * Created by vellapanti on 17/1/16.
 */

//*********Adapter of Subjects activity************//

public class SubjectsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<SubjectsList> arrayList;
    LayoutInflater inflater;
    SubjectDatabase database;
    private EditText subject;

    public SubjectsAdapter(Context context, ArrayList<SubjectsList> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        inflater = LayoutInflater.from(context);
        database = new SubjectDatabase(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_subjects, parent, false);
        SubjectsViewHolder viewHolder = new SubjectsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        SubjectsViewHolder viewHolder = (SubjectsViewHolder) holder;
        viewHolder.subName.setText(arrayList.get(position).getSubjectName());

        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, viewHolder.swipeLayout.findViewById(R.id.right_swipe));
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, viewHolder.swipeLayout.findViewById(R.id.left_swipe));


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


        viewHolder.delSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subject = arrayList.get(position).getSubjectName();
                database.deleteSubject(subject);

                arrayList.remove(position);
                ((SubjectsActivity) context).setEmptyView(arrayList.size());
                notifyDataSetChanged();
            }
        });

        viewHolder.editSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View v = inflater.inflate(R.layout.add_subject, null);
                subject = (EditText) v.findViewById(R.id.subject_name);
                subject.setText(arrayList.get(position).getSubjectName());
                final String old_subject = arrayList.get(position).getSubjectName();
                builder.setView(v);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String subjectName = subject.getText().toString().trim();
                        database.editSubject(subjectName, old_subject);
                        arrayList.clear();
                        arrayList.addAll(database.getAllSubjects());
                        notifyDataSetChanged();
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
