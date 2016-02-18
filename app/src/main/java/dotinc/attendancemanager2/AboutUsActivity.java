package dotinc.attendancemanager2;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import dotinc.attendancemanager2.Utils.Helper;

public class AboutUsActivity extends AppCompatActivity {

    private TextView appName, appVer, appCompany;
    private Button rateUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        appName = (TextView) findViewById(R.id.app_name);
        rateUs = (Button) findViewById(R.id.rateUs);
        rateUs.setTypeface(Typeface.createFromAsset(getAssets(), Helper.OXYGEN_BOLD));
        appName.setTypeface(Typeface.createFromAsset(getAssets(), Helper.OXYGEN_BOLD));
        appVer = (TextView) findViewById(R.id.app_ver);
        appVer.setTypeface(Typeface.createFromAsset(getAssets(), Helper.OXYGEN_REGULAR));
        appCompany = (TextView) findViewById(R.id.company);
        appCompany.setTypeface(Typeface.createFromAsset(getAssets(), Helper.JOSEFIN_SANS_REGULAR));
        rateUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=dotinc.attendancemanager2"));
                startActivity(intent);
            }
        });

    }
}
