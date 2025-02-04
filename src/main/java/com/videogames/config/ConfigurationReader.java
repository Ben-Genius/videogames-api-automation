package com.videogames.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationReader {
    private static final Properties properties = new Properties();
    
    static {
        try (InputStream input = ConfigurationReader.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("Unable to find config.properties");
            }
            properties.load(input);
        } catch (IOException ex) {
            throw new RuntimeException("Failed to load configuration file", ex);
        }
    }
    
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    public static String getBaseUrl() {
        return getProperty("api.base.url");
    }
    
    public static String getAuthEndpoint() {
        return getProperty("endpoint.auth");
    }
    
    public static String getVideoGamesEndpoint() {
        return getProperty("endpoint.videogames");
    }
}
