package io.github.Smaltin.AvilonSubBot.Commands.Administration;

import io.github.Smaltin.AvilonSubBot.Commands.AbstractCommand;
import io.github.Smaltin.AvilonSubBot.UserRole;
import io.github.Smaltin.AvilonSubBot.Utilities;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Gives me the ability to shutdown my pi
 *
 * @author Smaltin
 */
public class RestartCommand extends AbstractCommand {

    @Override
    public UserRole getRequiredRole() {
        return UserRole.Admin;
    }

    @Override
    public String getCommand() {
        return "restart";
    }

    @Override
    public String getArgs() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Restarts the discord bot's host device. Only able to be run by bot admins.";
    }

    @Override
    public void runCommand(JDA client, MessageReceivedEvent event, Message msg) {
        if (Utilities.isBotAdmin(msg.getAuthor())) {
            try {
                msg.reply("Restarting Smaltin's Pi...").mentionRepliedUser(false).queue();
                Runtime.getRuntime().exec("sudo shutdown -r now");
            } catch (Exception e) {
                msg.reply("New exception\n" + e).mentionRepliedUser(false).queue();
                e.printStackTrace();
            }
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
            try {
                event.reply("Restarting Smaltin's Pi...").queue();
                Runtime.getRuntime().exec("sudo shutdown -r now");
            } catch (Exception e) {
                event.reply("New exception\n" + e).setEphemeral(true).queue();
                e.printStackTrace();
            }
        } else {
            event.reply("You're not an admin.").setEphemeral(true).queue();
        }
    }
}