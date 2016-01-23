package dotinc.attendancemanager2.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import dotinc.attendancemanager2.Objects.AttendanceList;
import dotinc.attendancemanager2.Objects.SubjectsList;

/**
 * Created by vellapanti on 21/1/16.
 */
public class AttendanceDatabase extends SQLiteOpenHelper {
    public static final int Database_Version = 1;
    public static final String Databse_Name = "attendance_track";
    public static final String Subject_Id = "subject_id";
    public static final String Action = "action";
    public static final String ATTENDANCE_TRACKER = "attendance_tracker";
    public static final String DATE = "date";
    public static final String POSITION = "position";

    public AttendanceDatabase(Context context) {
        super(context, Databse_Name, null, Database_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String ATTENDANCE = "CREATE TABLE " + ATTENDANCE_TRACKER + "(" + Subject_Id + " INTEGER ," +POSITION + " INTEGER ,"
                + Action + " INTEGER ," + DATE + " VARCHAR(50));";
        db.execSQL(ATTENDANCE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String ATTENDANCE = "CREATE TABLE " + ATTENDANCE_TRACKER + "(" + Subject_Id + " INTEGER ," +POSITION + " INTEGER ,"
                + Action + " INTEGER ," + DATE + " VARCHAR(50));";
        db.execSQL(ATTENDANCE);
    }

    public void addAttendance(AttendanceList attendanceList){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Subject_Id,attendanceList.getId());
        values.put(Action,attendanceList.getAction());
        values.put(DATE,attendanceList.getDate());

    }


}
