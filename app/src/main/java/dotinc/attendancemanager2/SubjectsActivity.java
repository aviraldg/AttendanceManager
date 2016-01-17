package dotinc.attendancemanager2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

import dotinc.attendancemanager2.Adapters.SubjectsAdapter;

public class SubjectsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FloatingActionButton addSubjects;
    private ArrayList<String> arrayList;
    private Toolbar toolbar;
    private EditText subject;
    SubjectsAdapter adapter;

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
                dialog.setView(view);
                dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

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
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        addSubjects = (FloatingActionButton) findViewById(R.id.add_subjects);
        arrayList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.subjects);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SubjectsAdapter(this, arrayList);
        recyclerView.setAdapter(adapter);
        subject = (EditText) findViewById(R.id.add_subjects);
    }

}
