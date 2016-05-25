package org.nda.javafx.piradioplayer2.config;

import java.io.*;
import java.util.Properties;

/**
 * Created by NDA on 25.05.2016.
 */
public class MyConfig {

    public MyConfig(){}

    private Properties props = null;
    private static MyConfig instance = null;
    private String _file;


    public static MyConfig getInstance(String file){
        if (instance == null) {
            instance = new MyConfig(file);
        }
        return instance;
    }

    public static MyConfig getInstance(){
        return instance;
    }


    private MyConfig(String file){
        File configFile = new File(file);
        this._file = file;
        try {
            FileReader reader = new FileReader(configFile);
            props = new Properties();
            props.load(reader);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String property){
        return props.getProperty(property);
    }

    public void setProperty(String property, String value){
        props.setProperty(property, value);
    }

    public void save(){
        try {
            FileWriter writer = new FileWriter(this._file);
            props.store(writer, "host settings");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
