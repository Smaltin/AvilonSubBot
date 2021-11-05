package com.github.Smaltin.Commands;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public abstract class AbstractCommand {

    /**
     * The name of the command that the user will use to reference it
     *
     * @return The command name without the prefix
     */
    public abstract String getCommand();

    /**
     * The arguments the command may require
     *
     * @return The arguments (ex: <user>)
     */
    public abstract String getArgs();

    /**
     * I wonder what this command does?
     *
     * @return A brief description of the command and what it does
     */
    public abstract String getDescription();

    public abstract void runCommand(JDA client, MessageReceivedEvent event, Message msg);

    /**
     * Returns secondary names that will be accepted if the user calls this command
     *
     * @return array of nicknames
     */
    public String[] getAliases() {
        return new String[0];
    }
}