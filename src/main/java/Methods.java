import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.managers.ChannelManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Methods {

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
        String webRequest = Main.API_ENDPOINT;
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
            System.out.println("GET request not worked");
            return "ERR_1";
        }
    }
}