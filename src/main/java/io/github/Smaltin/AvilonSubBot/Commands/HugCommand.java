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

public class HugCommand extends AbstractCommand {

    private static final String[] hugImages = new String[]{"https://cdn.weeb.sh/images/Sk-xxs3C-.gif", "https://cdn.weeb.sh/images/ryjJFdmvb.gif", "https://cdn.weeb.sh/images/HJ7lY_QwW.gif", "https://cdn.weeb.sh/images/rkx1dJ25z.gif", "https://cdn.weeb.sh/images/rJ_slRYFZ.gif", "https://cdn.weeb.sh/images/rkIK_u7Pb.gif", "https://cdn.weeb.sh/images/r1kC_dQPW.gif", "https://cdn.weeb.sh/images/Hyec_OmDW.gif", "https://cdn.weeb.sh/images/HytoudXwW.gif", "https://cdn.weeb.sh/images/Hk0yFumwW.gif"};
    private final Random random = new Random(System.currentTimeMillis());

    @Override
    public String getCommand() {
        return "hug";
    }

    @Override
    public String getArgs() {
        return "(Optional) <@Mention>";
    }

    @Override
    public String getDescription() {
        return "Sends a hugging gif";
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

        builder.setImage(hugImages[random.nextInt(hugImages.length)]).setColor(Color.decode("#FF0000")).setDescription("\uD83E\uDD17 **" + authorPing + "** huggy **" + mentionedUserPing + "** \uD83E\uDD17");
        msg.getChannel().sendMessageEmbeds(builder.build()).submit();
    }

    @Override
    public void setupSlashCommand(JDA client) {
        client.upsertCommand(getCommand(), getDescription()).addOption(OptionType.USER, "user", "Who are you hugging?", true).queue();
    }

    @Override
    public void runCommand(JDA client, SlashCommandEvent event) {
        EmbedBuilder builder = new EmbedBuilder();
        OptionMapping argUser = event.getOption("user");
        if (argUser != null)
            builder.setImage(hugImages[random.nextInt(hugImages.length)]).setColor(Color.decode("#FF0000")).setDescription("\uD83E\uDD17 **" + event.getUser().getAsMention() + "** huggy **" + argUser.getAsUser().getAsMention() + "** \uD83E\uDD17");
        event.replyEmbeds(builder.build()).queue();
    }

    @Override
    public String[] getAliases() {
        return new String[]{"huggy"};
    }
}
