package io.github.Smaltin.AvilonSubBot.Commands.Music;

import io.github.Smaltin.AvilonSubBot.Commands.AbstractCommand;
import io.github.Smaltin.AvilonSubBot.MusicUtilities.MusicUtilities;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class SkipCommand extends AbstractCommand {
    @Override
    public String getCommand() {
        return "skip";
    }

    @Override
    public String getArgs() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Skips the currently playing song and plays the next if it exists";
    }

    @Override
    public void runCommand(JDA client, MessageReceivedEvent event, Message msg) {
        MusicUtilities.skipTrack(msg.getTextChannel());
    }

    @Override
    public void runCommand(JDA client, SlashCommandEvent event) {

    }
}
