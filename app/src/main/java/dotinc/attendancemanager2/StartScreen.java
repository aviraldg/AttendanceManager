package dotinc.attendancemanager2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import dotinc.attendancemanager2.Adapters.StartScreenAdapter;
import dotinc.attendancemanager2.Fragements.First;
import dotinc.attendancemanager2.Fragements.Second;
import dotinc.attendancemanager2.Fragements.Third;
import dotinc.attendancemanager2.Utils.Helper;
import dotinc.attendancemanager2.Utils.ProgressPageIndicator;

public class StartScreen extends AppCompatActivity {

    private Context context;
    private ViewPager pager;
    private ArrayList<Fragment> fragments;
    private String[] desc;
    private TextView helpTv;
    private Button start;
    private ProgressPageIndicator indicator;

    private void instantiate() {
        pager = (ViewPager) findViewById(R.id.start_screen_pager);
        fragments = new ArrayList<>();
        fragments.add(new First());
        fragments.add(new Second());
        fragments.add(new Third());

        desc = getResources().getStringArray(R.array.viewpager_desc);
        indicator = (ProgressPageIndicator) findViewById(R.id.pageIndicator);
        helpTv = (TextView) findViewById(R.id.help_text);
        helpTv.setTypeface(Typeface.createFromAsset(getAssets(), Helper.JOSEFIN_SANS_REGULAR));
        helpTv.setText(desc[0]);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        context = StartScreen.this;

        if (Helper.getFromPref(context, Helper.COMPLETED, "").matches("completed")) {
            startActivity(new Intent(context, MainActivity.class));
            finish();
        }

        instantiate();
        pager.setAdapter(new StartScreenAdapter(getSupportFragmentManager(), fragments));
        pager.addOnPageChangeListener(new CustomOnPageChangeListener());
        indicator.setViewPager(pager, 0);
        indicator.setFillColor(ContextCompat.getColor(context, R.color.noClassColor));


        start = (Button) findViewById(R.id.start_btn);
        start.setEnabled(false);
        buttonEnabled();
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, ChooseAvatarActivity.class));
            }
        });
    }

    private class CustomOnPageChangeListener extends ViewPager.SimpleOnPageChangeListener {
        @Override
        public void onPageSelected(int position) {

            helpTv.setText(desc[position]);
            switch (position) {
                case 0:
                    indicator.setViewPager(pager, 0);
                    indicator.setFillColor(ContextCompat.getColor(context, R.color.noClassColor));
                    start.setEnabled(false);
                    buttonEnabled();
                    break;
                case 1:
                    indicator.setViewPager(pager, 1);
                    indicator.setFillColor(ContextCompat.getColor(context, R.color.absentColor));
                    start.setEnabled(false);
                    buttonEnabled();
                    break;
                case 2:
                    indicator.setViewPager(pager, 2);
                    indicator.setFillColor(ContextCompat.getColor(context, R.color.attendedColor));
                    start.setEnabled(true);
                    buttonEnabled();
                    break;
                default:
                    break;
            }
            super.onPageSelected(position);
        }
    }

    public void buttonEnabled() {
        if (!start.isEnabled()) {
            start.setBackgroundColor(ContextCompat.getColor(context, R.color.button_disabled));
            start.setTextColor(ContextCompat.getColor(context, R.color.secondaryText));
        } else {
            start.setBackgroundColor(ContextCompat.getColor(context, R.color.attendedColor));
            start.setTextColor(ContextCompat.getColor(context, R.color.white));
        }
    }
}
