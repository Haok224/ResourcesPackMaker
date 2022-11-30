package org.haok.resourcespackmaker.log;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@SuppressWarnings("ALL")
public class LogPrintStream extends PrintStream {
    private final Date date = new Date();
    private String className;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss:SS");

    public LogPrintStream(OutputStream out, Class c) throws UnsupportedEncodingException {
        super(out, true, "UTF-8");
        this.className = c.getName();
    }

    public LogPrintStream() {
        super(System.out);
    }

    @Override
    public void println() {
        super.println();
    }

    @Override
    public void println(boolean x) {
        super.println("[INFO] " + format.format(new Date()) + " " + className + " " + x);
        flush();
        System.out.println("[INFO] " + format.format(new Date()) + " " + className + " " + x);
    }

    @Override
    public void println(char x) {
        super.println("[INFO] " + format.format(new Date()) + " " + className + " " + x);
        flush();
        System.out.println("[INFO] " + format.format(new Date()) + " " + className + " " + x);
    }

    @Override
    public void println(int x) {
        super.println("[INFO] " + format.format(new Date()) + " " + className + " " + x);
        flush();
        System.out.println("[INFO] " + format.format(new Date()) + " " + className + " " + x);
    }

    @Override
    public void println(long x) {
        super.println("[INFO] " + format.format(new Date()) + " " + className + " " + x);
        flush();
        System.out.println("[INFO] " + format.format(new Date()) + " " + className + " " + x);
    }

    @Override
    public void println(float x) {
        super.println("[INFO] " + format.format(new Date()) + " " + className + " " + x);
        flush();
        System.out.println("[INFO] " + format.format(new Date()) + " " + className + " " + x);
    }

    @Override
    public void println(double x) {
        super.println("[INFO] " + format.format(new Date()) + " " + className + " " + x);
        flush();
        System.out.println("[INFO] " + format.format(new Date()) + " " + className + " " + x);
    }

    @Override
    public void println(char[] x) {
        super.println("[INFO] " + format.format(new Date()) + " " + className + " " + Arrays.toString(x));
        flush();
        System.out.println("[INFO] " + format.format(new Date()) + " " + className + " " + x);
    }

    @Override
    public void println(String x) {
        super.println("[INFO] " + format.format(new Date()) + " " + className + " " + x);
        flush();
        System.out.println("[INFO] " + format.format(new Date()) + " " + className + " " + x);
    }

    @Override
    public void println(Object x) {
        String s = x == null ? "null" : x.toString();
        super.println("[INFO] " + format.format(new Date()) + " " + className + " " + s);
        flush();
        System.out.println("[INFO] " + format.format(new Date()) + " " + className + " " + s);
    }

    public void warn(Throwable t) {
        if (t == null) {
            throw new IllegalArgumentException("t is null");
        }
        super.println("[WARN] " + format.format(new Date()) + " " + className + " " + t.getMessage() + " " + t);
        flush();
        System.out.println("[WARN] " + format.format(new Date()) + " " + className + " " + t.getMessage() + " " + t);
    }
}
