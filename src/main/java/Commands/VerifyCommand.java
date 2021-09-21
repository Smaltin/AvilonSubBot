package Commands;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Objects;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class VerifyCommand extends AbstractCommand {

    private static final String[] joinMessages = new String[]{" just joined. Can I get a heal?", " is here. Welcome!", " we hope you brought pizza.", " leave your weapons by the door.", " appeared. Welcome!", " showed up!", " just arrived, wellcum!"};
    private final Random random = new Random(System.currentTimeMillis());
    public static final String VERIFY_CHANNEL_ID = getEnv("VERIFY_CHANNEL_ID");
    public static final String VERIFY_PASSWORD = getEnv("VERIFY_PASSWORD");
    public static final String VERIFY_REMOVE_ROLE_ID = getEnv("VERIFY_REMOVE_ROLE_ID");
    public static final String VERIFY_GENERAL_CHANNEL_ID = getEnv("VERIFY_GENERAL_CHANNEL_ID");

    @Override
    public String getCommand() {
        return "verify";
    }

    @Override
    public void runCommand(JDA client, MessageReceivedEvent event, Message msg) {
        Member author = msg.getMember();
        assert author != null;
        if (!msg.getChannel().getId().equals(VERIFY_CHANNEL_ID)) {
            return;
        }

        String[] args = msg.getContentRaw().split(" ");
        if (args.length == 2) {
            if (args[1].equals(VERIFY_PASSWORD)) {
                author.getGuild().removeRoleFromMember(author, Objects.requireNonNull(author.getGuild().getRoleById(VERIFY_REMOVE_ROLE_ID))).submit();
                TextChannel general = (TextChannel) client.getGuildChannelById(VERIFY_GENERAL_CHANNEL_ID);
                assert general != null;
                general.sendMessage(author.getAsMention() + joinMessages[random.nextInt(joinMessages.length)]).queue((result) -> {
                }, (failure) -> System.out.println("Message failed: " + Arrays.toString(failure.getStackTrace())));
            } else { //.delete().queueAfter(delay, TimeUnit.SECONDS); TODO make it delete message after 5 or so seconds
                msg.getChannel().sendMessage(author.getAsMention() + ", wrong password given.").queue((result) -> {
                    result.delete().queueAfter(5, TimeUnit.SECONDS);
                }, (failure) -> {
                    System.out.println("Message failed: " + Arrays.toString(failure.getStackTrace()));
                });
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
            loadProps.load(new FileInputStream("settings.env"));
            return loadProps.getProperty(key);
        } catch (Exception e) {
            e.printStackTrace();
            return "No, you're bad.";
        }
    }
}