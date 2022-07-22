package io.github.Smaltin.AvilonSubBot.Commands;

import io.github.Smaltin.AvilonSubBot.Commands.AbstractCommand;
import io.github.Smaltin.AvilonSubBot.Utilities;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import static io.github.Smaltin.AvilonSubBot.Configuration.DEVELOPER_MODE;
import static io.github.Smaltin.AvilonSubBot.Runner.CODE_VERSION;

public class DebugCommand extends AbstractCommand {
    @Override
    public String getCommand() {
        return "debug";
    }

    @Override
    public String getArgs() {
        return /*"(Optional) <@Mention>"*/null;
    }

    @Override
    public String getDescription() {
        return "Returns debug information about the user"/* (self or mentioned)*/+", potentially helps with fixing stuff";
    }

    @Override
    public void runCommand(JDA client, MessageReceivedEvent event, Message msg) {
        try {
            EmbedBuilder sendme = new EmbedBuilder();
            sendme.addField("User Role", Utilities.getUserRole(msg.getAuthor()).toString(), true);
            sendme.addField("Server Join Date", String.valueOf(msg.getMember().getTimeJoined()), true);
            sendme.addField("Discord Join Date", String.valueOf(msg.getAuthor().getTimeCreated()), true);
            //sendme.addField("Bot Uptime (DD:HH:MM:SS)", ) //TODO add uptime shit
            sendme.addField("Developer Mode", String.valueOf(DEVELOPER_MODE), true);
            sendme.addField("Code Version", CODE_VERSION, true);
            sendme.setFooter("Bot by Smaltin#2208");
            msg.replyEmbeds(sendme.build()).queue();
        } catch (Exception ignored) {
        }
    }
}