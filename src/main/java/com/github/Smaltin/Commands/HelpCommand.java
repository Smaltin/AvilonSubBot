package com.github.Smaltin.Commands;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class HelpCommand extends AbstractCommand {
    @Override
    public String getCommand() {
        return "help";
    }

    @Override
    public void runCommand(JDA client, MessageReceivedEvent event, Message msg) {

    }
}