import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by Oleh on 15-Jan-18.
 */
public class ReadFromFile {
    static List<LogInfo> logInfoArrayList = new ArrayList<>();
    // TODO: 22-Jan-18 write data to file


    public static void main(String[] args) throws FileNotFoundException {
        List<String> fileLines = new ArrayList<>();
        List<String> listOfValidFiles = getListOfFilesInDirectory("src\\main\\resources\\");
        collectAllDataToList(fileLines, listOfValidFiles);
        readDataAndCreateObjects(fileLines);
        mergeDataByPeriod(14);
    }


    private static List<String> getListOfFilesInDirectory(String pathToFolder) {
        File folder = new File(pathToFolder);
        List<String> validFiles = new ArrayList<>();
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().contains("transactionsLog") && file.getName().endsWith(".txt")) {
                validFiles.add(file.getAbsolutePath());
            }
        }
        System.out.println(validFiles.size());

        return validFiles;
    }

    private static List<String> collectAllDataToList(List<String> fileLines, List<String> validFiles) {
        for (int a = 0; a < validFiles.size(); a++) {
            File file = new File(validFiles.get(a));
            Scanner sc = null;
            try {
                sc = new Scanner(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            while (sc.hasNextLine()) {
                fileLines.add(sc.nextLine());
            }
            fileLines.add("");
        }
        return fileLines;
    }


    private static List<LogInfo> readDataAndCreateObjects(List<String> fileLines) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, d MMM yyyy hh:mm:ss k z").withLocale(Locale.US);
        String previousLine = "";
        String tempDate;
        LocalDate logTime = null;
        String tempId = "";
        List<String> listOfIds;
        for (String line : fileLines
                ) {
            if (line.trim().contains("Log time:")) {
                tempDate = line.replaceFirst("Log time: ", "").trim();

                // op check how to change it
                SimpleDateFormat dateFormat = new SimpleDateFormat("E, dd MMM yyyy", Locale.US);
                try {
                    logTime = dateFormat.parse(tempDate).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                logTime = logTime;
            } else if (!line.trim().contains("Log time:") && !line.trim().contains("Captured transactions:") &&
                    !line.trim().contains("Asset id:") && !line.trim().contains("Warning") && !StringUtils.isBlank(line)) {
                tempId += line.trim() + ",";

            }
            if (!StringUtils.isBlank(previousLine) && line.equals("")) {
                if (!(StringUtils.isBlank(tempId))) {
                    listOfIds = Arrays.asList(tempId.split("\\s*,\\s*"));
                    logInfoArrayList.add(new LogInfo(logTime, listOfIds));
                    tempId = "";
                }
            }
            previousLine = line;
        }
        return logInfoArrayList;
    }

    private static HashMap<String, Map<String, Integer>> mergeDataByPeriod(int period) {
        LocalDate start = logInfoArrayList.get(0).getDate();
        LocalDate end = start.plusDays(period);
        String timePeriod = "";
        HashMap<String, Map<String, Integer>> sortedMap = new LinkedHashMap<>();
        List<String> id = new ArrayList<>();

        Collections.sort(logInfoArrayList, new Comparator<LogInfo>() {
            @Override
            public int compare(LogInfo o1, LogInfo o2) {
                if (o1.date.equals(o2.date)) {
                    return 0;
                }
                return o1.date.compareTo(o2.date);
            }
        });

        for (int a = 0; a < logInfoArrayList.size(); a++) {
            if (logInfoArrayList.get(a).getDate().isBefore(end)) {
                id.addAll(logInfoArrayList.get(a).getId());
                if (a == logInfoArrayList.size() - 1) {
                    timePeriod = start.toString() + "  " + end.minusDays(1).toString();
                    Collections.sort(id);
                    countDuplicates(id);
                    sortedMap.put(timePeriod, countDuplicates(id));
                }
            } else {
                timePeriod = start.toString() + "  " + end.minusDays(1).toString();
                Collections.sort(id);
                countDuplicates(id);
                sortedMap.put(timePeriod, countDuplicates(id));
                id = new ArrayList<>();
                start = start.plusDays(period);
                end = end.plusDays(period);
                a--;
            }
        }

        sortedMap.forEach((k, v) -> System.out.println(k + " => " + v));
//        v.forEach((foundValue, numRepeat) -> {
//            System.out.println(foundValue + " => " + numRepeat);
//        });

        return sortedMap;
    }


    private static HashMap<String, Integer> countDuplicates(List<String> id) {
        HashMap<String, Integer> nameAndCount = new HashMap<>();
        // build hash table with count
        for (String name : id) {
            Integer count = nameAndCount.get(name);
            if (count == null) {
                nameAndCount.put(name, 1);
            } else {
                nameAndCount.put(name, ++count);
            }
        }

        return nameAndCount;
    }
}