package dotinc.attendancemanager2;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import dotinc.attendancemanager2.Utils.Helper;

public class AboutUsActivity extends AppCompatActivity {

    private TextView appName, appVer, appCompany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        appName = (TextView) findViewById(R.id.app_name);
        appName.setTypeface(Typeface.createFromAsset(getAssets(), Helper.OXYGEN_BOLD));
        appVer = (TextView) findViewById(R.id.app_ver);
        appVer.setTypeface(Typeface.createFromAsset(getAssets(), Helper.OXYGEN_REGULAR));
        appCompany = (TextView) findViewById(R.id.company);
        appCompany.setTypeface(Typeface.createFromAsset(getAssets(), Helper.JOSEFIN_SANS_REGULAR));

    }
}
