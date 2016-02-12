package dotinc.attendancemanager2;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.appyvet.rangebar.RangeBar;

import dotinc.attendancemanager2.Utils.Helper;

public class NameAndCriteriaActivity extends AppCompatActivity {

    private Context context;
    private ImageView userImage;
    private EditText userName;
    private RangeBar attCriteria;
    private TextView selectedPerc;
    private FloatingActionButton fab;
    private CardView nameCard, percCard;
    private Animation cardAnimation;
    private CoordinatorLayout root;
    private static int user_image_id, rangeBarValue;


    private void instantiate() {

        context = NameAndCriteriaActivity.this;
        userImage = (ImageView) findViewById(R.id.user_img);
        userName = (EditText) findViewById(R.id.user_name);
        attCriteria = (RangeBar) findViewById(R.id.rangebar);
        selectedPerc = (TextView) findViewById(R.id.selected_perc);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        root = (CoordinatorLayout) findViewById(R.id.root);
        nameCard = (CardView) findViewById(R.id.name_card);
        percCard = (CardView) findViewById(R.id.percentage_card);
        user_image_id = Integer.parseInt(Helper.getFromPref(context, Helper.USER_IMAGE_ID, String.valueOf(0)));

        String name = Helper.getFromPref(context, Helper.USER_NAME, "");
        if (name.equals("") == false)
            userName.setText(name);

        cardAnimation = AnimationUtils.loadAnimation(this, R.anim.expand_in);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            userImage.setTransitionName(getIntent().getStringExtra("transitionName"));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_and_criteria);

        instantiate();
        setUserImage();
        setupRangeBar();

        animateCard(nameCard);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nameCard.getVisibility() == View.VISIBLE && percCard.getVisibility() != View.VISIBLE) {
                    if (userName.getText().toString().trim().equals("")) {
                        showSnackbar("Enter your name");
                        userName.setText("");
                    } else {
                        Helper.saveToPref(context, Helper.USER_NAME, userName.getText().toString().trim());
                        animateCard(percCard);
                    }
                } else if (percCard.getVisibility() == View.VISIBLE) {
                    rangeBarValue = Integer.parseInt(Helper.getFromPref(context, Helper.ATTENDANCE_CRITERIA, String.valueOf(-1)));

                    if (userName.getText().toString().equals("") && rangeBarValue == 0)
                        showSnackbar("Enter your name and criteria");
                    if (userName.getText().toString().equals(""))
                        showSnackbar("Enter your name");
                    else if (rangeBarValue == 0)
                        showSnackbar("Enter attendance criteria");
                    else
                        startActivity(new Intent(NameAndCriteriaActivity.this, SubjectsActivity.class));
                }
            }
        });

        attCriteria.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {
                selectedPerc.setText(rightPinValue + "%");
                Helper.saveToPref(context, Helper.ATTENDANCE_CRITERIA, rightPinValue);
            }
        });
    }

    private void setupRangeBar() {
        attCriteria.setTickColor(getResources().getColor(R.color.tickColor));
        attCriteria.setConnectingLineColor(getResources().getColor(R.color.colorPrimaryDark));
        attCriteria.setPinColor(getResources().getColor(R.color.colorPrimaryDark));
        attCriteria.setSelectorColor(getResources().getColor(R.color.colorPrimaryDark));
        attCriteria.setConnectingLineWeight(5);

        rangeBarValue = Integer.parseInt(Helper.getFromPref(context, Helper.ATTENDANCE_CRITERIA, String.valueOf(0)));

        if (rangeBarValue != 0) {
            selectedPerc.setText(rangeBarValue + "%");
            attCriteria.setSeekPinByIndex(rangeBarValue / 5);
        } else {
            selectedPerc.setText("0%");
            attCriteria.setSeekPinByIndex(0);
        }
    }

    private void setUserImage() {
        if (user_image_id == 1)
            userImage.setImageResource(R.drawable.user_male);
        else if (user_image_id == 2)
            userImage.setImageResource(R.drawable.user_female);
    }

    private void animateCard(final CardView cardView) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                cardView.setVisibility(View.VISIBLE);
                cardView.startAnimation(cardAnimation);
            }
        }, 300);
    }


    private void showSnackbar(String message) {
        Snackbar.make(root, message, Snackbar.LENGTH_LONG).show();
    }
}