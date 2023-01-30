package com.elong.test.japi.utils;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.Reporter;

public class Logger {
    public static enum Level {
        INFO("INFO"),
        WARN("WARN"),
        ERROR("ERROR");

        private String value;

        Level(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }

        public static Level fromString(String value) {
            if (null != value) {
                for (Level l : Level.values()) {
                    if (value.equals(l.value)) {
                        return l;
                    }
                }
            }
            return null;
        }
    }

    public static void info(String msg) throws UnsupportedEncodingException {
        log(Level.INFO, msg);
    }

    public static void warn(String msg) throws UnsupportedEncodingException {
        log(Level.WARN, msg);
    }

    public static void error(String msg) throws UnsupportedEncodingException {
        log(Level.ERROR, msg);
    }

    public static void log(Level level, String msg) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sb.append(sdf.format(new Date()));
        sb.append("\t");
        sb.append(level.value);
        sb.append("\t--\t");
        sb.append(msg);
        Reporter.log(sb.toString());
    }
}
