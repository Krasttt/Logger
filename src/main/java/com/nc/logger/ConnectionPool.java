package com.nc.logger;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {
    private HikariConfig conf;
    private HikariDataSource dataSource;

    public ConnectionPool(){
        conf = new HikariConfig();
        conf.setJdbcUrl(ConfigReader.getProperty("url"));
        conf.setUsername(ConfigReader.getProperty("user"));
        conf.setPassword(ConfigReader.getProperty("password"));
        dataSource = new HikariDataSource(conf);
    }

    public Connection getConnection(){
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
