package ilab.utility;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ConfigData {

    Properties properties;

    public  ConfigData(){

    File src  = new File("Config_File/Config.properties");

    try{
        FileInputStream fileInputStream = new FileInputStream(src);

        properties = new Properties();
        properties.load(fileInputStream);
    }catch(Exception e){
        System.out.println("Failed to load Config file " + e.getMessage());
    }
    }

    public String getURL(){
        return properties.getProperty("ilabCareerURL");
    }
    public String getBrowser(){
        return properties.getProperty("browser");

    }

}
