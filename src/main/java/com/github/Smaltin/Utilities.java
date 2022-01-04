package com.github.Smaltin;

import net.dv8tion.jda.api.entities.User;

public class Utilities {
    public static boolean isBotAdmin(User user) {
        //                        Smaltin                  Kuro                  Avilon
        String[] adminIDs = {"609396168417476645", "396892407884546058", "609396168417476645"};
        for (String id : adminIDs) {
            if (id.equals(user.getId())) return true;
        }
        return false;
    }
}