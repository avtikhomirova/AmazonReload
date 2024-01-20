package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private final static String CONFIG_PROPERTIES = "configuration.properties"; // Relative path
    private static volatile ConfigLoader config; // volatile for thread safety
    private Properties properties;

    private ConfigLoader() {
        properties = new Properties();
        loadProperties(CONFIG_PROPERTIES);
    }

    public static ConfigLoader getConfig() { // Public method
        if (config == null) {
            synchronized (ConfigLoader.class) { // Synchronized block for thread safety
                if (config == null) {
                    config = new ConfigLoader();
                }
            }
        }
        return config;
    }

    private void loadProperties(String fileName) {
        try (InputStream stream = ConfigLoader.class.getClassLoader().getResourceAsStream(fileName)) {
            if (stream == null) {
                throw new IOException("File not found " + fileName); // Throw exception if file not found
            }
            properties.load(stream);
        } catch (IOException e) {
            System.err.println("Error during file reading " + fileName);
            throw new RuntimeException(e);
        }
    }

    public String getProperty(String key) { // Non-static method
        String value = properties.getProperty(key);
        if (value == null) {
            throw new IllegalArgumentException("Property not found: " + key); // Handle missing property
        }
        return value;
    }
}
