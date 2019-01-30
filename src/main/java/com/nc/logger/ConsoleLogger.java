package com.nc.logger;

import java.sql.Date;
import java.util.Calendar;

public class ConsoleLogger implements Logger {
    private final Class logClass;
    private final Level threshold;

    public ConsoleLogger(Class<?> clazz, String threshold) {
        logClass = clazz;
        this.threshold = Level.valueOf(threshold);
    }

    @Override
    public void debug(String msg) {
        if (Level.DEBUG.ordinal() >= threshold.ordinal()) {
            Date date = new Date(Calendar.getInstance().getTimeInMillis());
            print(Level.DEBUG, msg, date);
        } else return;
    }

    @Override
    public void info(String msg) {
        if (Level.INFO.ordinal() >= threshold.ordinal()) {
            Date date = new Date(Calendar.getInstance().getTimeInMillis());
            print(Level.INFO, msg, date);
        } else return;

    }

    @Override
    public void warn(String msg) {
        if (Level.WARNING.ordinal() >= threshold.ordinal()) {
            Date date = new Date(Calendar.getInstance().getTimeInMillis());
            print(Level.WARNING, msg, date);
        } else return;
    }

    @Override
    public void error(String msg) {
        if (Level.ERROR.ordinal() >= threshold.ordinal()) {
            Date date = new Date(Calendar.getInstance().getTimeInMillis());
            print(Level.ERROR, msg, date);
        } else return;
    }

    @Override
    public void fatal(String msg) {
        if (Level.FATAL.ordinal() >= threshold.ordinal()) {
            Date date = new Date(Calendar.getInstance().getTimeInMillis());
            print(Level.FATAL, msg, date);
        } else return;
    }

    private void print(Level level, String msg, Date date) {
        System.out.println(date + " "
                + logClass.getCanonicalName() + "   Thread: " + Thread.currentThread().getName() + "\n" + level.name() + ": " + msg + "\n");
    }
}
