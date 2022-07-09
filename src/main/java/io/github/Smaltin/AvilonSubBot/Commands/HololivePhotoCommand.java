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
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import static io.github.Smaltin.AvilonSubBot.Configuration.PREFIX;

public class HololivePhotoCommand extends AbstractCommand {

    private static HashMap<String, Long> channelRateLimit = new HashMap<>();
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
        Long time = channelRateLimit.getOrDefault(msg.getChannel().getId(), System.currentTimeMillis() - 5000);
        if (System.currentTimeMillis() <= time) {
            msg.reply("Please wait 5 seconds between running this command. The cooldown is to prevent rate limiting.").queue();
            return;
        } else {
            channelRateLimit.put(msg.getChannel().getId(), System.currentTimeMillis() + 5000);
        }
        String tags = "hololive" + msg.getContentRaw().substring(PREFIX.length() + getCommand().length());
        try {
            int page = 1 + (int) (Math.random() * 1000);
            if (tags.length() > 8) {
                page = 1;
            }
            List<SafebooruImage> images = DefaultImageBoards.SAFEBOORU.search(page, 100, tags).blocking();
            if (images.size() >= 1) {
                BoardImage image = images.get((int) (Math.random() * images.size()));
                msg.reply(image.getURL()).queue();
            } else {
                msg.reply("No images could be found. Either the server is overloaded or your tag(s) were invalid.").queue();
            }
        } catch (Exception e) {
            msg.reply("New exception\n" + e).queue();
        }
    }
}