package com.github.Smaltin.Commands;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Arrays;

public class BanCommand extends AbstractCommand {

    @Override
    public String getCommand() {
        return "ban";
    }

    @Override
    public void runCommand(JDA client, MessageReceivedEvent event, Message msg) {
        Guild guild = msg.getGuild();
        Member author = msg.getMember();
        assert author != null;
        Member bot = guild.getSelfMember();

        if (event.getMessage().getMentionedMembers().isEmpty()) {
            return;
        }

        Member target = msg.getMentionedMembers().get(0);

        if (!author.canInteract(target) || !author.hasPermission(Permission.BAN_MEMBERS)) {
            event.getChannel().sendMessage("You are missing permission to ban this member").queue();
            return;
        }

        if (!bot.canInteract(target) || !bot.hasPermission(Permission.BAN_MEMBERS)) {
            event.getChannel().sendMessage("I am missing permissions to ban that member").queue();
            return;
        }

        String[] split = msg.getContentRaw().split(" ");
        String reason = split.length >= 3 ? String.join(" ", Arrays.copyOfRange(split, 3, split.length)) : null;

        event.getGuild()
                .ban(target, 0, "hi")
                .reason(reason)
                .queue(
                        (__) -> event.getChannel().sendMessage("Ban was successful").queue(),
                        (error) -> event.getChannel().sendMessageFormat("Could not ban %s", error.getMessage()).queue()
                );
    }
}