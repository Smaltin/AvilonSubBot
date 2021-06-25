import Commands.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.Nullable;

import javax.security.auth.login.LoginException;
import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class Main extends ListenerAdapter {
    public static final long CHANNEL_ID = Long.parseLong("855948494950498324");
    public static final String BOTKEY = getEnv("BOTKEY");
    public static final String YOUTUBE_CHANNEL = getEnv("YOUTUBE_CHANNEL");
    public static final String YOUTUBE_API_KEY = getEnv("YOUTUBE_API_KEY");
    public static final String API_ENDPOINT = "https://www.googleapis.com/youtube/v3/channels?part=statistics&id=" + YOUTUBE_CHANNEL + "&key=" + YOUTUBE_API_KEY;
    public static final HashMap<String, AbstractCommand> commands = new HashMap<>();
    public static final HashMap<String, AbstractCommand> commandsAlias = new HashMap<>();
    public static JDA holder;
    public static String postedSubCt;

    public static String prefix = "+";

    public static void main(String[] args) throws LoginException, InterruptedException {
        holder = JDABuilder.createDefault(BOTKEY).addEventListeners(new Main()).build();
        holder.awaitReady();
        loadCommands();
        SubCount thread = new SubCount();
        thread.start();
    }

    /**
     * Loads up all the commands from commands put into the list
     *
     * @author jojo2357
     */
    public static void loadCommands() {
        if (commands.keySet().size() > 0) return;
        List<Class<? extends AbstractCommand>> classes = Arrays.asList(CatCommand.class, VerifyCommand.class, PaciCommand.class, PatCommand.class, PingCommand.class, MemeCommand.class, KickCommand.class, BanCommand.class, HugCommand.class, KissCommand.class, AviTimeCommand.class, HowPogCommand.class);
        //TODO add help and eject commands
        for (Class<? extends AbstractCommand> s : classes) {
            try {
                if (Modifier.isAbstract(s.getModifiers())) {
                    continue;
                }
                AbstractCommand c = s.getConstructor().newInstance();
                if (!commands.containsKey(c.getCommand())) {
                    commands.put(c.getCommand(), c);
                }
                for (String alias : c.getAliases()) {
                    if (!commandsAlias.containsKey(alias)) {
                        commandsAlias.put(alias, c);
                    }
                }
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Searches through registered commands and gets the command matching incomming message
     *
     * @param message either the full incomming message or target string that a command should be extracted from
     * @return a command fitting the passed string, null if no matches
     * @author jojo2357
     */
    @Nullable
    public static AbstractCommand getCommand(String message) {
        message = message.replace(prefix, "").split(" ")[0];
        if (commands.containsKey(message))
            return commands.get(message);
        if (commandsAlias.containsKey(message))
            return commandsAlias.get(message);
        return null;
    }

    public static String getEnv(String key) {
        try {
            Properties loadProps = new Properties();
            loadProps.load(new FileInputStream("settings.env"));
            return loadProps.getProperty(key);
        } catch (Exception e) {
            e.printStackTrace();
            return "No, you're bad.";
        }
    }

    /**
     * Called by {@link JDA} when a new message is sent and visible to the discord bot
     *
     * @param message the incoming message
     * @author jojo2357
     */
    @Override
    public void onMessageReceived(MessageReceivedEvent message) {
        if (message.getAuthor().isBot())
            return;
        if (!message.isFromGuild())
            return;

        Message msg = message.getMessage();

        if (!(msg.getContentRaw().length() > 0)) {
            return;
        }

        if (msg.getContentRaw().startsWith(prefix) && getCommand(msg.getContentRaw()) != null) {
            System.out.println("Recieved Message: " + message.getMessage().getContentRaw());
            AbstractCommand command = getCommand(message.getMessage().getContentRaw());
            if (command == null)
                return;
            command.runCommand(holder, message, msg);
        }
    }
}