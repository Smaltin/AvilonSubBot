package com.github.Smaltin.Commands;

import com.github.Smaltin.Main;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class HelpCommand extends AbstractCommand {
    @Override
    public String getCommand() {
        return "help";
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
        //System.out.println("Sending help command");
        StringBuilder send = new StringBuilder("I know the following commands:\n\n");
        for (int i = 0; i < Main.commands.values().size(); i++) {
            if ((i+1) % 11 == 0) {
                msg.getChannel().sendMessage(send).submit();
                //System.out.println(send);
                send = new StringBuilder();
            }
            AbstractCommand command = (AbstractCommand) Main.commands.values().toArray()[i];
            send.append("`").append(Main.getEnv("PREFIX") + command.getCommand()).append(" ").append(command.getArgs() == null ? "" : command.getArgs()).append("` - ").append(command.getDescription()).append("\n");
        }
        msg.getChannel().sendMessage(send).submit();
        //System.out.println(send);
    }

    @Override
    public String[] getAliases() {
        return new String[]{"commands", "cmds"};
    }
}