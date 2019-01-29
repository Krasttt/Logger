package com.nc.logger;

import java.sql.*;
import java.sql.Date;
import java.util.Calendar;

public class DBLogger  implements Logger{
    private final Class logClass;

    private static final String URL = "jdbc:postgresql://localhost:5432/LogDB";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1703";

    private static final String INSERT = "insert into \"Log\"(level_id, app_id, message, class, date,thread) values (?,?,?,?,?,?)";
    private static final String SELECT = "select id from \"Level\" where name = ?";

    private Connection connection;
    private PreparedStatement insertPreparedStatement;
    private PreparedStatement selectPreparedStatement;

    public DBLogger(Class<?> clazz){
        logClass = clazz;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            insertPreparedStatement = connection.prepareStatement(INSERT);
            selectPreparedStatement = connection.prepareStatement(SELECT);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void debug(String msg) {
        Date date = new Date(Calendar.getInstance().getTimeInMillis());
        preparedStatementExecute(Level.DEBUG, msg,date);
    }

    public void info(String msg) {
        Date date = new Date(Calendar.getInstance().getTimeInMillis());
        preparedStatementExecute(Level.INFO, msg,date);
    }

    public void error(String msg) {
        Date date = new Date(Calendar.getInstance().getTimeInMillis());
        preparedStatementExecute(Level.ERROR, msg,date);
    }

    public void warn(String msg) {
        Date date = new Date(Calendar.getInstance().getTimeInMillis());
        preparedStatementExecute(Level.WARNING, msg,date);
    }

    public void fatal(String msg) {
        Date date = new Date(Calendar.getInstance().getTimeInMillis());
        preparedStatementExecute(Level.FATAL, msg,date);
    }

    private void preparedStatementExecute(Level level, String msg,Date date) {
        try {
            selectPreparedStatement.setString(1, level.name());
            ResultSet resultSet = selectPreparedStatement.executeQuery();
            if (resultSet.next()) {
                int level_id = resultSet.getInt(1);
                insertPreparedStatement.setInt(1, level_id);
            }

            insertPreparedStatement.setInt(2, 1);
            insertPreparedStatement.setString(3, msg);
            insertPreparedStatement.setString(4, String.valueOf(logClass));
            insertPreparedStatement.setDate(5, date);
            insertPreparedStatement.setString(6,Thread.currentThread().getName());
            insertPreparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(new Date(Calendar.getInstance().getTimeInMillis()) + " "
                + logClass.getCanonicalName() + "\n"+level.name()+": " + msg);
    }
}
