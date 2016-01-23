package dotinc.attendancemanager2.Objects;

/**
 * Created by vellapanti on 21/1/16.
 */
public class AttendanceList {

    int id;
    int action;
    int dayCode;
    int position;
    String date;

    public int getDayCode() {
        return dayCode;
    }

    public void setDayCode(int dayCode) {
        this.dayCode = dayCode;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }


}
