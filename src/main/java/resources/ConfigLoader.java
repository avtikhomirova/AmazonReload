package resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {
    private Properties properties;

    public ConfigLoader() {
        FileInputStream inputStream;
        properties = new Properties();
        try {
            inputStream = new FileInputStream("src/main/java/resources/configuration.properties"); // Put the path of your file here
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key){
        return this.properties.getProperty(key);
    }

    public int getIntProperty(String key){
        String value = this.properties.getProperty(key);
        return Integer.parseInt(value);
    }
}

