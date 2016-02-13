package dotinc.attendancemanager2.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import dotinc.attendancemanager2.Objects.SubjectsList;
import dotinc.attendancemanager2.Objects.TimeTableList;

/**
 * Created by vellapanti on 18/1/16.
 */
public class SubjectDatabase extends SQLiteOpenHelper {
    public static final int Database_Version = 1;
    public static final String Subject_Name_Databse = "subject_name";
    public static final String Subjects_Table = "Semester_Subjects";
    public static final String Subject_Id = "subject_id";
    public static final String Subject_List = "subject_list";


    public SubjectDatabase(Context context) {
        super(context, Subject_Name_Databse, null, Database_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SUBJECTS = "CREATE TABLE " + Subjects_Table + "(" + Subject_Id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Subject_List + " VARCHAR(30));";
        db.execSQL(SUBJECTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addsubject(SubjectsList list) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Subject_List, list.getSubjectName());
        db.insert(Subjects_Table, null, values);
        db.close();
    }

    public void editSubject(String new_subject, String old_subject) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + Subjects_Table + " SET " + Subject_List + " = '" + new_subject + "' WHERE " + Subject_List + " = '" + old_subject + "'";
        db.execSQL(query);
        db.close();
    }

    public void deleteSubject(String subject) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + Subjects_Table + " WHERE " + Subject_List + " = '" + subject + "'";
        db.execSQL(query);
        db.close();
    }

    public ArrayList<SubjectsList> getAllSubjects() {
        ArrayList<SubjectsList> SubjectName = new ArrayList<SubjectsList>();
        String query = "SELECT * FROM " + Subjects_Table;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToNext()) {
            do {
                SubjectsList subjectsList = new SubjectsList();
                subjectsList.setId(cursor.getInt(0));
                subjectsList.setSubjectName(cursor.getString(1));
                SubjectName.add(subjectsList);

            } while (cursor.moveToNext());
        } else {
            Log.d("option_cur", "null");
        }
        db.close();
        return SubjectName;
    }

    public ArrayList<String> getAllSubjectName(){
        ArrayList<String> subjectName =  new ArrayList<>();
        String query = "SELECT * FROM " + Subjects_Table;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToNext()) {
            do {
                subjectName.add(cursor.getString(1));
            } while (cursor.moveToNext());
        } else {
            Log.d("option_cur", "null");
        }
        db.close();
        return subjectName;
    }

    public void addMultipleSubjects(ArrayList<String> subjects){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        for (int size=0;size<subjects.size();size++){
            values.put(Subject_List,subjects.get(size));
            database.insert(Subjects_Table,null,values);
        }
        database.close();



    }

    public ArrayList<TimeTableList> getAllSubjectsForExtra() {
        ArrayList<TimeTableList> SubjectName = new ArrayList<>();
        String query = "SELECT * FROM " + Subjects_Table + " GROUP BY " + Subject_List;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToNext()) {
            do {
                TimeTableList timeTableList = new TimeTableList();
                timeTableList.setId(cursor.getInt(0));
                timeTableList.setSubjectName(cursor.getString(1));
                SubjectName.add(timeTableList);

            } while (cursor.moveToNext());
        } else {
            Log.d("option_cur", "null");
        }
        db.close();
        return SubjectName;
    }

//    public void toast() {
//        SQLiteDatabase dbs = this.getWritableDatabase();
//
//        String[] col = {Subject_Id, Subject_List};
//        Cursor cur = dbs.query(Subjects_Table, col, null, null, null, null, null);
//        StringBuffer buffer = new StringBuffer();
//        if (cur != null) {
//            while (cur.moveToNext()) {
//                int id = cur.getInt(0);
//                String subject = cur.getString(1);
//                Log.d("option_database", "id:" + String.valueOf(id) + " " + "name:" + subject);
//            }
//        } else {
//            Log.d("option", "cursor is null");
//        }
//    }
}
