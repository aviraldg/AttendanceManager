package dotinc.attendancemanager2.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Currency;

import dotinc.attendancemanager2.Objects.AttendanceList;
import dotinc.attendancemanager2.Objects.SubjectsList;

/**
 * Created by vellapanti on 21/1/16.
 */
public class AttendanceDatabase extends SQLiteOpenHelper {
    public static final int Database_Version = 2;
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
        String ATTENDANCE = "CREATE TABLE " + ATTENDANCE_TRACKER + "(" + Subject_Id + " INTEGER ," + POSITION + " INTEGER ,"
                + Action + " INTEGER ," + DATE + " VARCHAR(50));";
        db.execSQL(ATTENDANCE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String table_update = "ALTER TABLE " + ATTENDANCE_TRACKER + " ADD COLUMN " + POSITION + " INTEGER ";
        db.execSQL(table_update);
    }

    public void addAttendance(AttendanceList attendanceList) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Subject_Id, attendanceList.getId());
        values.put(Action, attendanceList.getAction());
        values.put(DATE, attendanceList.getDate());
        values.put(POSITION, attendanceList.getPosition());
        db.insert(ATTENDANCE_TRACKER, null, values);
        db.close();
    }

    public int totalPresent(AttendanceList list) {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT COUNT(" + Action + ") FROM " + ATTENDANCE_TRACKER + " WHERE " + Subject_Id + " = " +
                list.getId() + " AND " + Action + " =1 ";
        Cursor cursor = database.rawQuery(query, null);
        return cursor.getInt(0);
    }

    public int totalBunked(AttendanceList list) {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT COUNT(" + Action + ") FROM " + ATTENDANCE_TRACKER + " WHERE " + Subject_Id + " = " +
                list.getId() + " AND " + Action + " =0 ";
        Cursor cursor = database.rawQuery(query, null);
        return cursor.getInt(0);
    }

    public int totalClasses(AttendanceList list){
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT COUNT("+Action+") FROM " + ATTENDANCE_TRACKER + " WHERE "+ Subject_Id+ " = "+
                list.getId()+" AND ("+ Action +" =1 OR "+Action + "=0)";
        Cursor cursor = database.rawQuery(query,null);
        return cursor.getInt(0);
    }

}
