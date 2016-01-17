package dotinc.attendancemanager2.Utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vellapanti on 18/1/16.
 */
public class SubjectDatabase extends SQLiteOpenHelper {
    public static final int Database_Version = 1;
    public static final String Subject_Name_Databse = "subject_name";
    public static final String Subjects_Table = "Semester_Subjects";
    public static final String Subject_Id = "subject_id";
    public static final String Subject_List = "subject_list";


    public SubjectDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, Subject_Name_Databse, null, Database_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SUBJECTS="CREATE TABLE "+Subjects_Table+"("+Subject_Id+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +Subject_List+ " VARCHAR(30));";
        db.execSQL(SUBJECTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
