package com.github.Smaltin.Commands;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Arrays;

public class KickCommand extends AbstractCommand {

    @Override
    public String getCommand() {
        return "kick";
    }

    @Override
    public String getArgs() {
        return "<@Mention> <Reason>";
    }

    @Override
    public String getDescription() {
        return "Kicks the mentioned user with the reason provided";
    }

    @Override
    public void runCommand(JDA client, MessageReceivedEvent event, Message msg) {
        Guild guild = msg.getGuild();
        Member author = msg.getMember();
        Member bot = guild.getSelfMember();

        if (event.getMessage().getMentionedMembers().isEmpty()) {
            return;
        }

        Member target = msg.getMentionedMembers().get(0);
        assert author != null;
        if (!author.canInteract(target) || !author.hasPermission(Permission.KICK_MEMBERS)) {
            event.getChannel().sendMessage("You are missing permission to kick this member").queue();
            return;
        }

        if (!bot.canInteract(target) || !bot.hasPermission(Permission.KICK_MEMBERS)) {
            event.getChannel().sendMessage("I am missing permissions to kick that member").queue();
            return;
        }
        String[] split = msg.getContentRaw().split(" ");
        String reason = split.length >= 3 ? String.join(" ", Arrays.copyOfRange(split, 3, split.length)) : null;

        event.getGuild()
                .kick(target, "hi")
                .reason(reason)
                .queue(
                        (__) -> event.getChannel().sendMessage("Kick was successful").queue(),
                        (error) -> event.getChannel().sendMessageFormat("Could not kick %s", error.getMessage()).queue()
                );
    }
}