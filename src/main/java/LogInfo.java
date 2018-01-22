import java.time.LocalDate;
import java.util.List;

/**
 * Created by Oleh on 15-Jan-18.
 */
public class LogInfo {
    LocalDate date;
    List<String> id;

    public LogInfo(LocalDate date, List<String> id) {
        this.date = date;
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<String> getId() {
        return id;
    }
}
