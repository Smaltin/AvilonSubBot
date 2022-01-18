package io.github.Smaltin.AvilonSubBot.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.Random;

public class MemeCommand extends AbstractCommand {

    private final Random random = new Random(System.currentTimeMillis());

    @Override
    public String getCommand() {
        return "gibmeme";
    }

    @Override
    public String getArgs() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Sends a random meme";
    }

    @Override
    public void runCommand(JDA client, MessageReceivedEvent event, Message msg) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setImage("https://api.cool-img-api.ml/meme?randomize=" + random.nextInt(99999999)).setColor(Color.decode("#ff0000"));
        msg.getChannel().sendMessageEmbeds(builder.build()).submit();
    }

    @Override
    public String[] getAliases() {
        return new String[]{"givememe", "meme", "randommeme", "rmeme"};
    }
}