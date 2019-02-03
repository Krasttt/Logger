package com.nc.logger;

import java.sql.*;
import java.util.Calendar;

public class DBLogger implements Logger {
    private static final String INSERT_LOG = "insert into \"Log\"(level_id, app_id, message, class,thread,date)" +
            " values (?,?,?,?,?,?)";
    private static final String INSERT_APP = "insert into \"App\"(name) values (?)";
    private static final String SELECT_LEVEL = "select id from \"Level\" where name = ?";
    private static final String SELECT_APP = "select id from \"App\" where name = ?";

    private final Class logClass;
    private final Level threshold;
    private int appId;
    private ConnectionPool connectionPool;

    public DBLogger(Class<?> clazz,String appName, String threshold) {

        connectionPool = ConnectionPool.getInstance();
        logClass = clazz;
        this.threshold = Level.valueOf(threshold);
        appId = selectAppId(appName);

    }

    private int selectAppId(String appName) {
        try (Connection db = connectionPool.getConnection();
             PreparedStatement selectAppPreparedStatement = db.prepareStatement(SELECT_APP);
             PreparedStatement insertAppPreparedStatement = db.prepareStatement(INSERT_APP)){

            selectAppPreparedStatement.setString(1, appName);
            ResultSet resultSet = selectAppPreparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            insertAppPreparedStatement.setString(1, appName);
            insertAppPreparedStatement.execute();

            selectAppPreparedStatement.setString(1, appName);
            ResultSet resultSetNew = selectAppPreparedStatement.executeQuery();
            if (resultSetNew.next()) {
                return resultSetNew.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void debug(String msg) {
        if (Level.DEBUG.ordinal() >= threshold.ordinal()) {
            Timestamp date = new Timestamp(Calendar.getInstance().getTimeInMillis());
            preparedStatementExecute(Level.DEBUG, msg, date);
        } else return;
    }

    public void info(String msg) {
        if (Level.INFO.ordinal() >= threshold.ordinal()) {
            Timestamp date = new Timestamp(Calendar.getInstance().getTimeInMillis());
            preparedStatementExecute(Level.INFO, msg, date);
        } else return;
    }

    public void warn(String msg) {
        if (Level.WARNING.ordinal() >= threshold.ordinal()) {
            Timestamp date = new Timestamp(Calendar.getInstance().getTimeInMillis());
            preparedStatementExecute(Level.WARNING, msg, date);
        } else return;
    }

    public void error(String msg) {
        if (Level.ERROR.ordinal() >= threshold.ordinal()) {
            Timestamp date = new Timestamp(Calendar.getInstance().getTimeInMillis());
            preparedStatementExecute(Level.ERROR, msg, date);
        } else return;
    }

    public void fatal(String msg) {
        if (Level.FATAL.ordinal() >= threshold.ordinal()) {
            Timestamp date = new Timestamp(Calendar.getInstance().getTimeInMillis());
            preparedStatementExecute(Level.FATAL, msg, date);
        } else return;
    }

    private void preparedStatementExecute(Level level, String msg, Timestamp date) {
        try (Connection db = connectionPool.getConnection();
             PreparedStatement selectLevelPreparedStatement = db.prepareStatement(SELECT_LEVEL);
             PreparedStatement insertLogPreparedStatement = db.prepareStatement(INSERT_LOG)) {

            selectLevelPreparedStatement.setString(1, level.name());
            ResultSet resultSet = selectLevelPreparedStatement.executeQuery();
            if (resultSet.next()) {
                int level_id = resultSet.getInt(1);
                insertLogPreparedStatement.setInt(1, level_id);
            }
            insertLogPreparedStatement.setInt(2, appId);
            insertLogPreparedStatement.setString(3, msg);
            insertLogPreparedStatement.setString(4, logClass.getCanonicalName());
            insertLogPreparedStatement.setString(5, Thread.currentThread().getName());
            insertLogPreparedStatement.setTimestamp(6, date);

            insertLogPreparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
