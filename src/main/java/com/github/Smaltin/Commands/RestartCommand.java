package com.github.Smaltin.Commands;

import com.github.Smaltin.Utilities;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * Gives me the ability to shutdown my pi
 *
 * @author Smaltin
 */
public class RestartCommand extends AbstractCommand {

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
        return "Restarts the discord bot's host device. Only able to be run by Smaltin#2208";
    }

    @Override
    public void runCommand(JDA client, MessageReceivedEvent event, Message msg) {
        if (Utilities.isBotAdmin(msg.getAuthor())) {
            try {
                Runtime.getRuntime().exec("sudo shutdown -h now");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}