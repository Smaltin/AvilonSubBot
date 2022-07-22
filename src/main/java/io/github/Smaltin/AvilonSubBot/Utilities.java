package io.github.Smaltin.AvilonSubBot;

import net.dv8tion.jda.api.entities.User;

import static io.github.Smaltin.AvilonSubBot.Configuration.ADMIN_IDS;

public class Utilities {
    public static boolean isBotAdmin(User user) {
        UserRole role = getUserRole(user);
        return role != UserRole.User;
    }

    public static UserRole getUserRole(User user) {
        if (user.getId().equals("97122523086340096")) return UserRole.BotCreator;
        for (String id : ADMIN_IDS) {
            if (id.equals(user.getId())) return UserRole.Admin;
        }
        return UserRole.User;
    }
}