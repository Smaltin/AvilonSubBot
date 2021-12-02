package com.github.Smaltin;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.managers.ChannelManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.Instant;

import static com.github.Smaltin.Configuration.*;

public class SubCountRenamer {

    public static void setChannelName(long channelId, String channelName, boolean tryAgain) throws InterruptedException {
        GuildChannel channel = Main.holder.getGuildChannelById(channelId);
        ChannelManager channelManager;
        if (channel != null) {
            channelManager = channel.getManager();
            channelManager.setName(channelName).queue();
        } else {
            if (!tryAgain) {
                Thread.sleep(1000);
                setChannelName(channelId, channelName, true);
            } else {
                System.out.println("Cannot set channel name to '" + channelName + "' for " + channelId);
            }
        }
    }

    public static String getSubscriberCount() throws IOException {
        String webRequest = API_ENDPOINT;
        URL url = new URL(webRequest);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("accept", "application/json");
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            JsonObject rootObj = JsonParser.parseString(response.toString()).getAsJsonObject(); //getAsJsonObject("statistics").get("subscriberCount")
            JsonObject rootItems = rootObj.getAsJsonArray("items").get(0).getAsJsonObject().getAsJsonObject("statistics");
            return rootItems.get("subscriberCount").getAsString();
        } else {
            System.out.println("GET request failed. You should GET better.");
            return "ERR_1";
        }
    }

    public static class SubCounter extends Thread {

        @Override
        public void run() {
            while (true) {
                try {
                    String subs = getSubscriberCount();
                    Long subsLong = Long.parseLong(subs);
                    DecimalFormat myFormatter = new DecimalFormat("###,###");
                    if (!subs.equals(Main.postedSubCt)) {
                        setChannelName(CHANNEL_ID, SUBSCRIBER_NAME + ": " + myFormatter.format(subsLong), false);
                        Main.postedSubCt = subs;
                        System.out.println(Timestamp.from(Instant.now()) + "[Changed] " + myFormatter.format(subsLong) + " " + SUBSCRIBER_NAME);
                    } else {
                        System.out.println(Timestamp.from(Instant.now()) + "[No Change] " + myFormatter.format(subsLong) + " " + SUBSCRIBER_NAME);
                    }
                    Thread.sleep(60000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}