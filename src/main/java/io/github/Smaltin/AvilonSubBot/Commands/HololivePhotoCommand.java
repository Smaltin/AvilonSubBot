package io.github.Smaltin.AvilonSubBot.Commands;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.kodehawa.lib.imageboards.DefaultImageBoards;
import net.kodehawa.lib.imageboards.entities.BoardImage;
import net.kodehawa.lib.imageboards.entities.impl.SafebooruImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.List;

import static io.github.Smaltin.AvilonSubBot.Configuration.PREFIX;

public class HololivePhotoCommand extends AbstractCommand {
    @Override
    public String getCommand() {
        return "hololive";
    }

    @Override
    public String getArgs() {
        return "(Optional) <tag> <tag2> ...";
    }

    @Override
    public String getDescription() {
        return "Sends a random safebooru image tagged \"hololive.\" Optionally can add additional tags to the search.";
    }

    @Override
    public void runCommand(JDA client, MessageReceivedEvent event, Message msg) {
        String tags = "hololive" + msg.getContentRaw().substring(PREFIX.length() + getCommand().length());
        try {
            int page = 1 + (int) (Math.random() * 1000);
            if (tags.length() > 8) {
                page = 1;
            }
            List<SafebooruImage> images = DefaultImageBoards.SAFEBOORU.search(page, 100, tags).blocking();
            if (images.size() >= 1) {
                BoardImage image = images.get((int) (Math.random() * images.size()));
                URL url = new URL(image.getURL());
                BufferedImage img = ImageIO.read(url);
                String extension = image.getURL().substring(image.getURL().lastIndexOf(".") + 1);
                File file = new File("temp." + extension);
                ImageIO.write(img, extension, file);
                msg.reply(file).queue();
                file.delete();
            } else {
                msg.reply("No images could be found. Either the server is overloaded or your tag(s) were invalid.").queue();
            }
        } catch (Exception e) {
            msg.reply("New exception\n" + e).queue();
        }
    }
}