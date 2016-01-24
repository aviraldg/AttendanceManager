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
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import dotinc.attendancemanager2.Adapters.SubjectsAdapter;
import dotinc.attendancemanager2.Objects.SubjectsList;
import dotinc.attendancemanager2.Utils.SubjectDatabase;

public class SubjectsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FloatingActionButton addSubjects;
    private TextView subjectText;
    private ArrayList<SubjectsList> arrayList;
    private Toolbar toolbar;
    private EditText subject;
    private LinearLayout emptyView;
    private CoordinatorLayout root;
    SubjectsAdapter adapter;
    SubjectDatabase database;
    SubjectsList subjectsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);

        instantiate();

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
                        } else if (subjectName.length() == 0) {
                            Snackbar.make(root, "Subject cannot be empty", Snackbar.LENGTH_LONG).show();
                        } else {
                            Snackbar.make(root, "Subject already entered", Snackbar.LENGTH_LONG).show();
                        }
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


    private int checkIfAlreadyEntered(String subject) {
        int flag = 0;
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getSubjectName().equals(subject)) {
                flag = 1;
                break;
            }
        }
        return flag;
    }

    private void instantiate() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Input Subjects");
        addSubjects = (FloatingActionButton) findViewById(R.id.add_subjects);
        subjectText = (TextView) findViewById(R.id.subject_layout_title);
        database = new SubjectDatabase(this);
        emptyView = (LinearLayout) findViewById(R.id.empty_view);
        root = (CoordinatorLayout) findViewById(R.id.root);
        subjectsList = new SubjectsList();
        arrayList = database.getAllSubjects();
        setEmptyView(arrayList.size());
        recyclerView = (RecyclerView) findViewById(R.id.subjects);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SubjectsAdapter(this, arrayList);
    }


    public void setEmptyView(int size) {
        if (size == 0) {
            emptyView.setVisibility(View.VISIBLE);
            subjectText.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
            subjectText.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.subject_name, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.done) {
            if (!arrayList.isEmpty()) {
                Intent intent = new Intent(SubjectsActivity.this, WeeklySubjectsActivity.class);
                startActivity(intent);
            } else
                Snackbar.make(root, "Enter atleast one subject", Snackbar.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
