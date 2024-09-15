package utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static utils.DateUtils.getTodayValue;
import java.io.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static utils.utils.delay;

/**
 * The type File utils.
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class fileUtils {

    private static final Logger log = LoggerFactory.getLogger(fileUtils.class);

    /**
     * Gets file from container.
     *
     * @param driver the driver
     * @param fileToDownload the file name(with extension) to download
     * @param fileToSave the file name(with extension) to save
     */
    public static void getFileFromContainer(WebDriver driver, String fileToDownload, String fileToSave) {
        try {
            String command = String.format("curl GET http://localhost:4444/download/%s/%s",
                    ((RemoteWebDriver) driver).getSessionId(), fileToDownload);
            ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            InputStream response = process.getInputStream();
            File file = new File("./exportedFiles/" + fileToSave);
            copyInputStreamToFile(response, file);
        } catch (IOException e) {
            log.error("Fail to download file from container");
            e.printStackTrace();
        }
    }

    private static void copyInputStreamToFile(InputStream input, File file)
            throws IOException {
        try (OutputStream output = new FileOutputStream(file, false)) {
            input.transferTo(output);
        }
    }

    public static void getExportFileFromContainer(String sessionId, String fileToDownload, String fileToSave) {
        Process process = null;
        try {
            String command = String.format(
                    "curl http://localhost:4444/home/azureadmin/myagent/_work/1/s/ExportedFiles/",
                    sessionId, generateStringToCopy(fileToDownload), fileToSave);
            ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
            processBuilder.redirectErrorStream(true);
            process = processBuilder.start();
            /// delay(2);
            process.waitFor(4, TimeUnit.SECONDS);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append(System.getProperty("line.separator"));
            }
            String result = builder.toString();
            log.warn(result);

        } catch (IOException e) {
            log.error("Fail to download file from container");
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("Interuupted Exception Occured");
            e.printStackTrace();
        } finally {
            System.out.println("Destroy the process");
            process.destroy();
        }
    }

    public static String generateStringToCopy(String fileToDownload) {
        String date = getTodayValue().replace("/", "_");
        String time = DateUtils.getTodayTime().replace(":", "_").replace(" ", "%20");
        return fileToDownload + date + time + ".xlsx";
    }

    public static List<String> getHeaderRowData(String fileToSave) throws IOException {
        delay(1);
        List<String> headers = new ArrayList<String>();
        File fileName = new File(System.getProperty("user.dir") + "EXPORTED_FILE_PATH" + fileToSave);
        String path = fileName.getName();
        FileInputStream fileInputStream = new FileInputStream(
                System.getProperty("user.dir") + "/ExportedFiles/" + path);
        delay(1);
        Workbook xWorkbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = xWorkbook.getSheetAt(0);
        int column = sheet.getRow(0).getLastCellNum();
        for (int i = 0; i < column; i++) {
            Cell cellvalue = sheet.getRow(0).getCell(i);
            String header = cellvalue.getStringCellValue();
            headers.add(header);
        }
        xWorkbook.close();
        return headers;
    }

    public static void deleteFile(String startingBy) throws IOException {
        File[] listOfFiles = new File(System.getProperty("user.dir") + "/ExportedFiles/").listFiles();
        for (File fileName : listOfFiles) {
            if (fileName.getName().startsWith(startingBy)) {
                fileName.delete();
            }
        }
    }
}
