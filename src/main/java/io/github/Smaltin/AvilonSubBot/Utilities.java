package io.github.Smaltin.AvilonSubBot;

import net.dv8tion.jda.api.entities.User;

public class Utilities {
    public static boolean isBotAdmin(User user) {
        String[] adminIDs = Configuration.getEnv("ADMIN_IDS").split(",");
        for (String id : adminIDs) {
            if (id.equals(user.getId())) return true;
        }
        return false;
    }
}