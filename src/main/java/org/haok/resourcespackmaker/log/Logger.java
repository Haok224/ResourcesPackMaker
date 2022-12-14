package org.haok.resourcespackmaker.log;

import org.haok.resourcespackmaker.util.Util;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Logger {
    private PrintStream stream;
    private final SimpleDateFormat format;
    public Logger() {
        this.format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
        try {
            File path = new File("log");
            System.out.println("make log dir:"+path.mkdirs());
            File file = new File("log/"+new SimpleDateFormat("yyyy-MM-dd_HH：mm：ss：SS").format(new Date())+".log");
            System.out.println("create log file:"+file.createNewFile());
            stream = new PrintStream(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        File[] fileList = new File("log/").listFiles();
        int logFile = 0;
        if (fileList != null) {
            for (File file : fileList) {
                String path = file.getPath();
                if (Util.getFileType(path).equals("log")){
                    logFile++;
                }
            }
        }
        System.out.println("log file amount:"+logFile);
        if (logFile >= 10){
            System.out.println("log file amount >= 10.create zip");
            Util.toZip("log/"+new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss_SS").format(new Date())+".zip", List.of(fileList));
        }
    }
    public void info(Object o){
        stream.println(format.format(new Date().getTime())+" [INFO] "+o);
        System.out.println(format.format(new Date().getTime())+" [INFO] "+o);
    }
    public void warn(Throwable t){
        stream.println(format.format(new Date().getTime())+" [WARN] ");
        t.printStackTrace(stream);
        System.err.println(format.format(new Date().getTime())+" [WARN] ");
        t.printStackTrace(System.err);
    }
}
