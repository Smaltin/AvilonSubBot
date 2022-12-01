package io.github.Smaltin.AvilonSubBot.Commands.Music;

import io.github.Smaltin.AvilonSubBot.Commands.AbstractCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class QueueCommand extends AbstractCommand {
    @Override
    public String getCommand() {
        return "queue";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"q", "upcoming"};
    }

    @Override
    public String getArgs() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public void runCommand(JDA client, MessageReceivedEvent event, Message msg) {

    }

    @Override
    public void runCommand(JDA client, SlashCommandEvent event) {

    }
}
