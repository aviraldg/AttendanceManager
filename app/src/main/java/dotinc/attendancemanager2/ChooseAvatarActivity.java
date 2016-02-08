package dotinc.attendancemanager2;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import dotinc.attendancemanager2.Utils.Helper;

public class ChooseAvatarActivity extends AppCompatActivity {

    private ImageView user_male;
    private ImageView user_female;
    private Animation imageAnimation;
    private TextView avatarText, orText;
    private Context context;

    private Handler mHandler;
    private CharSequence mText;
    private int mIndex;
    private long mDelay;

    void instantiate() {
        context = ChooseAvatarActivity.this;

        if (Helper.getFromPref(context, Helper.COMPLETED, "").matches("completed")) {
            startActivity(new Intent(context, MainActivity.class));
            finish();
        }
        mHandler = new Handler();
        avatarText = (TextView) findViewById(R.id.select_avatar_text);
        orText = (TextView) findViewById(R.id.or_text);
        user_male = (ImageView) findViewById(R.id.user_male);
        user_female = (ImageView) findViewById(R.id.user_female);
        imageAnimation = AnimationUtils.loadAnimation(context, R.anim.expand_in);

        imageAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                orText.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_avatar);

        instantiate();

        user_male.startAnimation(imageAnimation);
        user_female.startAnimation(imageAnimation);
        animateText("Choose your Avatar");
        setCharacterDelay(45);
    }

    public void onClickMale(View view) {

        Helper.saveToPref(context, Helper.USER_IMAGE_ID, String.valueOf(1));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Intent intent = new Intent(this, NameAndCriteriaActivity.class);
            intent.putExtra("transitionName", "user_male_transition");
            startSharedElement(intent);
        } else {
            startActivity(new Intent(this, NameAndCriteriaActivity.class));
        }
    }

    public void onClickFemale(View view) {
        Helper.saveToPref(context, Helper.USER_IMAGE_ID, String.valueOf(2));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Intent intent = new Intent(this, NameAndCriteriaActivity.class);
            intent.putExtra("transitionName", "user_female_transition");
            startSharedElement(intent);
        } else {
            startActivity(new Intent(this, NameAndCriteriaActivity.class));
        }
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void startSharedElement(Intent intent) {

        View maleImg = findViewById(R.id.user_male);
        View femaleImg = findViewById(R.id.user_female);
        user_male.setTransitionName("user_male_transition");
        user_female.setTransitionName("user_female_transition");

        Pair<View, String> p1 = Pair.create(maleImg, "user_male_transition");
        Pair<View, String> p2 = Pair.create(femaleImg, "user_female_transition");

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, p1, p2);
        startActivity(intent, options.toBundle());
    }


    private Runnable characterAdder = new Runnable() {
        @Override
        public void run() {

            avatarText.setText(mText.subSequence(0, mIndex++));
            if (mIndex <= mText.length())
                mHandler.postDelayed(characterAdder, mDelay);
        }
    };

    public void animateText(CharSequence text) {
        mText = text;
        mIndex = 0;

        avatarText.setText("");
        mHandler.removeCallbacks(characterAdder);
        mHandler.postDelayed(characterAdder, mDelay);
    }

    public void setCharacterDelay(long millis) {
        this.mDelay = millis;
    }
}
