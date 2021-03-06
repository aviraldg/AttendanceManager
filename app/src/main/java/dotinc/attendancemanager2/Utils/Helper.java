package dotinc.attendancemanager2.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Handler;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * Created by ddvlslyr on 7/2/16.
 */
public class Helper {
    public static final String FILENAME = "ATTENDANCE_MANAGER";
    public static final String ATTENDANCE_CRITERIA = "ATTENDANCE_CRITERIA";
    public static final String USER_NAME = "USER_NAME";
    public static final String USER_IMAGE_ID = "USER_IMAGE_ID";
    public static final String COMPLETED = "COMPLETED";
    public static final String NEEDBREAK = "BREAK";
    public static final String INITIAL_SETUP = "INITIAL_SETUP";
    public static final String FIRST_TIME = "FIRST_TIME";
    public static final String PACKAGENAME = "dotinc.attendancemanager2";


    public static final String OXYGEN_BOLD = "fonts/oxygen-bold.ttf";
    public static final String OXYGEN_REGULAR = "fonts/oxygen-regular.ttf";
    public static final String JOSEFIN_SANS_REGULAR = "fonts/josefin_sans_regular.ttf";
    public static final String JOSEFIN_SANS_BOLD = "fonts/josefin_sans-bold.ttf";

    private static Handler mHandler;
    private static CharSequence mText;
    private static int mIndex;
    private static long mDelay;
    private static Runnable characterAdder;

    public static void saveToPref(Context context, String key, String val) {
        SharedPreferences preferences = context.getSharedPreferences(FILENAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, val);
        editor.commit();
    }

    public static String getFromPref(Context context, String key, String defVal) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILENAME, context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defVal);
    }

    public static boolean exportDatabase(String dbName) {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//" + PACKAGENAME
                        + "//databases//" + dbName;
                String backupDBPath = "/AttendanceManager/" + dbName;

                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = null;

                dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean importDatabase(String dbName) {

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//" + PACKAGENAME
                        + "//databases//" + dbName;
                String backupDBPath = "/AttendanceManager/" + dbName;
                File backupDB = new File(data, currentDBPath);
                File currentDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static void animateText(final TextView textView, String text, long delay) {
        mHandler = new Handler();
        mIndex = 0;
        mText = text;
        mDelay = delay;
        characterAdder = new Runnable() {
            @Override
            public void run() {
                textView.setText(mText.subSequence(0, mIndex++));
                if (mIndex <= mText.length())
                    mHandler.postDelayed(characterAdder, mDelay);
            }
        };
        textView.setText("");
        mHandler.removeCallbacks(characterAdder);
        mHandler.postDelayed(characterAdder, mDelay);
    }


    public static String getDeviceInformation(Context context) {
        PackageManager manager = context.getPackageManager();
        PackageInfo infoPackage = null;
        try {
            infoPackage = manager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String appversion = infoPackage.versionName;
        String line = "\n\n---------------------------------------\n";
        return line+"\n\n" + "Model: " + android.os.Build.MODEL
                + "\nOS: " + android.os.Build.VERSION.RELEASE
                + "\nAppVersion: "
                + appversion + line;
    }
}
