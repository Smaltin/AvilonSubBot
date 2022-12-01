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
import java.util.Random;

public class KissCommand extends AbstractCommand {

    private static final String[] kissImages = new String[]{"https://cdn.discordapp.com/attachments/603251454542741504/603251515846688777/tenor.gif", "https://cdn.discordapp.com/attachments/603251454542741504/603251516282765312/tenor_4.gif", "https://cdn.discordapp.com/attachments/603251454542741504/603251516282765313/giphy_1.gif", "https://cdn.discordapp.com/attachments/603251454542741504/603251517033807892/tenor_1.gif", "https://cdn.discordapp.com/attachments/603251454542741504/603251517633462319/tenor_2.gif", "https://cdn.discordapp.com/attachments/603251454542741504/603251517633462480/giphy.gif", "https://cdn.discordapp.com/attachments/603251454542741504/603251519030296587/tenor_5.gif", "https://cdn.discordapp.com/attachments/603251454542741504/603251519030296589/tenor_6.gif"};
    private final Random random = new Random(System.currentTimeMillis());

    @Override
    public String getCommand() {
        return "kiss";
    }

    @Override
    public String getArgs() {
        return "(Optional) <@Mention>";
    }

    @Override
    public String getDescription() {
        return "Sends a kissing gif";
    }

    @Override
    public void runCommand(JDA client, MessageReceivedEvent event, Message msg) {
        EmbedBuilder builder = new EmbedBuilder();
        String mentionedUserPing;
        Member author = msg.getMember();
        assert author != null;
        String authorPing = author.getEffectiveName();
        if (event.getMessage().getMentionedMembers().isEmpty()) {
            mentionedUserPing = "themself";
        } else {
            Member target = msg.getMentionedMembers().get(0);
            mentionedUserPing = target.getEffectiveName();
        }

        builder.setImage(kissImages[random.nextInt(kissImages.length)]).setColor(Color.decode("#FF0000")).setDescription(":kissing_heart: **" + authorPing + "** kissy **" + mentionedUserPing + "** :kissing_heart:");
        msg.getChannel().sendMessageEmbeds(builder.build()).submit();
    }

    @Override
    public void setupSlashCommand(JDA client) {
        client.upsertCommand(getCommand(), getDescription()).addOption(OptionType.USER, "user", "Who are you kissing?", true).queue();
    }

    @Override
    public void runCommand(JDA client, SlashCommandEvent event) {
        EmbedBuilder builder = new EmbedBuilder();
        OptionMapping argUser = event.getOption("user");
        if (argUser != null)
            builder.setImage(kissImages[random.nextInt(kissImages.length)]).setColor(Color.decode("#FF0000")).setDescription(":kissing_heart: **" + event.getUser().getAsMention() + "** kissy **" + argUser.getAsUser().getAsMention() + "** :kissing_heart:");
        event.replyEmbeds(builder.build()).queue();
    }

    @Override
    public String[] getAliases() {
        return new String[]{"kissy"};
    }
}
