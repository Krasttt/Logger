package com.nc.logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigReader {
    private static Map<String,String> map = new HashMap();

    static {
        Properties prop = new Properties();
        InputStream inputStream = null;
        try {
            String filename = "config.properties";
            inputStream = Main.class.getClassLoader().getResourceAsStream(filename);
            if (inputStream == null) {
                System.out.println("Sorry, unable to find " + filename);
            }
            prop.load(inputStream);

            map.put("threshold",prop.getProperty("threshold").toUpperCase());
            if (map.get("threshold")==null){
                map.put("threshold","DEBUG");
            }
            map.put("input",prop.getProperty("input"));
            if (map.get("input")==null){
                map.put("input","console");
            }
            if (map.get("input").equals("database")){
                map.put("url",prop.getProperty("url"));
                map.put("user",prop.getProperty("user"));
                map.put("password",prop.getProperty("password"));
                map.put("appName",prop.getProperty("appName"));
            }
            if (map.get("input").equals("file")){
                map.put("fileName",prop.getProperty("fileName"));
                map.put("path",prop.getProperty("path"));
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
    }

    public static String getProperty(String prop){
        return map.get(prop);
    }
}
