package io.github.Smaltin.AvilonSubBot;

import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class Configuration {
    public static boolean DEVELOPER_MODE;

    public static String SETTINGS_FILEPATH;
    public static long CHANNEL_ID;
    public static String BOTKEY;
    public static String YOUTUBE_CHANNEL;
    public static String YOUTUBE_API_KEY;
    public static String API_ENDPOINT;
    public static String SUBSCRIBER_NAME;
    public static String VERIFY_CHANNEL_ID;
    public static String VERIFY_PASSWORD;
    public static String VERIFY_REMOVE_ROLE_ID;
    public static String VERIFY_GENERAL_CHANNEL_ID;
    public static String PREFIX;

    /**
     * Enters into the settings.env file and pulls out the value you set
     *
     * @param key The key to check under
     * @return the string value from the key
     */
    public static String getEnv(String key) {
        try {
            Properties loadProps = new Properties();
            loadProps.load(Files.newInputStream(Paths.get(SETTINGS_FILEPATH)));
            return loadProps.getProperty(key);
        } catch (Exception e) {
            e.printStackTrace();
            return "No, you're bad.";
        }
    }

    public static void updateEnv() {
        DEVELOPER_MODE = Boolean.parseBoolean(getEnv("DEV"));
        CHANNEL_ID = Long.parseLong(getEnv("SUBCT_CHANNEL_ID"));
        BOTKEY = getEnv("BOTKEY");
        YOUTUBE_CHANNEL = getEnv("YOUTUBE_CHANNEL");
        YOUTUBE_API_KEY = getEnv("YOUTUBE_API_KEY");
        API_ENDPOINT = "https://www.googleapis.com/youtube/v3/channels?part=statistics&id=" + YOUTUBE_CHANNEL + "&key=" + YOUTUBE_API_KEY;
        SUBSCRIBER_NAME = getEnv("SUBSCRIBER_NAME");
        VERIFY_CHANNEL_ID = getEnv("VERIFY_CHANNEL_ID");
        VERIFY_PASSWORD = getEnv("VERIFY_PASSWORD");
        VERIFY_REMOVE_ROLE_ID = getEnv("VERIFY_REMOVE_ROLE_ID");
        VERIFY_GENERAL_CHANNEL_ID = getEnv("VERIFY_GENERAL_CHANNEL_ID");
        PREFIX = getEnv("PREFIX");
    }
}
