package logger;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Logger событий при работе сервера
 *
 * Author: Belenov.D
 * */

public class Logger {

    private File logFile;
    private SimpleDateFormat sf = new SimpleDateFormat("dd.MM.yyyy");
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    private String filePath = System.getProperty("user.home")+"\\MyLog\\"+sf.format(new Date())+"\\log.txt";

    public Logger(){
        logFile = new File(filePath);
        if (!logFile.exists()) {
            logFile.getParentFile().mkdirs();
        }
    }

    public void log (Exception e) {
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);

            FileWriter fw = new FileWriter(filePath,true);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(dateFormat.format(new Date())+" "+sw.toString()+"\n");
            bw.close();
        } catch (IOException e1) {
            System.out.println(e1);
        }
    }

    public void log (String loggingText) {
        try {
            FileWriter fw = new FileWriter(filePath,true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(dateFormat.format(new Date())+" "+loggingText+"\n");
            bw.close();
        } catch (IOException e1) {
            System.out.println(e1);
        }
    }
}
