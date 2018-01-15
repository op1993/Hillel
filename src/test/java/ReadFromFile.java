import org.apache.commons.lang3.StringUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Oleh on 15-Jan-18.
 */
public class ReadFromFile {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("src\\main\\resources\\testData.txt");
        Scanner sc = new Scanner(file);
        List<LogInfo> logInfoArrayList = new ArrayList<>();
        List<String> array = new ArrayList<>();

        while (sc.hasNextLine()) {
            array.add(sc.nextLine());
        }

        String prev = "";
        String tempDate = null;
        String tempId = null;
        for (String a : array
                ) {
            if (a.trim().contains("Log time:")) {
                tempDate = a.replaceFirst("Log time: ", "");
            }
//             (a.trim().contains("Captured transactions:")) {
//                tempId = a.replaceFirst("Captured transactions:","");
//            }
            else if (!a.trim().contains("Log time:") && !a.trim().contains("Captured transactions:") &&
                    !a.trim().contains("Asset id:") && !a.trim().contains("Warning") && !StringUtils.isBlank(a)) {
             //   System.out.println(a + "-----");
                tempId = a;
            }

            if (prev.equals(a)) {
                //   System.out.println("----------------------------");
                logInfoArrayList.add(new LogInfo(tempDate, tempId));
            }
            prev = a;

//            // System.out.println(a);

        }
        for (LogInfo logInfos : logInfoArrayList) {
            //        System.out.println(logInfos.getDate());
            System.out.println(logInfos.getId());
        }


    }
}
