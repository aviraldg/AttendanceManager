package dotinc.attendancemanager2;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import dotinc.attendancemanager2.Utils.Helper;

public class ShowNameCardActivity extends AppCompatActivity {

    private Context context;
    private ImageView userImage;
    private TextView userName, userPerc;
    private ProgressBar progressBar;

    private void instantiate() {
        userImage = (ImageView) findViewById(R.id.user_img);
        userName = (TextView) findViewById(R.id.user_name);
        userPerc = (TextView) findViewById(R.id.user_perc);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        context = ShowNameCardActivity.this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //getWindow().setSharedElementEnterTransition(TransitionInflater.from(this).inflateTransition(R.transition.shared_element_transition));
            //getWindow().
        }
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
                startActivity(new Intent(context, SubjectsActivity.class));
                finish();
            }
        }, 3000);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAfterTransition();
    }
}
