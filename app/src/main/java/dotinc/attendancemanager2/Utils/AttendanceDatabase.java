package dotinc.attendancemanager2.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import dotinc.attendancemanager2.Objects.AttendanceList;
import dotinc.attendancemanager2.Objects.TimeTableList;

/**
 * Created by vellapanti on 21/1/16.
 */
public class AttendanceDatabase extends SQLiteOpenHelper {
    public static final int Database_Version = 2;
    public static final String Database_Name = "attendance_track";
    public static final String Subject_Id = "subject_id";
    public static final String Action = "action";
    public static final String ATTENDANCE_TRACKER = "attendance_tracker";
    public static final String DATE = "date";
    public static final String POSITION = "position";

    public AttendanceDatabase(Context context) {
        super(context, Database_Name, null, Database_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String ATTENDANCE = "CREATE TABLE " + ATTENDANCE_TRACKER + "(" + Subject_Id + " INTEGER ,"
                + Action + " INTEGER ," + DATE + " VARCHAR(50) ,"+ POSITION + " INTEGER);";
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
//        Log.d("option_data", "ac:" + attendanceList.getAction() + "id:" + attendanceList.getId() +
//                "dae:" + attendanceList.getDate() + "pos:" + attendanceList.getPosition());
        db.close();
    }

    public ArrayList<AttendanceList> getAllDates(int id) {
        ArrayList<AttendanceList> attendanceLists = new ArrayList<>();
        String query = "SELECT * FROM " + ATTENDANCE_TRACKER + " WHERE " + Subject_Id + " = " + id +
                " AND (" + Action + " =1 OR " + Action + "=0)";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                AttendanceList attendanceList = new AttendanceList();
                attendanceList.setId(cursor.getInt(0));
                attendanceList.setAction(cursor.getInt(1));
                attendanceList.setDate(cursor.getString(2));
                attendanceLists.add(attendanceList);
            }
        } else {
        }
        Log.d("option_cur", "null");
        return attendanceLists;
    }

    public int totalPresent(int id) {
        int totalPresent = 0;
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT COUNT(" + Action + ") FROM " + ATTENDANCE_TRACKER + " WHERE " + Subject_Id + " = " +
                id + " AND " + Action + " =1 ";
        Cursor cursor = database.rawQuery(query, null);

        if (cursor != null) {
            while (cursor.moveToNext())
                totalPresent = cursor.getInt(0);
        } else
            Log.d("option", "cursor is null");
        return totalPresent;
    }

    public int totalBunked(int id) {
        int totalBunked = 0;
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT COUNT(" + Action + ") FROM " + ATTENDANCE_TRACKER + " WHERE " + Subject_Id + " = " +
                id + " AND " + Action + " =0 ";
        Cursor cursor = database.rawQuery(query, null);
        if (cursor != null) {
            while (cursor.moveToNext())
                totalBunked = cursor.getInt(0);
        } else
            Log.d("option", "cursor is null");
        return totalBunked;
    }

    public int totalClasses(int id) {
        int totalClasses = 0;
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT COUNT(" + Action + ") FROM " + ATTENDANCE_TRACKER + " WHERE " + Subject_Id + " = " +
                id + " AND (" + Action + " =1 OR " + Action + "=0)";
        Cursor cursor = database.rawQuery(query, null);
        if (cursor != null) {
            while (cursor.moveToNext())
                totalClasses = cursor.getInt(0);
        } else
            Log.d("option", "cursor is null");
        return totalClasses;
    }

    public void resetAttendance(int id, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + ATTENDANCE_TRACKER + " WHERE " + Subject_Id + " = " + id + " AND " +
                DATE + " = '" + date + "'";
        db.execSQL(query);
        db.close();
    }

    public void deleteSubjects(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + ATTENDANCE_TRACKER + " WHERE " + Subject_Id + " = " + id;
        db.execSQL(query);
        db.close();
    }

    public void toast() {
        SQLiteDatabase dbs = this.getWritableDatabase();
        String[] col = {Subject_Id, POSITION, Action, DATE};
        Cursor cur = dbs.query(ATTENDANCE_TRACKER, col, null, null, null, null, null);
        if (cur != null) {
            while (cur.moveToNext()) {
                int id = cur.getInt(0);
                int pos = cur.getInt(1);
                int dc = cur.getInt(2);
                String subject = cur.getString(3);

                Log.d("option_database", "id:" + String.valueOf(id) + " " + "date:" + subject + "  " + "act:" + dc + "pos:" + pos);
            }
        } else {
            Log.d("option", "cursor is null");
        }
        dbs.close();
    }

    public ArrayList<AttendanceList> getMarker(TimeTableList list, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<AttendanceList> attendanceLists = new ArrayList<>();
        String query = "SELECT * FROM " + ATTENDANCE_TRACKER + " WHERE " + Subject_Id + " = " + list.getId() +
                " AND " + DATE + " = '" + date + "'";

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToLast();
        if (cursor != null && cursor.getCount() != 0) {
            AttendanceList attendanceList = new AttendanceList();
            attendanceList.setId(cursor.getInt(0));
            attendanceList.setAction(cursor.getInt(1));

            attendanceLists.add(attendanceList);
        } else {
            Log.d("option_cur", "null");
        }
        return attendanceLists;

    }
}
