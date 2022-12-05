package io.github.Smaltin.AvilonSubBot.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import java.awt.*;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class HowPogCommand extends AbstractCommand {
    private final Random random = new Random(System.currentTimeMillis());

    @Override
    public String getCommand() {
        return "howpog";
    }

    @Override
    public String getArgs() {
        return "<@Mention OR String>";
    }

    @Override
    public String getDescription() {
        return "How poggers is the thing you're inputting? Who knows. (Rhetorical. I do.)";
    }

    @Override
    public void runCommand(JDA client, MessageReceivedEvent event, Message msg) {
        EmbedBuilder builder = new EmbedBuilder();

        if (event.getMessage().getMentionedMembers().isEmpty()) {
            String[] split = msg.getContentRaw().split(" ");
            if (split.length >= 2) {
                builder.setColor(Color.decode("#ff00ff")).setTitle("How Poggers?").setDescription("**" + String.join(" ", Arrays.copyOfRange(split, 1, split.length)) + "** is " + random.nextInt(100) + "% Poggers! \uD83D\uDE2F");
            }
        } else {
            Member target = msg.getMentionedMembers().get(0);
            builder.setColor(Color.decode("#ff00ff")).setTitle("How Poggers?").setDescription("**" + target.getEffectiveName() + "** is " + random.nextInt(100) + "% Poggers! \uD83D\uDE2F");
        }

        CompletableFuture<Message> sent = msg.getChannel().sendMessageEmbeds(builder.build()).submit();
        msg.addReaction("\uD83D\uDE2F").queue();
    }

    @Override
    public void setupSlashCommand(JDA client) {
        client.upsertCommand(getCommand(), getArgs()).addOption(OptionType.STRING, "subject", "the object").addOption(OptionType.USER, "user", "the person").queue();
    }

    @Override
    public void runCommand(JDA client, SlashCommandEvent event) {
        EmbedBuilder builder = new EmbedBuilder();
        OptionMapping argSubject = event.getOption("subject");
        OptionMapping argUser = event.getOption("user");
        if (argSubject != null) {
            builder.setColor(Color.decode("#ff00ff")).setTitle("How Poggers?").setDescription("**" + argSubject.getAsString() + "** is " + random.nextInt(100) + "% Poggers! \uD83D\uDE2F");
        } else if (argUser != null) {
            builder.setColor(Color.decode("#ff00ff")).setTitle("How Poggers?").setDescription("**" + argUser.getAsUser().getName() + "** is " + random.nextInt(100) + "% Poggers! \uD83D\uDE2F");
        } else {
            event.reply("You must specify either a subject or user.").setEphemeral(true).queue();
            return;
        }
        event.replyEmbeds(builder.build()).queue();
    }
}