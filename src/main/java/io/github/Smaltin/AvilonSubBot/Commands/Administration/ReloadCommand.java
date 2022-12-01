package io.github.Smaltin.AvilonSubBot.Commands.Administration;

import io.github.Smaltin.AvilonSubBot.Commands.AbstractCommand;
import io.github.Smaltin.AvilonSubBot.Configuration;
import io.github.Smaltin.AvilonSubBot.UserRole;
import io.github.Smaltin.AvilonSubBot.Utilities;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import static java.util.concurrent.TimeUnit.SECONDS;

public class ReloadCommand extends AbstractCommand {

    @Override
    public UserRole getRequiredRole() {
        return UserRole.Admin;
    }

    @Override
    public String getCommand() {
        return "reload";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"refresh", "refreshcache", "rcache", "reloadcache"};
    }

    @Override
    public String getArgs() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Reloads the bot's cache. Only can be ran by bot admins";
    }

    @Override
    public void runCommand(JDA client, MessageReceivedEvent event, Message msg) {
        if (Utilities.isBotAdmin(msg.getAuthor())) {
            msg.reply("Updating environment variable cache...").mentionRepliedUser(false).queue();
            Configuration.updateEnv();
        } else {
            msg.reply("You're not an admin.").mentionRepliedUser(false)
                    .delay(3, SECONDS, null) // delete 3 seconds later
                    .flatMap(Message::delete)
                    .queue();
        }
    }

    @Override
    public void runCommand(JDA client, SlashCommandEvent event) {
        if (Utilities.isBotAdmin(event.getUser())) {
            event.reply("Updating environment variable cache...").queue();
            Configuration.updateEnv();
        } else {
            event.reply("You're not an admin.").setEphemeral(true).queue();
        }
    }
}