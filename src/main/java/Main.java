import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class Main {
    public static final long CHANNEL_ID = Long.parseLong("855909993806037003");
    private static final String BOTKEY = "ODU1ODk3Nzc2MTI1NjQwNzA0.YM5K6w.0klOwSA3y8KES1f6llCCahoZvlw";
    private static final String YOUTUBE_CHANNEL = "UCDfhh-KjgxjW_PJCcsU226g";
    private static final String YOUTUBE_API_KEY = "AIzaSyApSt7agFderSClMv0rIBWx_x-0Y1aVv8E";
    public static final String API_ENDPOINT = "https://www.googleapis.com/youtube/v3/channels?part=statistics&id=" + YOUTUBE_CHANNEL + "&key=" + YOUTUBE_API_KEY;
    public static JDA holder;
    public static String postedSubCt;

    public static void main(String[] args) throws LoginException {
        holder = JDABuilder.createDefault(BOTKEY).build();
        SubCount thread = new SubCount();
        thread.start();
    }
}
