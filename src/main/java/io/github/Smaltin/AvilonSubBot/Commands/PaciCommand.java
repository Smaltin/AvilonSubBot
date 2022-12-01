package io.github.Smaltin.AvilonSubBot.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.function.Consumer;

public class PaciCommand extends AbstractCommand {
    @Override
    public String getCommand() {
        return "gibpaci";
    }

    @Override
    public String getArgs() {
        return "<@Mention>";
    }

    @Override
    public String getDescription() {
        return "Sends the mentioned user a pacifier image";
    }

    @Override
    public void runCommand(JDA client, MessageReceivedEvent event, Message msg) {
        if (!event.getMessage().getMentionedMembers().isEmpty()) {
            EmbedBuilder builder = new EmbedBuilder();
            Member target = msg.getMentionedMembers().get(0);
            builder.setImage("https://cdn.discordapp.com/attachments/814822166305570816/814822486519709746/1613399364931.png").setColor(Color.decode("#ff00ff")).setTitle("Pacifier");
            Consumer<PrivateChannel> messageSender = channel -> channel.sendMessageEmbeds(builder.build()).submit();
            target.getUser().openPrivateChannel().queue(messageSender);
        }
    }

    @Override
    public void setupSlashCommand(JDA client) {
    }

    @Override
    public void runCommand(JDA client, SlashCommandEvent event) {
    }
}