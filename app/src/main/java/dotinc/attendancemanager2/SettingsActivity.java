package dotinc.attendancemanager2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.rey.material.widget.Switch;

import java.util.ArrayList;

import dotinc.attendancemanager2.Adapters.SettingsAdapter;
import dotinc.attendancemanager2.Utils.AttendanceDatabase;
import dotinc.attendancemanager2.Utils.Helper;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private Context context;
    private ListView listView;
    private Toolbar toolbar;
    private Switch bunk_switch;
    private TextView bunkText;
    private AttendanceDatabase database;

    private void instantiate() {
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Settings");

        context = SettingsActivity.this;
        bunkText = (TextView) findViewById(R.id.bunk_text);
        bunkText.setText("Need a Break?");
        database = new AttendanceDatabase(context);
        //bunkText.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/oxygen-regular.ttf"));

        bunk_switch = (Switch) findViewById(R.id.onOff);
        if (Integer.parseInt(Helper.getFromPref(context, Helper.NEEDBREAK, String.valueOf(-1))) == 1) {
            bunk_switch.setChecked(true);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        instantiate();
        setUpListView();

        bunk_switch.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(Switch view, boolean checked) {
                if (checked)
                    Helper.saveToPref(context, Helper.NEEDBREAK, String.valueOf(1));
                else
                    Helper.saveToPref(context, Helper.NEEDBREAK, String.valueOf(0));
            }
        });
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

    private void setUpListView() {
        listView = (ListView) findViewById(R.id.settings_list);

        ArrayList<String> options = new ArrayList<>();
        options.add("Edit name and criteria");
        options.add("Edit Subjects");
        options.add("Edit Timetable");
        options.add("Import and Export");
        options.add("Reset attendance");

        ArrayList<Integer> icons = new ArrayList<Integer>();
        icons.add(R.mipmap.ic_edit_black_24dp);
        icons.add(R.mipmap.ic_library_books_black_24dp);
        icons.add(R.mipmap.ic_date_range_black_24dp);
        icons.add(R.mipmap.ic_import_export_black_24dp);
        icons.add(R.mipmap.ic_settings_backup_restore_black_24dp);
        listView.setAdapter(new SettingsAdapter(this, options, icons));

        listView.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        switch (i) {
            case 0:
                startActivity(new Intent(this, NameAndCriteriaActivity.class)
                        .putExtra("Settings", true));
                break;
            case 1:
                startActivity(new Intent(this, SubjectsActivity.class)
                        .putExtra("Settings", true));
                break;
            case 2:
                startActivity(new Intent(this, WeeklySubjectsActivity.class)
                        .putExtra("Settings", true));
                break;
            case 3:
                //startActivity(new Intent(this, Feedback.class));
                break;
            case 4:
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Reset Attendance");
                builder.setMessage("Do you wish to reset your attendance?");
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //database.deleteAllAttendance();
                        //database.close();
                    }
                });
                builder.create().show();
                break;
        }
    }
}
