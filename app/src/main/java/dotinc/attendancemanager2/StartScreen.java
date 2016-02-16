package dotinc.attendancemanager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import dotinc.attendancemanager2.Adapters.StartScreenAdapter;
import dotinc.attendancemanager2.Fragements.First;
import dotinc.attendancemanager2.Fragements.Fourth;
import dotinc.attendancemanager2.Fragements.Second;
import dotinc.attendancemanager2.Fragements.Third;
import dotinc.attendancemanager2.Utils.Helper;

public class StartScreen extends AppCompatActivity {

    private Context context;
    private ViewPager pager;
    private ArrayList<Fragment> fragments;

    private void instantiate() {
        pager = (ViewPager) findViewById(R.id.start_screen_pager);
        fragments = new ArrayList<>();
        fragments.add(new First());
        fragments.add(new Second());
        fragments.add(new Third());
        fragments.add(new Fourth());
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

    }
}
