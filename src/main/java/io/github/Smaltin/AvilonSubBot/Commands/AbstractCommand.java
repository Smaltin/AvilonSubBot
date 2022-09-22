package io.github.Smaltin.AvilonSubBot.Commands;

import io.github.Smaltin.AvilonSubBot.UserRole;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

public abstract class AbstractCommand {

    /**
     * The name of the command that the user will use to reference it
     *
     * @return The command name without the prefix
     */
    public abstract String getCommand();

    /**
     * Allows for a slash command to be created. Takes in a {@link CommandListUpdateAction} and outputs true if the slash command was created successfully or false otherwise
     */
    public boolean setupSlashCommand() {
        return false;
    }

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

    //public abstract void runCommand(JDA Client, SlashCommandEvent event, )

    /**
     * Returns secondary names that will be accepted if the user calls this command
     *
     * @return array of nicknames
     */
    public String[] getAliases() {
        return new String[0];
    }

    public UserRole getRequiredRole() {
        return UserRole.User;
    }
}