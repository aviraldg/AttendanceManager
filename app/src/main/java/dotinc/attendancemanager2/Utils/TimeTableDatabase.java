package dotinc.attendancemanager2.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import dotinc.attendancemanager2.Objects.SubjectsList;
import dotinc.attendancemanager2.Objects.TimeTableList;

/**
 * Created by vellapanti on 18/1/16.
 */
public class TimeTableDatabase extends SQLiteOpenHelper {
    public static final int Databse_Version = 1;
    public static final String TimeTable_Database_Name = "timetable_database";
    public static final String Subject_Id = "subject_id";
    public static final String Subjects_Selected = "subjects_selected";
    public static final String Day_Code = "day_code";
    public static final String TimeTable_Table = "timetable";

    public TimeTableDatabase(Context context) {
        super(context, TimeTable_Database_Name, null, Databse_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String TIMETABLE = "CREATE TABLE " + TimeTable_Table + "(" + Subject_Id + " INTEGER ," + Day_Code + " INTEGER ," + Subjects_Selected +
                " VARCHAR(30));";
        db.execSQL(TIMETABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void toast() {
        SQLiteDatabase dbs = this.getWritableDatabase();

        String[] col = {Subject_Id, Day_Code, Subjects_Selected};
        Cursor cur = dbs.query(TimeTable_Table, col, null, null, null, null, null);
        StringBuffer buffer = new StringBuffer();
        //#2yulqqpp
        if (cur != null) {
            while (cur.moveToNext()) {
                int id = cur.getInt(0);
                int dc = cur.getInt(1);
                String subject = cur.getString(2);
                Log.d("option_database", "id:" + String.valueOf(id) + " " + "name:" + subject + "  " + "dc:" + dc);
            }
        } else {
            Log.d("option", "cursor is null");
        }
    }

    public void addTimeTable(TimeTableList list) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Subject_Id, list.getId());
        values.put(Day_Code, list.getDayCode());
        values.put(Subjects_Selected, list.getSubjectName());
        db.insert(TimeTable_Table, null, values);
        db.close();
    }

    public ArrayList<TimeTableList> getSubjects(TimeTableList List) {
        ArrayList<TimeTableList> tableLists = new ArrayList<>();
        int day_code = List.getDayCode();
        String query = "SELECT * FROM " + TimeTable_Table + " WHERE " + Day_Code + " = " + day_code;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        if (cursor.moveToNext()) {
            do {
                TimeTableList timeTableList = new TimeTableList();
                timeTableList.setId(cursor.getInt(0));
                timeTableList.setDayCode(cursor.getInt(1));
                timeTableList.setSubjectName(cursor.getString(2));
                tableLists.add(timeTableList);
            } while (cursor.moveToNext());
        } else {
            Log.d("option_cur", "null");
        }
        return tableLists;
    }
}
