package io.github.Smaltin.AvilonSubBot.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static io.github.Smaltin.AvilonSubBot.Runner.CODE_VERSION;

public class ChangelogCommand extends AbstractCommand {
    HashMap<String, String> clogs = new HashMap<>() {
        {
            put("0.0.1", "Bot is created with a few basic commands given by Avilon");
            put("0.0.2", "Bot is capable of listing subscribers of Avilon into a channel, ran on a separate thread and restarted when killed");
            put("0.0.3", "added hololive photo command\ncooldown per channel");
            put("0.0.4", "Code cleanup\nmake hololive photo command asynchronous");
            put("0.0.5", "Added a new permission system for bot admins, everyone and commands now have role permissions\nPings were removed from replies\nAdded a new debugging command that I'll add more to later to help get info\nAdded a way to reload the settings file in case I want to quickly change things on the fly");
            put("0.0.6", "Added the get generation command for Avilon's server");
            put("0.0.7", "Updated help command for visibility\nHololive photo command now supports other image boards in age restricted channels\nAdded bot uptime to debug command");
            put("0.0.8", "Adds the changelog command\nRemove version from help command as it's posted in the debug command");
            put("0.0.9", "Adds the ability to use slash commands for certain commands. Disabled the gibmeme command since the images are no longer being hosted.");
            put("0.0.10", "Adds more slash commands, specifically ones involving options. Makes verify command server specific.");
        }
    };

    @Override
    public String getCommand() {
        return "changelog";
    }

    @Override
    public String getArgs() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Replies with a list of changes by version number";
    }

    @Override
    public void runCommand(JDA client, MessageReceivedEvent event, Message msg) {
        EmbedBuilder changelog = new EmbedBuilder();
        changelog.setTitle("Changelog");
        changelog.setFooter("Bot by Smaltin#2208");
        //changelog.addField("Note", "Any entries between versions 0.0.1 and 0.0.4 were guesses made by the changelog, there could have been more internal changes that I'm forgetting", false);
        changelog.addField("Current Version", CODE_VERSION, false);
        ArrayList<String> sortedList = new ArrayList<>(clogs.keySet());
        Collections.sort(sortedList);
        for (String key : sortedList) {
            changelog.addField(key, clogs.get(key), false);
        }
        msg.replyEmbeds(changelog.build()).queue();
    }

    @Override
    public void runCommand(JDA client, SlashCommandEvent event) {
        EmbedBuilder changelog = new EmbedBuilder();
        changelog.setTitle("Changelog");
        changelog.setFooter("Bot by Smaltin#2208");
        //changelog.addField("Note", "Any entries between versions 0.0.1 and 0.0.4 were guesses made by the changelog, there could have been more internal changes that I'm forgetting", false);
        changelog.addField("Current Version", CODE_VERSION, false);
        ArrayList<String> sortedList = new ArrayList<>(clogs.keySet());
        Collections.sort(sortedList);
        for (String key : sortedList) {
            changelog.addField(key, clogs.get(key), false);
        }
        event.replyEmbeds(changelog.build()).setEphemeral(true).queue();
    }
}