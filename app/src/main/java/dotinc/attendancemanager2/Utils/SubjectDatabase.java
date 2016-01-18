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
        return SubjectName;
    }


}
