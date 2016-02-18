package dotinc.attendancemanager2.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import dotinc.attendancemanager2.Objects.TimeTableList;

/**
 * Created by vellapanti on 18/1/16.
 */
public class TimeTableDatabase extends SQLiteOpenHelper {
    public static final int Database_Version = 2;
    public static final String TimeTable_Database_Name = "timetable_database";
    public static final String Subject_Id = "subject_id";
    public static final String Subjects_Selected = "subjects_selected";
    public static final String Day_Code = "day_code";
    public static final String TimeTable_Table = "timetable";
    public static final String POSITION = "position";
    Context context;

    public TimeTableDatabase(Context context) {
        super(context, TimeTable_Database_Name, null, Database_Version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String TIMETABLE = "CREATE TABLE " + TimeTable_Table + "(" + Subject_Id + " INTEGER ,"
                + Day_Code + " INTEGER ," + Subjects_Selected + " VARCHAR(30) ," + POSITION + " INTEGER);";
        db.execSQL(TIMETABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String table_update = "ALTER TABLE " + TimeTable_Table + " ADD COLUMN " + POSITION + " INTEGER ";
        db.execSQL(table_update);
    }



    public void addTimeTable(TimeTableList list) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Subject_Id, list.getId());
        values.put(Day_Code, list.getDayCode());
        values.put(Subjects_Selected, list.getSubjectName());
        values.put(POSITION, list.getPosition());
        db.insert(TimeTable_Table, null, values);
        db.close();
    }
    public int checkEmpty(){
        int flag =0;
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "SELECT * FROM "+ TimeTable_Table;
        Cursor cursor = database.rawQuery(query,null);
        if (cursor.getCount()!=0)
            flag=1;
        return flag;
    }
    public ArrayList<TimeTableList> getSubjects(TimeTableList List) {
        ArrayList<TimeTableList> tableLists = new ArrayList<>();
        int day_code = List.getDayCode();
        String query = "SELECT * FROM " + TimeTable_Table + " WHERE " + Day_Code + " = " + day_code;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                TimeTableList timeTableList = new TimeTableList();
                timeTableList.setId(cursor.getInt(0));
                timeTableList.setDayCode(cursor.getInt(1));
                timeTableList.setSubjectName(cursor.getString(2));
                timeTableList.setPosition(cursor.getInt(3));
                tableLists.add(timeTableList);
            }
        }
        return tableLists;
    }

    public void deleteSubject(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TimeTable_Table + " WHERE " + Subject_Id + " = " + id;
        db.execSQL(query);
        db.close();
    }

    public void deleteTimeTable(TimeTableList list) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TimeTable_Table + " WHERE " + Subject_Id + " = " + list.getId() + " and " +
                Subjects_Selected + " = '" + list.getSubjectName() + "' and " + POSITION + " = " + list.getPosition()
                + " and " + Day_Code + " = " + list.getDayCode();
        db.execSQL(query);
        db.close();

    }

    public void editSubject(String new_subject, String old_subject) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TimeTable_Table + " SET " + Subjects_Selected + " = '" + new_subject + "' WHERE " + Subjects_Selected + " = '" + old_subject + "'";
        db.execSQL(query);
        db.close();
    }

    public boolean exportData() {
        return Helper.exportDatabase(TimeTable_Database_Name);
    }

    public boolean importData() {
        return Helper.importDatabase(TimeTable_Database_Name);
    }
}
