/**
 * Created by Oleh on 15-Jan-18.
 */
public class LogInfo {
    String date;
    String id;

    public LogInfo(String date, String id) {
        this.date = date;
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
