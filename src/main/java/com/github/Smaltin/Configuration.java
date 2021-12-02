package com.github.Smaltin;

import java.io.FileInputStream;
import java.util.Properties;

public class Configuration {
    public static final boolean DEVELOPER_MODE = true;
    public static final long CHANNEL_ID = Long.parseLong(getEnv("SUBCT_CHANNEL_ID"));
    public static final String BOTKEY = getEnv("BOTKEY");
    public static final String YOUTUBE_CHANNEL = getEnv("YOUTUBE_CHANNEL");
    public static final String YOUTUBE_API_KEY = getEnv("YOUTUBE_API_KEY");
    public static final String API_ENDPOINT = "https://www.googleapis.com/youtube/v3/channels?part=statistics&id=" + YOUTUBE_CHANNEL + "&key=" + YOUTUBE_API_KEY;
    public static final String SUBSCRIBER_NAME = getEnv("SUBSCRIBER_NAME");
    public static final String VERIFY_CHANNEL_ID = getEnv("VERIFY_CHANNEL_ID");
    public static final String VERIFY_PASSWORD = getEnv("VERIFY_PASSWORD");
    public static final String VERIFY_REMOVE_ROLE_ID = getEnv("VERIFY_REMOVE_ROLE_ID");
    public static final String VERIFY_GENERAL_CHANNEL_ID = getEnv("VERIFY_GENERAL_CHANNEL_ID");

    /**
     * Enters into the settings.env file and pulls out the value you set
     *
     * @param key The key to check under
     * @return the string value from the key
     */
    public static String getEnv(String key) {
        try {
            Properties loadProps = new Properties();
            loadProps.load(new FileInputStream((DEVELOPER_MODE ? "dev-" : "") + "settings.env"));
            return loadProps.getProperty(key);
        } catch (Exception e) {
            e.printStackTrace();
            return "No, you're bad.";
        }
    }
}
