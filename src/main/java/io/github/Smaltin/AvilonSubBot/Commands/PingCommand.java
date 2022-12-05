package io.github.Smaltin.AvilonSubBot.Commands;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * Allows the user to ping the bot
 *
 * @author jojo2357
 */
public class PingCommand extends AbstractCommand {
    private static final String[] pingMessages = new String[]{
            ":ping_pong::white_small_square::black_small_square::black_small_square::ping_pong:",
            ":ping_pong::black_small_square::white_small_square::black_small_square::ping_pong:",
            ":ping_pong::black_small_square::black_small_square::white_small_square::ping_pong:",
            ":ping_pong::black_small_square::white_small_square::black_small_square::ping_pong:",
    };

    @Override
    public String getCommand() {
        return "ping";
    }

    @Override
    public String getArgs() {
        return "(Optional) fancy";
    }

    @Override
    public String getDescription() {
        return "Checks the ping of the bot. If fancy is passed then it changes the appearance of the ping but isn't necessary";
    }

    @Override
    public void runCommand(JDA client, MessageReceivedEvent event, Message msg) {
        String[] args = msg.getContentRaw().split(" ");
        if (args.length > 1 && args[1].matches("fancy")) {
            msg.getChannel().sendMessage("Checking ping...").queue(sendMe -> {
                int pings = 5;
                int lastResult;
                int sum = 0, min = 999, max = 0;
                long start = System.currentTimeMillis();
                for (int j = 0; j < pings; j++) {
                    sendMe.editMessage(pingMessages[j % pingMessages.length]).submit();
                    lastResult = (int) (System.currentTimeMillis() - start);
                    sum += lastResult;
                    min = Math.min(min, lastResult);
                    max = Math.max(max, lastResult);
                    try {
                        Thread.sleep(1_500L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    start = System.currentTimeMillis();
                }
                sendMe.editMessage(String.format("Average ping is %dms (min: %d, max: %d)", (int) Math.ceil(sum / 5f), min, max)).submit();
            });
        } else {
            long start = System.currentTimeMillis();
            msg.getChannel().sendMessage(":outbox_tray: checking ping").queue(
                    sendMe -> sendMe.editMessage(":inbox_tray: ping is " + (System.currentTimeMillis() - start) + "ms").submit());
        }
    }

    @Override
    public void runCommand(JDA client, SlashCommandEvent event) {
        long time = System.currentTimeMillis();
        event.reply("Pong!").setEphemeral(true) // reply or acknowledge
                .flatMap(v ->
                        event.getHook().editOriginalFormat("Pong: %d ms", System.currentTimeMillis() - time) // then edit original
                ).queue(); // Queue both reply and edit
    }

    @Override
    public void setupSlashCommand(JDA client) {
        client.upsertCommand(getCommand(), "Checks the ping of the bot").queue();
    }
}