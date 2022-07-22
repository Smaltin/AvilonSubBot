package io.github.Smaltin.AvilonSubBot.Commands;

import io.github.Smaltin.AvilonSubBot.Runner;
import io.github.Smaltin.AvilonSubBot.UserRole;
import io.github.Smaltin.AvilonSubBot.Utilities;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class HelpCommand extends AbstractCommand {
    @Override
    public String getCommand() {
        return "help";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"commands", "cmds"};
    }

    @Override
    public String getArgs() {
        return null;
    }

    @Override
    public String getDescription() {
        return "A list of all the commands";
    }

    @Override
    public void runCommand(JDA client, MessageReceivedEvent event, Message msg) {
        StringBuilder send = new StringBuilder("Current Code Version: " + Runner.CODE_VERSION + "\nI know the following commands:\n\n");
        for (int i = 0; i < Runner.commands.values().size(); i++) {
            if ((i + 1) % 11 == 0) {
                msg.getChannel().sendMessage(send).submit();
                //System.out.println(send);
                send = new StringBuilder();
            }
            AbstractCommand command = (AbstractCommand) Runner.commands.values().toArray()[i];
            UserRole REQUIRED_ROLE = command.getRequiredRole();
            if (REQUIRED_ROLE == UserRole.User //Is the user authorized to use the command? If not, don't show it
                    || ((REQUIRED_ROLE == UserRole.Admin || REQUIRED_ROLE == UserRole.BotCreator) && Utilities.isBotAdmin(msg.getAuthor())))
                send.append("`").append(Runner.getEnv("PREFIX")).append(command.getCommand()).append(" ").append(command.getArgs() == null ? "" : command.getArgs()).append("` - ").append(command.getDescription()).append("\n");
        }
        msg.getChannel().sendMessage(send).submit();
    }
}