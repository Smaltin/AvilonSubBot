package Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.awt.*;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class HowPogCommand extends AbstractCommand {
    private final Random random = new Random(System.currentTimeMillis());

    @Override
    public String getCommand() {
        return "howpog";
    }

    @Override
    public void runCommand(JDA client, MessageReceivedEvent event, Message msg) {
        EmbedBuilder builder = new EmbedBuilder();

        if (event.getMessage().getMentionedMembers().isEmpty()) {
            String[] split = msg.getContentRaw().split(" ");
            if (split.length >= 2) {
                builder.setColor(Color.decode("#ff00ff")).setTitle("How Poggers?").setDescription("**" + String.join(" ", Arrays.copyOfRange(split, 1, split.length)) + "** is " + random.nextInt(100) + "% Poggers! \uD83D\uDE2F");
            }
        } else {
            Member target = msg.getMentionedMembers().get(0);
            builder.setColor(Color.decode("#ff00ff")).setTitle("How Poggers?").setDescription("**" + target.getEffectiveName() + "** is " + random.nextInt(100) + "% Poggers! \uD83D\uDE2F");
        }

        CompletableFuture<Message> sent = msg.getChannel().sendMessageEmbeds(builder.build()).submit();
        msg.addReaction("\uD83D\uDE2F").queue();
    }
}