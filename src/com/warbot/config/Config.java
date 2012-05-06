package com.warbot.config;

import java.io.FileInputStream;
import java.util.Properties;


/**
 * Created with IntelliJ IDEA.
 * User: han han
 * Date: 5/5/12
 * Time: 8:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class Config {
    private static Config instance;
    private static String userId;
    private static String password;
    private static String baseUrl;
    private static String configFilePath = "src/com/warbot/config/warbot.properties";

    private Config(){
        //empty constructor
        Properties configFile = new Properties();
        try{
            FileInputStream stream = new FileInputStream(configFilePath);
            configFile.load(stream);
            userId = configFile.getProperty("userId");
            password = configFile.getProperty("password");
            baseUrl = configFile.getProperty("baseUrl");
        } catch (Exception e){

            e.printStackTrace();
            //System.out.println("Unable to read config file: " + configFilePath);
            System.exit(1);
        }
    }

    private static Config getConfig(){
        if(instance == null){
            instance = new Config();
        }
        return instance;
    }

    public static String getUserId(){
        return getConfig().userId;
    }

    public static String getPassword(){
        return getConfig().password;
    }

    public static String getBaseUrl(){
        return getConfig().baseUrl;
    }

}
