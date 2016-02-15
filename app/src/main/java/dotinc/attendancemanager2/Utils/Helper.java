package dotinc.attendancemanager2.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

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
    public static final String PACKAGENAME = "dotinc.attendancemanager2";

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

    public static void clearData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Helper.FILENAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();

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
}
