package dotinc.attendancemanager2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import dotinc.attendancemanager2.Adapters.SubjectsAdapter;
import dotinc.attendancemanager2.Objects.SubjectsList;
import dotinc.attendancemanager2.Utils.SubjectDatabase;
import dotinc.attendancemanager2.Utils.TimeTableDatabase;

public class SubjectsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton addSubjects;
    private TextView subjectText;
    private View view1;
    private ImageView done;
    private ArrayList<SubjectsList> arrayList;
    private EditText subject;
    private LinearLayout emptyView;
    private CoordinatorLayout root;
    private SubjectsAdapter adapter;
    private SubjectDatabase database;
    private SubjectsList subjectsList;
    private TimeTableDatabase timeTableDatabase;
    private boolean ifEmpty, fromSettings;

    private void instantiate() {
        addSubjects = (FloatingActionButton) findViewById(R.id.add_subjects);
        subjectText = (TextView) findViewById(R.id.subject_layout_title);
        view1 = findViewById(R.id.view1);

        fromSettings = getIntent().getBooleanExtra("Settings", false);
        database = new SubjectDatabase(this);
        timeTableDatabase = new TimeTableDatabase(this);
        emptyView = (LinearLayout) findViewById(R.id.empty_view);
        root = (CoordinatorLayout) findViewById(R.id.root);
        done = (ImageView) findViewById(R.id.done);
        subjectsList = new SubjectsList();
        arrayList = database.getAllSubjects();
        setEmptyView(arrayList.size());
        recyclerView = (RecyclerView) findViewById(R.id.subjects);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SubjectsAdapter(this, arrayList);
    }

//    public void olderVersionDatabase() {
//        ifEmpty = timeTableDatabase.checkEmpty();
//        if (ifEmpty == true){
//
//            Log.d("option_timedatabase", "is null");
//
//        }
//
//        else {
//            timeTableDatabase.addPosition();
//            timeTableDatabase.toast();
//            Log.d("option_timedatabase","not null");
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);

        instantiate();
        //olderVersionDatabase();

        recyclerView.setAdapter(adapter);

        addSubjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(SubjectsActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.add_subject, null);
                subject = (EditText) view.findViewById(R.id.subject_name);

                dialog.setView(view);
                dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String subjectName = subject.getText().toString().trim();
                        if (checkIfAlreadyEntered(subjectName) == 0 && subjectName.length() != 0) {
                            subjectsList.setSubjectName(subjectName);
                            database.addsubject(subjectsList);
                            arrayList.clear();
                            arrayList.addAll(database.getAllSubjects());
                            setEmptyView(arrayList.size());
                            adapter.notifyDataSetChanged();
                        } else if (subjectName.length() == 0)
                            showSnackbar("Subject cannot be empty");
                        else
                            showSnackbar("Subject already entered");
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                dialog.create().show();

            }
        });
    }


    public int checkIfAlreadyEntered(String subject) {
        int flag = 0;
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getSubjectName().equals(subject)) {
                flag = 1;
                break;
            }
        }
        return flag;
    }


    public void setEmptyView(int size) {
        if (size == 0) {
            emptyView.setVisibility(View.VISIBLE);
            subjectText.setVisibility(View.GONE);
            view1.setVisibility(View.GONE);
            done.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
            subjectText.setVisibility(View.VISIBLE);
            view1.setVisibility(View.VISIBLE);
            done.setVisibility(View.VISIBLE);
        }

    }

    public void showSnackbar(String message) {
        Snackbar.make(root, message, Snackbar.LENGTH_LONG).show();
    }

    public void doneAddSubjects(View view) {
        if (fromSettings)
            finish();
        else
            startActivity(new Intent(SubjectsActivity.this, WeeklySubjectsActivity.class));
    }
}
