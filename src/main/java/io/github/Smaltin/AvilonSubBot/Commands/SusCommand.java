package io.github.Smaltin.AvilonSubBot.Commands;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class SusCommand extends AbstractCommand {
    String resourcesFolder = System.getProperty("user.dir") + "/resources";
    String amongUsImage = resourcesFolder + "/impostor.png";

    @Override
    public String getCommand() {
        return "sus";
    }

    @Override
    public String getArgs() {
        return "<@Mention>";
    }

    @Override
    public String getDescription() {
        return "Puts the user in an among us suit";
    }

    @Override
    public void runCommand(JDA client, MessageReceivedEvent event, Message msg) {
        BufferedImage amongusImg = null;
        BufferedImage userImg = null;

        try {
            amongusImg = ImageIO.read(new File(amongUsImage));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            userImg = ImageIO.read(new URL(Objects.requireNonNullElseGet(msg.getAuthor().getAvatarUrl(), () -> msg.getAuthor().getDefaultAvatarUrl())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (amongusImg != null && userImg != null) {
            BufferedImage temp = new BufferedImage(
                    amongusImg.getWidth(), amongusImg.getHeight(),
                    BufferedImage.TYPE_INT_ARGB);
            Graphics graphics = temp.getGraphics();
            graphics.drawImage(userImg, 1418, 573, 941, 941, null);
            graphics.drawImage(amongusImg, 0, 0, null);
            graphics.dispose();
            File f = new File(
                    resourcesFolder + "/amongus_" + msg.getAuthor().getId() + ".png");
            try {
                ImageIO.write(temp, "png", f);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            msg.reply(f).mentionRepliedUser(false).queue((success) -> {
                f.delete();
            });
        }
    }
    @Override
    public void runCommand(JDA client, SlashCommandEvent event) {
        BufferedImage amongusImg = null;
        BufferedImage userImg = null;

        try {
            amongusImg = ImageIO.read(new File(amongUsImage));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            userImg = ImageIO.read(new URL(Objects.requireNonNullElseGet(event.getUser().getAvatarUrl(), () -> event.getUser().getDefaultAvatarUrl())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (amongusImg != null && userImg != null) {
            BufferedImage temp = new BufferedImage(
                    amongusImg.getWidth(), amongusImg.getHeight(),
                    BufferedImage.TYPE_INT_ARGB);
            Graphics graphics = temp.getGraphics();
            graphics.drawImage(userImg, 1418, 573, 941, 941, null);
            graphics.drawImage(amongusImg, 0, 0, null);
            graphics.dispose();
            File f = new File(
                    resourcesFolder + "/amongus_" + event.getUser().getId() + ".png");
            try {
                ImageIO.write(temp, "png", f);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            event.reply("").addFile(f).queue((success) -> {
                f.delete();
            });
        }
    }
}