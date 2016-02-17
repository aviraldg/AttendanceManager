package dotinc.attendancemanager2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import dotinc.attendancemanager2.Adapters.HelpAdapter;

public class HelpActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    ArrayList<String> topicList;
    ArrayList<String> descriptionList;
    HelpAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        instantiate();
        addToList();
    }

    private void addToList() {

        topicList.add("Setting up Time Table");
        descriptionList.add(getResources().getString(R.string.setting_up_timetable));

        topicList.add("Adding Attendance");
        descriptionList.add(getResources().getString(R.string.adding_attendance));

        topicList.add("Adding Extra classes");
        descriptionList.add(getResources().getString(R.string.adding_extraClasses));

        topicList.add("Shortcut to add all attendance");
        descriptionList.add(getResources().getString(R.string.shortcut_add_attendance));

        topicList.add("Go to a specific date");
        descriptionList.add(getResources().getString(R.string.gotodate));

        topicList.add("Detailed analysis");
        descriptionList.add(getResources().getString(R.string.detailed_analysis));

        topicList.add("Predictor");
        descriptionList.add(getResources().getString(R.string.predictor));
        adapter = new HelpAdapter(this, topicList, descriptionList);
        recyclerView.setAdapter(adapter);


    }

    private void instantiate() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Help");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = (RecyclerView) findViewById(R.id.help_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        topicList = new ArrayList<>();
        descriptionList = new ArrayList<>();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_help, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.help) {
            if (id == android.R.id.home)
                finish();
            else if (id == R.id.help) {
                Intent intent = new Intent(HelpActivity.this, FeedbackActivity.class);
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }
}