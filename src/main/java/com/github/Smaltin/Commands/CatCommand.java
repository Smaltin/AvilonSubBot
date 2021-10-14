package com.github.Smaltin.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.Random;

public class CatCommand extends AbstractCommand {

    private static final String[] catImages = new String[]{"https://purr.objects-us-east-1.dream.io/i/SOgqO.jpg", "https://purr.objects-us-east-1.dream.io/i/ThRz6.jpg", "https://purr.objects-us-east-1.dream.io/i/img_0645.jpeg", "https://purr.objects-us-east-1.dream.io/i/wKKER.jpg", "https://purr.objects-us-east-1.dream.io/i/vJtAi.jpg", "https://purr.objects-us-east-1.dream.io/i/Tp5gI.jpg", "https://purr.objects-us-east-1.dream.io/i/wgFuU.jpg", "https://purr.objects-us-east-1.dream.io/i/SDcfu.jpg", "https://purr.objects-us-east-1.dream.io/i/1001993_717282713070_557582143_n.jpg", "https://purr.objects-us-east-1.dream.io/i/tumblr_m421kru3Gq1r6nwiqo2_1280.jpg"};
    private final Random random = new Random(System.currentTimeMillis());

    @Override
    public String getCommand() {
        return "gibcatto";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"givecat", "randomcat", "rcat"};
    }

    @Override
    public void runCommand(JDA client, MessageReceivedEvent event, Message msg) {
        EmbedBuilder builder = new EmbedBuilder();
        Member author = msg.getMember();
        assert author != null;

        builder.setImage(catImages[random.nextInt(catImages.length)]).setColor(Color.decode("#ffd1dc")).setDescription("Meow Meow").setTitle("Cat Machine");
        msg.getChannel().sendMessageEmbeds(builder.build()).submit();
    }
}