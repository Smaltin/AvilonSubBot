package io.github.Smaltin.AvilonSubBot.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class AviTimeCommand extends AbstractCommand {

    @Override
    public String getCommand() {
        return "avitime";
    }

    @Override
    public String getArgs() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Displays Avilon's time in hr:min:sec format";
    }

    @Override
    public void runCommand(JDA client, MessageReceivedEvent event, Message msg) {
        EmbedBuilder builder = new EmbedBuilder();

        Date date = new Date();
        DateFormat df = new SimpleDateFormat("kk:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("Africa/Tunis"));
        String time = df.format(date);
        builder.setColor(Color.decode("#00FF0")).setDescription("**" + time + "**");
        msg.getChannel().sendMessageEmbeds(builder.build()).submit();
    }
}