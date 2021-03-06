package io.github.Smaltin.AvilonSubBot;

import io.github.Smaltin.AvilonSubBot.Commands.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.Nullable;

import javax.security.auth.login.LoginException;
import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class Runner extends ListenerAdapter {
    public static final HashMap<String, AbstractCommand> commands = new HashMap<>();
    public static final HashMap<String, AbstractCommand> commandsAlias = new HashMap<>();
    public static final String PREFIX = getEnv("PREFIX");
    public static final String CODE_VERSION = "0.0.1";
    public static JDA holder;
    public static String postedSubCt;

    public static void main(String[] args) throws LoginException, InterruptedException {
        Configuration.DEVELOPER_MODE = (args[0].equals("true"));
        while (true) {
            if (isInternetWorking())
                break;
        }
        holder = JDABuilder.createDefault(Configuration.BOTKEY).addEventListeners(new Runner()).build();
        holder.awaitReady();
        loadCommands();
        holder.getPresence().setActivity(Activity.listening("Avilon's Music"));
        SubCountRenamer.SubCounter thread = new SubCountRenamer.SubCounter();
        thread.start();
    }

    /**
     * Checks if the internet is working by connecting to http://google.com
     *
     * @return true if you can connect to http://google.com
     * @author Smaltin
     */
    public static boolean isInternetWorking() {
        try {
            URL url = new URL("https://www.google.com");
            URLConnection connection = url.openConnection();
            connection.connect();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Loads up all the commands from commands put into the list
     *
     * @author jojo2357
     */
    public static void loadCommands() {
        if (commands.keySet().size() > 0) return;
        List<Class<? extends AbstractCommand>> classes = Arrays.asList(RestartCommand.class, CatCommand.class, VerifyCommand.class, PaciCommand.class, PatCommand.class, PingCommand.class, MemeCommand.class, KickCommand.class, BanCommand.class, HugCommand.class, KissCommand.class, AviTimeCommand.class, HowPogCommand.class, HelpCommand.class);
        for (Class<? extends AbstractCommand> s : classes) { //TODO add "eject" and sus commands
            //TODO add music
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
     * Enters into the settings.env file and pulls out the value you set
     *
     * @param key The key to check under
     * @return the string value from the key
     */
    public static String getEnv(String key) {
        try {
            Properties loadProps = new Properties();
            loadProps.load(new FileInputStream((Configuration.DEVELOPER_MODE ? "dev-" : "") + "settings.env"));
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

        if (msg.getContentRaw().startsWith(PREFIX) && getCommand(msg.getContentRaw()) != null) {
            System.out.println("Recieved Message: " + message.getMessage().getContentRaw());
            AbstractCommand command = getCommand(message.getMessage().getContentRaw());
            if (command == null)
                return;
            command.runCommand(holder, message, msg);
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
        message = message.replace(PREFIX, "").split(" ")[0];
        if (commands.containsKey(message))
            return commands.get(message);
        if (commandsAlias.containsKey(message))
            return commandsAlias.get(message);
        return null;
    }
}