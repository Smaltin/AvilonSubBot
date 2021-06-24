package Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.Random;

public class PatCommand extends AbstractCommand {

    private static final String[] patImages = new String[]{"https://media.giphy.com/media/ARSp9T7wwxNcs/giphy.gif","https://media.tenor.com/images/a671268253717ff877474fd019ef73e9/tenor.gif","https://media.tenor.com/images/1f884a2d953bcb2ce32b02e435bea8d7/tenor.gif","https://media.tenor.com/images/943a52d38d896bda734a6396b1ffca89/tenor.gif","https://media.tenor.com/images/aa9cb58fc4dab5e1c6b9519009c4ada1/tenor.gif"};
    private final Random random = new Random(System.currentTimeMillis());

    @Override
    public String getCommand() {
        return "pat";
    }

    @Override
    public void runCommand(JDA client, MessageReceivedEvent event, Message msg) {
        EmbedBuilder builder = new EmbedBuilder();
        String mentionedUserPing;
        Member author = msg.getMember();
        assert author != null;
        String authorPing = author.getEffectiveName();
        if (event.getMessage().getMentionedMembers().isEmpty()) {
            mentionedUserPing = "themself";
        } else {
            Member target = msg.getMentionedMembers().get(0);
            mentionedUserPing = target.getEffectiveName();
        }

        builder.setImage(patImages[random.nextInt(patImages.length)]).setColor(Color.decode("#FF0000")).setDescription("**" + authorPing + "** patted **" + mentionedUserPing + "**");
        msg.getChannel().sendMessageEmbeds(builder.build()).submit();
    }
}