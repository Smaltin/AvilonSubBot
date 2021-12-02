package com.github.Smaltin.Commands;

import ij.IJ;
import ij.ImagePlus;
import ij.ImageStack;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.imagej.ImageJ;

public class SusCommand extends AbstractCommand {
    ImageJ ij = new ImageJ();

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
        try {
            ImagePlus image = IJ.openImage(msg.getAuthor().getAvatarUrl());
            ImagePlus sus = IJ.openImage("");
            ImageStack stack = new ImageStack();
            stack.addSlice(sus.getProcessor());
            stack.addSlice(image.getProcessor());
            String[] args = msg.getContentRaw().split(" ");
            if (args.length > 0) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}