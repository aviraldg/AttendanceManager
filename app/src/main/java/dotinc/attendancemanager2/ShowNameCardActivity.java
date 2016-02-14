package dotinc.attendancemanager2;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;

import dotinc.attendancemanager2.Utils.AttendanceDatabase;
import dotinc.attendancemanager2.Utils.Helper;
import dotinc.attendancemanager2.Utils.SubjectDatabase;
import dotinc.attendancemanager2.Utils.TimeTableDatabase;

public class ShowNameCardActivity extends AppCompatActivity {

    private Context context;
    private ImageView userImage, restoreImage;
    private TextView userName, userPerc;
    private ProgressBar progressBar;
    private View border;
    private TextView savedDataText, importData, freshStart;

    private void instantiate() {
        context = ShowNameCardActivity.this;
        userImage = (ImageView) findViewById(R.id.user_img);
        userName = (TextView) findViewById(R.id.user_name);
        userPerc = (TextView) findViewById(R.id.user_perc);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.getIndeterminateDrawable().setColorFilter(0xFFFFFFFF, PorterDuff.Mode.MULTIPLY);
        savedDataText = (TextView) findViewById(R.id.check_data_text);
        importData = (TextView) findViewById(R.id.import_data);
        restoreImage = (ImageView) findViewById(R.id.restore_image);
        freshStart = (TextView) findViewById(R.id.fresh_start);
        border = findViewById(R.id.border);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_name_card);

        instantiate();
        setup();
    }

    private void setup() {
        int id = Integer.parseInt(Helper.getFromPref(context, Helper.USER_IMAGE_ID, String.valueOf(0)));
        if (id == 1)
            userImage.setImageResource(R.drawable.user_male);
        else
            userImage.setImageResource(R.drawable.user_female);

        userName.setText(Helper.getFromPref(context, Helper.USER_NAME, ""));
        userPerc.setText("Attendance Criteria: "
                + Helper.getFromPref(context, Helper.ATTENDANCE_CRITERIA, String.valueOf(0)) + "%");
        progressBar.setVisibility(View.VISIBLE);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (checkPrevData()) {
                    showImport();
                } else {
                    startActivity(new Intent(context, SubjectsActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    finish();
                }
            }
        }, 2500);
    }

    private boolean checkPrevData() {
        final File storage = Environment.getExternalStorageDirectory();
        String[] files = {"attendance_track", "subject_name", "timetable_database"};
        File attdb = new File(storage + "/AttendanceManager/" + files[0]);
        File subdb = new File(storage + "/AttendanceManager/" + files[1]);
        File ttdb = new File(storage + "/AttendanceManager/" + files[2]);

        if (attdb.exists() && subdb.exists() && ttdb.exists())
            return true;
        else return false;
    }

    public void freshStart(View view) {
        startActivity(new Intent(context, SubjectsActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        finish();
    }

    private void importDatabase() {
        SubjectDatabase subjectDatabase = new SubjectDatabase(context);
        AttendanceDatabase attendanceDatabase = new AttendanceDatabase(context);
        TimeTableDatabase timeTableDatabase = new TimeTableDatabase(context);
        subjectDatabase.importData();
        attendanceDatabase.importData();
        timeTableDatabase.importData();
    }

    public void importToDatabase(View view) {
        importData.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        freshStart.setVisibility(View.GONE);
        border.setVisibility(View.GONE);
        savedDataText.setText("Importing data...");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                importDatabase();
                restoreImage.setImageResource(R.mipmap.ic_cloud_done_white_36dp);
                savedDataText.setText("Import Successful");
                progressBar.setVisibility(View.GONE);
                freshStart.setText("Let's Start");
                freshStart.setVisibility(View.VISIBLE);
            }
        }, 1000);
    }

    private void showImport() {
        progressBar.setVisibility(View.GONE);
        savedDataText.setText("BINGO! Backup Found");
        importData.setVisibility(View.VISIBLE);
        freshStart.setVisibility(View.VISIBLE);
        restoreImage.setVisibility(View.VISIBLE);
        border.setVisibility(View.VISIBLE);
    }
}
