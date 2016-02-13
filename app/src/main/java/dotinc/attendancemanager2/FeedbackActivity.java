package dotinc.attendancemanager2;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class FeedbackActivity extends AppCompatActivity {

    Toolbar toolbar;

    EditText title, message;
    TextInputLayout textTitle, textMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("FeedBack");

        title = (EditText) findViewById(R.id.feedtitle);
//        title.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/josefin_sans-bold.ttf"));
        message = (EditText) findViewById(R.id.feedmsg);
  //      message.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/josefin_sans_regular.ttf"));

        textTitle = (TextInputLayout) findViewById(R.id.textTitle);
        textMessage = (TextInputLayout) findViewById(R.id.textMsg);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_feedback, menu);
        return true;
    }


    @Override
    protected void onStop() {
        super.onStop();

        title.setText("");
        message.setText("");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.help) {

            String sub = title.getText().toString();
            String msg = message.getText().toString();

            if (sub.matches("") && msg.matches("")) {

                textTitle.setError("Title cannot be blank");
                textMessage.setError("Message cannot be blank");
            } else if (sub.matches("")) {
                textTitle.setError("Title cannot be blank");
                textMessage.setError(null);
            } else if (msg.matches("")) {
                textTitle.setError(null);
                textMessage.setError("Message cannot be blank");
            } else {

                textTitle.setError(null);
                textMessage.setError(null);

                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{"agamgupta2895@gmail.com","subhakr11droy@gmail.com"});
                email.putExtra(Intent.EXTRA_SUBJECT, sub);
                email.putExtra(Intent.EXTRA_TEXT, msg);

                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choose an Email Client"));
            }


        }

        return super.onOptionsItemSelected(item);
    }
}
