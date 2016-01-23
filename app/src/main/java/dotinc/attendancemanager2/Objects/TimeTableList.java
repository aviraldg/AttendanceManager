package dotinc.attendancemanager2.Objects;

/**
 * Created by vellapanti on 18/1/16.
 */
public class TimeTableList {
    int id;
    int dayCode;
    int position;
    String subjectName;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


    public void TimeTableList() {
    }

    public void TimeTableList(int id, int dayCode, int position, String subjectName) {
        this.id = id;
        this.dayCode = dayCode;
        this.position = position;
        this.subjectName = subjectName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getDayCode() {
        return dayCode;
    }

    public void setDayCode(int dayCode) {
        this.dayCode = dayCode;
    }


}
