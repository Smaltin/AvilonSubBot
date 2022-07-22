package io.github.Smaltin.AvilonSubBot.Commands;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.kodehawa.lib.imageboards.DefaultImageBoards;
import net.kodehawa.lib.imageboards.entities.BoardImage;

import java.util.HashMap;

import static io.github.Smaltin.AvilonSubBot.Configuration.PREFIX;
import static java.util.concurrent.TimeUnit.SECONDS;

public class HololivePhotoCommand extends AbstractCommand {

    private static final HashMap<String, Long> channelRateLimit = new HashMap<>();

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
            msg.reply("Please wait 5 seconds between running this command. The cooldown is to prevent rate limiting.").mentionRepliedUser(false)
                    .delay(5, SECONDS, null) // delete 5 seconds later
                    .flatMap(Message::delete)
                    .queue();
            return;
        } else {
            channelRateLimit.put(msg.getChannel().getId(), System.currentTimeMillis() + 5000);
        }
        String tags = "hololive" + msg.getContentRaw().substring(PREFIX.length() + getCommand().length());
        System.out.println("User " + msg.getAuthor().getAsTag() + " is searching Safebooru using tag(s) '" + tags + "'");
        try {
            int page = 1 + (int) (Math.random() * 1000);
            if (tags.length() > 8) {
                page = 1;
            }
            DefaultImageBoards.SAFEBOORU.search(page, 100, tags).async(images -> {
                if (images.size() >= 1) {
                    BoardImage image = images.get((int) (Math.random() * images.size()));
                    msg.reply(image.getURL()).mentionRepliedUser(false).queue();
                } else {
                    msg.reply("No images could be found. Either the server is overloaded or your tag(s) were invalid.").mentionRepliedUser(false)
                            .delay(5, SECONDS, null) // delete 5 seconds later
                            .flatMap(Message::delete)
                            .queue();
                }
            });
        } catch (Exception e) {
            msg.reply("New exception\n" + e).mentionRepliedUser(false).queue();
        }
    }
}