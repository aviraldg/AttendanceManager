package dotinc.attendancemanager2.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import dotinc.attendancemanager2.Objects.SubjectsList;
import dotinc.attendancemanager2.R;
import dotinc.attendancemanager2.SubjectsActivity;
import dotinc.attendancemanager2.Utils.SubjectDatabase;

/**
 * Created by vellapanti on 17/1/16.
 */
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
        viewHolder.sub_name.setText(arrayList.get(position).getSubjectName());

        viewHolder.subject_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view = inflater.inflate(R.layout.add_subject, null);
                subject = (EditText) view.findViewById(R.id.subject_name);
                final String old_subject = arrayList.get(position).getSubjectName();
                builder.setView(view);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String subjectName = subject.getText().toString().trim();
                        database.editSubject(subjectName,old_subject);
                        //database.toast();
                    }
                });
                builder.create().show();
            }
        });

        viewHolder.subject_layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("DELETE");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String subject = arrayList.get(position).getSubjectName();
                        database.deleteSubject(subject);
                        //database.toast();
                    }
                });
                builder.create().show();
                return true;
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    static class SubjectsViewHolder extends RecyclerView.ViewHolder {

        private TextView sub_name;
        private LinearLayout subject_layout;

        public SubjectsViewHolder(View itemView) {
            super(itemView);
            sub_name = (TextView) itemView.findViewById(R.id.subject);
            subject_layout = (LinearLayout) itemView.findViewById(R.id.subject_root);
        }
    }
}
