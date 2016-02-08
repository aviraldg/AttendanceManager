package dotinc.attendancemanager2.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ddvlslyr on 7/2/16.
 */
public class Helper {


    public static final String FILENAME = "ATTENDANCE_MANAGER";
    public static final String ATTENDANCE_CRITERIA = "ATTENDANCE_CRITERIA";
    public static final String USER_NAME = "USER_NAME";
    public static final String USER_IMAGE_ID = "USER_IMAGE_ID";
    public static final String COMPLETED = "COMPLETED";


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
}
