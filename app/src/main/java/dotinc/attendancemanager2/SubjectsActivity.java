package dotinc.attendancemanager2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

import dotinc.attendancemanager2.Adapters.SubjectsAdapter;
import dotinc.attendancemanager2.Objects.SubjectsList;
import dotinc.attendancemanager2.Utils.SubjectDatabase;

public class SubjectsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FloatingActionButton addSubjects;
    private ArrayList<SubjectsList> arrayList;
    private Toolbar toolbar;
    private EditText subject;
    SubjectsAdapter adapter;
    SubjectDatabase database;
    SubjectsList subjectsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);

        instantiate();
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
                        subjectsList.setSubjectName(subjectName);
                        database.addsubject(subjectsList);
                        arrayList = database.getAllSubjects();
                        adapter.notifyDataSetChanged();
                        //database.toast();

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

    private void instantiate() {

        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("NearBy");
        database = new SubjectDatabase(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        addSubjects = (FloatingActionButton) findViewById(R.id.add_subjects);
        arrayList = new ArrayList<SubjectsList>();
        arrayList = database.getAllSubjects();
        recyclerView = (RecyclerView) findViewById(R.id.subjects);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SubjectsAdapter(this, arrayList);
        recyclerView.setAdapter(adapter);
        subjectsList = new SubjectsList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.subject_name,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.done){
            Intent intent = new Intent(SubjectsActivity.this,TimeTableActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
