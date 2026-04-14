import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

// Abstract Logger
abstract class Logger {
    abstract void log(String message);
}

// FileLogger with rotation and backup limit
class FileLogger extends Logger {
    private String fileName;
    private long maxSize;
    private int maxBackupFiles;

    public FileLogger(String fileName, long maxSize, int maxBackupFiles) {
        this.fileName = fileName;
        this.maxSize = maxSize;
        this.maxBackupFiles = maxBackupFiles;
    }

    @Override
    public void log(String message) {
        try {
            File file = new File(fileName);

            // Rotate if size exceeded
            if (file.exists() && file.length() >= maxSize) {
                rotateLogs();
            }

            FileWriter fw = new FileWriter(fileName, true);
            BufferedWriter bw = new BufferedWriter(fw);

            String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .format(new Date());

            bw.write(timeStamp + " - " + message);
            bw.newLine();
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Improved rotation logic (log1, log2, log3)
    private void rotateLogs() {
        try {
            // Delete oldest file
            File oldest = new File("log" + maxBackupFiles + ".txt");
            if (oldest.exists()) {
                oldest.delete();
            }

            // Shift files (log2 -> log3, log1 -> log2)
            for (int i = maxBackupFiles - 1; i >= 1; i--) {
                File src = new File("log" + i + ".txt");
                if (src.exists()) {
                    File dest = new File("log" + (i + 1) + ".txt");
                    src.renameTo(dest);
                }
            }

            // Rename current log to log1
            File current = new File(fileName);
            File firstBackup = new File("log1.txt");
            current.renameTo(firstBackup);

            System.out.println("Log rotated successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

// Manager class
class LogManager {
    private Logger logger;

    public LogManager(Logger logger) {
        this.logger = logger;
    }

    public void writeLog(String msg) {
        logger.log(msg);
    }
}

// Main class
public class LogRotation {
    public static void main(String[] args) {
        // 1KB size, keep 3 backup files
        FileLogger fileLogger = new FileLogger("app.log", 1024, 3);
        LogManager manager = new LogManager(fileLogger);

        for (int i = 1; i <= 50; i++) {
            manager.writeLog("Log message " + i);
        }
    }
}