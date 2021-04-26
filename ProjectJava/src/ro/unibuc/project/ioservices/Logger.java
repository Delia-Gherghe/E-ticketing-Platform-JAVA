package ro.unibuc.project.ioservices;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Logger {
    private static Logger loggerInstance = null;
    private static FileWriter loggerWriter;

    private Logger(){
        try{
            loggerWriter = new FileWriter("audit.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Logger getInstance(){
        if (loggerInstance == null){
            loggerInstance = new Logger();
        }
        return loggerInstance;
    }

    public void write(String action){
        Date date = new Date();
        try{
            loggerWriter.write(action + "," + date + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close(){
        try{
            loggerWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
