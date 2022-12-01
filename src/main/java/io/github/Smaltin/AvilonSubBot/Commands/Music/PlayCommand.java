package io.github.Smaltin.AvilonSubBot.Commands.Music;

import io.github.Smaltin.AvilonSubBot.Commands.AbstractCommand;
import io.github.Smaltin.AvilonSubBot.MusicUtilities.MusicUtilities;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Arrays;

public class PlayCommand extends AbstractCommand {
    @Override
    public String getCommand() {
        return "play";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"p", "music"};
    }

    @Override
    public String getArgs() {
        return "<song>";
    }

    @Override
    public String getDescription() {
        return "Plays a given song (by name, youtube link, spotify url, etc.)";
    }

    @Override
    public void runCommand(JDA client, MessageReceivedEvent event, Message msg) {
        String[] split = msg.getContentRaw().split(" ");
        String song = split.length >= 2 ? String.join(" ", Arrays.copyOfRange(split, 1, split.length)) : null;
        if (song != null) {
            MusicUtilities.loadAndPlay(msg.getTextChannel(), song);
        }
    }

    @Override
    public void runCommand(JDA client, SlashCommandEvent event) {

    }
}