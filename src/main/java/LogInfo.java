import java.util.Date;
import java.util.List;

/**
 * Created by Oleh on 15-Jan-18.
 */
public class LogInfo {
    Date date2;
    List<String> listOfId;

    public LogInfo(Date date2, List<String> listOfId) {
        this.date2 = date2;
        this.listOfId = listOfId;
    }

    public List<String> getListOfId() {
        return listOfId;
    }

    public void setListOfId(List<String> listOfId) {
        this.listOfId = listOfId;
    }


    public Date getDate2() {
        return date2;
    }

    public void setDate2(Date date2) {
        this.date2 = date2;
    }
}
