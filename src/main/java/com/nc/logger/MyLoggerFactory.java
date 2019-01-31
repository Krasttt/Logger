package com.nc.logger;

public class MyLoggerFactory {
    public static Logger getLogger(Class<?> clazz){

        if (ConfigReader.getProperty("input").equals("file")){
            return  new FileLogger(clazz,ConfigReader.getProperty("threshold"),
                    ConfigReader.getProperty("fileName"),ConfigReader.getProperty("path"));
        }

        if (ConfigReader.getProperty("input").equals("database")){
            return new DBLogger(clazz,ConfigReader.getProperty("appName"),ConfigReader.getProperty("threshold"));
        }

        return new ConsoleLogger(clazz,ConfigReader.getProperty("threshold"));
    }
}
