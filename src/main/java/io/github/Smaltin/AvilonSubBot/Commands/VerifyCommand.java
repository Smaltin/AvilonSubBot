package io.github.Smaltin.AvilonSubBot.Commands;

import io.github.Smaltin.AvilonSubBot.Configuration;
import io.github.Smaltin.AvilonSubBot.Utilities;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class VerifyCommand extends AbstractCommand {

    private static final String[] joinMessages = new String[]{" just joined. Can I get a heal?", " is here. Welcome!", " we hope you brought pizza.", " leave your weapons by the door.", " appeared. Welcome!", " showed up!", " just arrived, wellcum!"};
    private final Random random = new Random(System.currentTimeMillis());

    @Override
    public String getCommand() {
        return "verify";
    }

    @Override
    public String getArgs() {
        return "<Password> OR @Mention @Mention @Mention";
    }

    @Override
    public String getDescription() {
        return "Admits the user to Avilon's server";
    }

    @Override
    public void runCommand(JDA client, MessageReceivedEvent event, Message msg) {
        Member author = msg.getMember();
        assert author != null;
        if (!msg.getChannel().getId().equals(Configuration.VERIFY_CHANNEL_ID)) {
            return;
        }

        String[] args = msg.getContentRaw().split(" ");
        if (args.length == 2) {
            if (args[1].equals(Configuration.VERIFY_PASSWORD)) {
                verifyMember(client, author, author);
            } else { //.delete().queueAfter(delay, TimeUnit.SECONDS); TODO make it delete message after 5 or so seconds
                msg.getChannel().sendMessage(author.getAsMention() + ", wrong password given.").queue((result) -> result.delete().queueAfter(5, TimeUnit.SECONDS), (failure) -> System.out.println("Message failed: " + Arrays.toString(failure.getStackTrace())));
            }
        } else if (Utilities.isBotAdmin(msg.getAuthor())) {
            try {
                if (!msg.getMentionedUsers().isEmpty()) {
                    for (Member member : msg.getMentionedMembers()) {
                        verifyMember(client, author, member);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void verifyMember(JDA client, Member author, Member member) {
        author.getGuild().removeRoleFromMember(member, Objects.requireNonNull(author.getGuild().getRoleById(Configuration.VERIFY_REMOVE_ROLE_ID))).submit();
        TextChannel general = (TextChannel) client.getGuildChannelById(Configuration.VERIFY_GENERAL_CHANNEL_ID);
        assert general != null;
        general.sendMessage(member.getAsMention() + joinMessages[random.nextInt(joinMessages.length)]).queue((result) -> {
        }, (failure) -> System.out.println("Message failed: " + Arrays.toString(failure.getStackTrace())));
    }
}