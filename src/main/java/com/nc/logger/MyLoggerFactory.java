package com.nc.logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MyLoggerFactory {
    public static Logger getLogger(Class<?> clazz){
        Properties prop = new Properties();
        InputStream inputStream = null;

        try {
            String filename = "config.properties";
            inputStream = Main.class.getClassLoader().getResourceAsStream(filename);
            if (inputStream == null) {
                System.out.println("Sorry, unable to find " + filename);
            }
            prop.load(inputStream);

            String threshold = prop.getProperty("threshold");
            if (threshold==null){
                threshold = "DEBUG";
            }

            String input = prop.getProperty("input");
            if (input==null){
                input = "console";
            }
            if (input.equals("console")){
                return new ConsoleLogger(clazz,threshold.toUpperCase());
            }
            if (input.equals("database")){
                String url = prop.getProperty("url");
                String user = prop.getProperty("user");
                String password = prop.getProperty("password");
                String appName = prop.getProperty("appName");
                return new DBLogger(clazz,url,user,password,appName,threshold.toUpperCase());
            }
            if (input.equals("file")){
                String fileName = prop.getProperty("fileName");
                String path = prop.getProperty("path");
                return new FileLogger(clazz,threshold.toUpperCase(),fileName,path);
            }


        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
