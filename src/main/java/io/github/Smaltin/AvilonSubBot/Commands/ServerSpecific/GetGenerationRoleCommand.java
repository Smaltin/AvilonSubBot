package io.github.Smaltin.AvilonSubBot.Commands.ServerSpecific;

import io.github.Smaltin.AvilonSubBot.Commands.AbstractCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.sql.Date;
import java.util.Objects;

public class GetGenerationRoleCommand extends AbstractCommand {
    @Override
    public String getCommand() {
        return "getgen";
    }

    @Override
    public String getArgs() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Determines what generation of server member you are and grants you the role. If this is incorrect (left server or were gen 0), inform a moderator";
    }

    @Override
    public void setupSlashCommand(JDA client) {
        client.upsertCommand(getCommand(), "Attempts to give the proper generation role for Avilon's server").queue();
    }

    @Override
    public void runCommand(JDA client, MessageReceivedEvent event, Message msg) {
        Member author = msg.getMember();
        assert author != null;
        if (!msg.getGuild().getId().equals("603147860225032192")) { //Pacifam only TODO make server specific number be in config
            //TODO make help command not show server specific commands unless actually in server
            return;
        }
        int gen = getGen(author.getTimeJoined().toEpochSecond());
        clearGenRoles(author);
        roleByGen(gen, author);
        msg.reply("You have been assigned to generation " + gen + ". This generation is based off your most recent join date. If you believe this is incorrect, inform a moderator.").queue();
    }

    @Override
    public void runCommand(JDA client, SlashCommandEvent event) {
        Member author = event.getMember();
        assert author != null;
        if (!Objects.requireNonNull(event.getGuild()).getId().equals("603147860225032192")) { //Pacifam only TODO make server specific number be in config
            //TODO make help command not show server specific commands unless actually in server
            event.reply("You are not in an eligible server for this command.").setEphemeral(true).queue();
            return;
        }
        int gen = getGen(author.getTimeJoined().toEpochSecond());
        clearGenRoles(author);
        roleByGen(gen, author);
        event.reply("You have been assigned to generation " + gen + ". This generation is based off your most recent join date. If you believe this is incorrect, inform a moderator.").queue();
    }

    private int getGen(long timeinsec) {
        long timeinms = timeinsec * 1000;
        if (timeinms < Date.valueOf("2021-02-22").getTime()) {
            return 0;
        } else if (timeinms < Date.valueOf("2021-06-30").getTime()) {
            return 1;
        } else if (timeinms < Date.valueOf("2021-11-30").getTime()) {
            return 2;
        } else if (timeinms < Date.valueOf("2022-06-30").getTime()) {
            return 3;
        } else if (timeinms < Date.valueOf("2022-09-30").getTime()) {
            return 4;
        } else if (timeinms < Date.valueOf("2023-06-15").getTime()) {
            return 5;
        } else {
            return 6;
        }
    }

    private void roleByGen(int gen, Member author) {
        long roleid = switch (gen) {
            case 0 -> 1019586837989294173L;
            case 1 -> 1019586896487264326L;
            case 2 -> 1009393869911240724L;
            case 3 -> 1019586932851884062L;
            case 4 -> 1019586983103840296L;
            case 5 -> 1020011083941023837L;
            case 6 -> 1119627567096406149L;
            default -> 0L;
        };
        if (roleid != 0L) {
            if (!author.getRoles().contains(author.getGuild().getRoleById(roleid))) {
                author.getGuild().addRoleToMember(author, Objects.requireNonNull(author.getGuild().getRoleById(roleid))).submit();
            }
        }
    }

    private void clearGenRoles(Member author) {
        long[] roles = {1019586837989294173L, 1019586896487264326L, 1009393869911240724L, 1019586932851884062L, 1019586983103840296L, 1020011083941023837L, 1119627567096406149L};
        for (Role r : author.getRoles()) {
            long roleid = r.getIdLong();
            for (long role : roles) {
                if (role == roleid) {
                    author.getGuild().removeRoleFromMember(author, r).submit();
                }
            }
        }
    }
}