package com.nc.logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;

public class FileLogger implements Logger {
    private final Class logClass;
    private final Level threshold;
    private FileWriter writer;

    public FileLogger(Class<?> clazz, String threshold, String fileName, String path) {
        logClass = clazz;
        this.threshold = Level.valueOf(threshold);
        File myFile;

        if (fileName == null) {
            fileName = "logs.txt";
        }
        if (path == null) {
            myFile = new File(fileName);
        } else {
            myFile = new File(path, fileName);
        }
        if (!myFile.exists()) {
            try {
                myFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            writer = new FileWriter(myFile, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void debug(String msg) {
        if (Level.DEBUG.ordinal() >= threshold.ordinal()) {
            Date date = new Date(Calendar.getInstance().getTimeInMillis());
            write(Level.DEBUG, msg, date);
        } else return;

    }

    @Override
    public void info(String msg) {
        if (Level.INFO.ordinal() >= threshold.ordinal()) {
            Date date = new Date(Calendar.getInstance().getTimeInMillis());
            write(Level.INFO, msg, date);
        } else return;
    }

    @Override
    public void warn(String msg) {
        if (Level.WARNING.ordinal() >= threshold.ordinal()) {
            Date date = new Date(Calendar.getInstance().getTimeInMillis());
            write(Level.WARNING, msg, date);
        } else return;
    }

    @Override
    public void error(String msg) {
        if (Level.ERROR.ordinal() >= threshold.ordinal()) {
            Date date = new Date(Calendar.getInstance().getTimeInMillis());
            write(Level.ERROR, msg, date);
        } else return;
    }

    @Override
    public void fatal(String msg) {
        if (Level.FATAL.ordinal() >= threshold.ordinal()) {
            Date date = new Date(Calendar.getInstance().getTimeInMillis());
            write(Level.FATAL, msg, date);
        } else return;
    }

    private void write(Level level, String msg, Date date) {
        try {
            writer.append(date + " "
                    + logClass.getCanonicalName() + "   Thread: " + Thread.currentThread().getName() + "\n" + level.name() + ": " + msg + "\n\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
