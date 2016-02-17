package dotinc.attendancemanager2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import dotinc.attendancemanager2.Adapters.CustomSpinnerAdapter;
import dotinc.attendancemanager2.Adapters.PredictorAdapter;
import dotinc.attendancemanager2.Objects.SubjectsList;
import dotinc.attendancemanager2.Utils.SubjectDatabase;

public class PredictorActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Spinner attendOrBunked;
    private EditText numClasses;
    private RecyclerView recyclerView;
    private PredictorAdapter adapter;
    private ArrayList<String> subjects;
    private ArrayList<SubjectsList> subjectsLists;
    private Button calculate;
    private SubjectDatabase subjectDatabase;
    private int flag;
    private int classes;
    ArrayList<String> main_choice;
    ArrayList<String> second_choice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predictor);
        instantiate();
        addItemsToSpinner();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.go_to_date_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }

    private void addItemsToSpinner() {
        second_choice.add("Attended");
        second_choice.add("Bunked");
        attendOrBunked.setAdapter(new CustomSpinnerAdapter(this, second_choice));
        attendOrBunked.setVisibility(View.VISIBLE);
        numClasses.setVisibility(View.VISIBLE);
        attendOrBunked.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if (pos == 0)
                    flag = 1;
                else
                    flag = 2;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!numClasses.getText().toString().trim().equals(""))
                    classes = Integer.parseInt(numClasses.getText().toString());
                adapter = new PredictorAdapter(PredictorActivity.this, flag, classes, subjectsLists);
                recyclerView.setAdapter(adapter);
            }
        });


    }

    private void instantiate() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Predictor");
        subjectDatabase = new SubjectDatabase(this);
        subjects = new ArrayList<>();
        attendOrBunked = (Spinner) findViewById(R.id.second_choice);
        numClasses = (EditText) findViewById(R.id.number_of_classes);
        recyclerView = (RecyclerView) findViewById(R.id.predictor_recycler);
        subjectsLists = subjectDatabase.getAllSubjects();
        calculate = (Button) findViewById(R.id.load_perc);
        main_choice = new ArrayList<>();
        second_choice = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
