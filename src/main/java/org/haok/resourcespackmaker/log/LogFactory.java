package org.haok.resourcespackmaker.log;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class LogFactory {
    public static LogPrintStream getLogger(Class c) {
        File file = new File("log");
        System.out.println("make log dir:" + file.mkdirs());
        File file1 = new File("log/log.log");
        try {
            System.out.println("make log file:"+file1.createNewFile());
            return new LogPrintStream(new FileOutputStream(file1), c);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new LogPrintStream();
    }
}
