package com.cicdez.sys;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LoggerFile {
    private final Class<?> clazz;
    private static String log = "";
    public LoggerFile(Class<?> clazz) {
        this.clazz = clazz;
    }

    public void info(Object msg) {
        log += "<" + date() + "> (" + Thread.currentThread().getName() + ") [" + clazz.getName() + "]: "
                + msg.toString() + "\n";
        System.out.println("<" + date() + "> (" + Thread.currentThread().getName() + ") [" +
                clazz.getName() + "]: " + msg.toString());
    }
    public void warning(Throwable throwable) {
        StringWriter writer = new StringWriter();
        throwable.printStackTrace(new PrintWriter(writer));
        info(throwable.getMessage());
        log += writer.toString() + "\n";
    }

    public static void create() {
        try {
            File file = new File(Main.LOGS, fileDate() + ".log");
            FileOutputStream stream = new FileOutputStream(file);
            stream.write(log.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static final Calendar CALENDAR = Calendar.getInstance();
    private static final SimpleDateFormat TIME = new SimpleDateFormat("HH:mm:ss.SSS");
    private static final SimpleDateFormat DATE = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
    private static String date() {
        return TIME.format(CALENDAR.getTime());
    }
    private static String fileDate() {
        return DATE.format(CALENDAR.getTime());
    }
}
