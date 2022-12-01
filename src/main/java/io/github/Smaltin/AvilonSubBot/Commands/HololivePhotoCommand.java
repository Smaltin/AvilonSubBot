package io.github.Smaltin.AvilonSubBot.Commands;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.kodehawa.lib.imageboards.DefaultImageBoards;

import java.util.Arrays;
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
        return "(Optional) <(Age Restricted Only) board> <tag> <tag2> ...";
    }

    @Override
    public String getDescription() {
        return "Sends a random booru image tagged \"hololive.\" Optionally can add additional tags to the search, and image boards can be specified in age restricted channels (g = gelbooru, r = rule34, d = danbooru, k = konachan)";
    }

    @Override
    public void runCommand(JDA client, MessageReceivedEvent event, Message msg) {
        Long time = channelRateLimit.getOrDefault(msg.getChannel().getId(), System.currentTimeMillis() - 5000);
        if (System.currentTimeMillis() <= time) {
            msg.reply("Please wait 5 seconds between running this command. The cooldown is to prevent rate limiting.").mentionRepliedUser(false).delay(5, SECONDS, null) // delete 5 seconds later
                    .flatMap(Message::delete).queue();
            return;
        } else {
            channelRateLimit.put(msg.getChannel().getId(), System.currentTimeMillis() + 5000);
        }

        String commandArgs = msg.getContentRaw().substring(PREFIX.length() + getCommand().length());
        String possibleBoard = !commandArgs.equals("") ? commandArgs.split(" ")[1] : "s";
        String tags;
        if (possibleBoard.length() == 1 && !possibleBoard.equalsIgnoreCase("a") && !commandArgs.equals("")) {
            String[] split = commandArgs.split(" ");
            tags = String.join(" ", Arrays.copyOfRange(split, 2, split.length));
            tags = tags.length() > 0 ? " " + tags : tags;
            possibleBoard = msg.getTextChannel().isNSFW() ? possibleBoard : "s";
        } else {
            possibleBoard = "s";
            tags = commandArgs;
        }
        tags = "hololive" + tags;

        int page = 1 + (int) (Math.random() * 100);
        if (tags.length() > 11) {
            page = 1;
        }
        searchBoard(msg, possibleBoard, tags, page);
    }

    @Override
    public void setupSlashCommand(JDA client) {
        client.upsertCommand(getCommand(), "Allows you to search for images tagged 'hololive'")
                .addOptions(
                        new OptionData(OptionType.STRING, "tags", "A list of additional tags, seperated by spaces", false),
                        new OptionData(OptionType.STRING, "board", "A board to search (Age Restricted only)")
                                .addChoice("Safebooru", "s")
                                .addChoice("Rule34", "r")
                                .addChoice("Danbooru", "d")
                                .addChoice("Konachan", "k")
                                .addChoice("Gelbooru", "g")
                ).queue();
    }

    @Override
    public void runCommand(JDA client, SlashCommandEvent event) {
        Long time = channelRateLimit.getOrDefault(event.getChannel().getId(), System.currentTimeMillis() - 5000);
        if (System.currentTimeMillis() <= time) {
            event.reply("Please wait 5 seconds between running this command. The cooldown is to prevent rate limiting.").setEphemeral(true).queue();
            return;
        } else {
            event.deferReply().queue();
            channelRateLimit.put(event.getChannel().getId(), System.currentTimeMillis() + 5000);
        }

        OptionMapping argTags = event.getOption("tags");
        OptionMapping argBoard = event.getOption("board");
        String searchTags = (argTags == null) ? "hololive" : "hololive " + argTags.getAsString();
        String searchBoard = (argBoard == null | !event.getTextChannel().isNSFW()) ? "s" : argBoard.getAsString();

        int page = searchTags.length() > 9 ? 1 : 1 + (int) (Math.random() * 100);

        searchBoard(event, searchBoard, searchTags, page);
    }

    public void searchBoard(Message msg, String board, String tags, int page) {
        switch (board) {
            case "g" -> {
                System.out.println("User " + msg.getAuthor().getAsTag() + " is searching Gelbooru on page " + page + " using tag(s) '" + tags + "'");
                try {
                    DefaultImageBoards.GELBOORU.search(page, 100, tags).async(images -> {
                        if (images.size() >= 1) {
                            String imageURL = images.get((int) (Math.random() * images.size())).getFile_url();
                            if (!imageURL.equals("")) {
                                msg.reply(imageURL).mentionRepliedUser(false).queue();
                            } else {
                                msg.reply("No images could be found. Either the server is overloaded or your tag(s) were invalid.").mentionRepliedUser(false).delay(5, SECONDS, null) // delete 5 seconds later
                                        .flatMap(Message::delete).queue();
                            }
                        } else {
                            msg.reply("No images could be found. Either the server is overloaded or your tag(s) were invalid.").mentionRepliedUser(false).delay(5, SECONDS, null) // delete 5 seconds later
                                    .flatMap(Message::delete).queue();
                        }
                    });
                } catch (Exception e) {
                    msg.reply("New exception\n" + e).mentionRepliedUser(false).queue();
                }
            }
            case "r" -> {
                System.out.println("User " + msg.getAuthor().getAsTag() + " is searching R34 using tag(s) '" + tags + "'");
                try {
                    DefaultImageBoards.RULE34.search(page, 100, tags).async(images -> {
                        if (images.size() >= 1) {
                            String imageURL = images.get((int) (Math.random() * images.size())).getFile_url();
                            if (!imageURL.equals("")) {
                                msg.reply(imageURL).mentionRepliedUser(false).queue();
                            } else {
                                msg.reply("No images could be found. Either the server is overloaded or your tag(s) were invalid.").mentionRepliedUser(false).delay(5, SECONDS, null) // delete 5 seconds later
                                        .flatMap(Message::delete).queue();
                            }
                        } else {
                            msg.reply("No images could be found. Either the server is overloaded or your tag(s) were invalid.").mentionRepliedUser(false).delay(5, SECONDS, null) // delete 5 seconds later
                                    .flatMap(Message::delete).queue();
                        }
                    });
                } catch (Exception e) {
                    msg.reply("New exception\n" + e).mentionRepliedUser(false).queue();
                }
            }
            case "d" -> {
                System.out.println("User " + msg.getAuthor().getAsTag() + " is searching Danbooru using tag(s) '" + tags + "'");
                try {
                    DefaultImageBoards.DANBOORU.search(page, 100, tags).async(images -> {
                        if (images.size() >= 1) {
                            String imageURL = images.get((int) (Math.random() * images.size())).getFile_url();
                            if (!imageURL.equals("")) {
                                msg.reply(imageURL).mentionRepliedUser(false).queue();
                            } else {
                                msg.reply("No images could be found. Either the server is overloaded or your tag(s) were invalid.").mentionRepliedUser(false).delay(5, SECONDS, null) // delete 5 seconds later
                                        .flatMap(Message::delete).queue();
                            }
                        } else {
                            msg.reply("No images could be found. Either the server is overloaded or your tag(s) were invalid.").mentionRepliedUser(false).delay(5, SECONDS, null) // delete 5 seconds later
                                    .flatMap(Message::delete).queue();
                        }
                    });
                } catch (Exception e) {
                    msg.reply("New exception\n" + e).mentionRepliedUser(false).queue();
                }
            }
            case "k" -> {
                System.out.println("User " + msg.getAuthor().getAsTag() + " is searching Konachan using tag(s) '" + tags + "'");
                try {
                    DefaultImageBoards.KONACHAN.search(page, 100, tags).async(images -> {
                        if (images.size() >= 1) {
                            String imageURL = images.get((int) (Math.random() * images.size())).getJpeg_url();
                            if (!imageURL.equals("")) {
                                msg.reply(imageURL).mentionRepliedUser(false).queue();
                            } else {
                                msg.reply("No images could be found. Either the server is overloaded or your tag(s) were invalid.").mentionRepliedUser(false).delay(5, SECONDS, null) // delete 5 seconds later
                                        .flatMap(Message::delete).queue();
                            }
                        } else {
                            msg.reply("No images could be found. Either the server is overloaded or your tag(s) were invalid.").mentionRepliedUser(false).delay(5, SECONDS, null) // delete 5 seconds later
                                    .flatMap(Message::delete).queue();
                        }
                    });
                } catch (Exception e) {
                    msg.reply("New exception\n" + e).mentionRepliedUser(false).queue();
                }
            }
            default -> {
                System.out.println("User " + msg.getAuthor().getAsTag() + " is searching Safebooru using tag(s) '" + tags + "'");
                try {
                    DefaultImageBoards.SAFEBOORU.search(page, 100, tags).async(images -> {
                        if (images.size() >= 1) {
                            String imageURL = images.get((int) (Math.random() * images.size())).getFileUrl();
                            if (!imageURL.equals("")) {
                                msg.reply(imageURL).mentionRepliedUser(false).queue();
                            } else {
                                msg.reply("No images could be found. Either the server is overloaded or your tag(s) were invalid.").mentionRepliedUser(false).delay(5, SECONDS, null) // delete 5 seconds later
                                        .flatMap(Message::delete).queue();
                            }
                        } else {
                            msg.reply("No images could be found. Either the server is overloaded or your tag(s) were invalid.").mentionRepliedUser(false).delay(5, SECONDS, null) // delete 5 seconds later
                                    .flatMap(Message::delete).queue();
                        }
                    });
                } catch (Exception e) {
                    msg.reply("New exception\n" + e).mentionRepliedUser(false).queue();
                }
            }
        }
    }

    public void searchBoard(SlashCommandEvent event, String board, String tags, int page) {
        switch (board) {
            case "g" -> {
                System.out.println("User " + event.getUser().getAsTag() + " is searching Gelbooru on page " + page + " using tag(s) '" + tags + "'");
                try {
                    DefaultImageBoards.GELBOORU.search(page, 100, tags).async(images -> {
                        if (images.size() >= 1) {
                            String imageURL = images.get((int) (Math.random() * images.size())).getFile_url();
                            if (!imageURL.equals("")) {
                                event.getHook().sendMessage(imageURL).queue();
                            } else {
                                event.getHook().sendMessage("No images could be found. Either the server is overloaded or your tag(s) were invalid.").queue();
                            }
                        } else {
                            event.getHook().sendMessage("No images could be found. Either the server is overloaded or your tag(s) were invalid.").queue();
                        }
                    });
                } catch (Exception e) {
                    event.getHook().sendMessage("New exception\n" + e).queue();
                }
            }
            case "r" -> {
                System.out.println("User " + event.getUser().getAsTag() + " is searching R34 using tag(s) '" + tags + "'");
                try {
                    DefaultImageBoards.RULE34.search(page, 100, tags).async(images -> {
                        if (images.size() >= 1) {
                            String imageURL = images.get((int) (Math.random() * images.size())).getFile_url();
                            if (!imageURL.equals("")) {
                                event.getHook().sendMessage(imageURL).queue();
                            } else {
                                event.getHook().sendMessage("No images could be found. Either the server is overloaded or your tag(s) were invalid.").queue();
                            }
                        } else {
                            event.getHook().sendMessage("No images could be found. Either the server is overloaded or your tag(s) were invalid.").queue();
                        }
                    });
                } catch (Exception e) {
                    event.getHook().sendMessage("New exception\n" + e).queue();
                }
            }
            case "d" -> {
                System.out.println("User " + event.getUser().getAsTag() + " is searching Danbooru using tag(s) '" + tags + "'");
                try {
                    DefaultImageBoards.DANBOORU.search(page, 100, tags).async(images -> {
                        if (images.size() >= 1) {
                            String imageURL = images.get((int) (Math.random() * images.size())).getFile_url();
                            if (!imageURL.equals("")) {
                                event.getHook().sendMessage(imageURL).mentionRepliedUser(false).queue();
                            } else {
                                event.getHook().sendMessage("No images could be found. Either the server is overloaded or your tag(s) were invalid.").queue();
                            }
                        } else {
                            event.getHook().sendMessage("No images could be found. Either the server is overloaded or your tag(s) were invalid.").queue();
                        }
                    });
                } catch (Exception e) {
                    event.getHook().sendMessage("New exception\n" + e).queue();
                }
            }
            case "k" -> {
                System.out.println("User " + event.getUser().getAsTag() + " is searching Konachan using tag(s) '" + tags + "'");
                try {
                    DefaultImageBoards.KONACHAN.search(page, 100, tags).async(images -> {
                        if (images.size() >= 1) {
                            String imageURL = images.get((int) (Math.random() * images.size())).getJpeg_url();
                            if (!imageURL.equals("")) {
                                event.getHook().sendMessage(imageURL).queue();
                            } else {
                                event.getHook().sendMessage("No images could be found. Either the server is overloaded or your tag(s) were invalid.").queue();
                            }
                        } else {
                            event.getHook().sendMessage("No images could be found. Either the server is overloaded or your tag(s) were invalid.").queue();
                        }
                    });
                } catch (Exception e) {
                    event.getHook().sendMessage("New exception\n" + e).queue();
                }
            }
            default -> {
                System.out.println("User " + event.getUser().getAsTag() + " is searching Safebooru using tag(s) '" + tags + "'");
                try {
                    DefaultImageBoards.SAFEBOORU.search(page, 100, tags).async(images -> {
                        if (images.size() >= 1) {
                            String imageURL = images.get((int) (Math.random() * images.size())).getFileUrl();
                            if (!imageURL.equals("")) {
                                event.getHook().sendMessage(imageURL).queue();
                            } else {
                                event.getHook().sendMessage("No images could be found. Either the server is overloaded or your tag(s) were invalid.").queue();
                            }
                        } else {
                            event.getHook().sendMessage("No images could be found. Either the server is overloaded or your tag(s) were invalid.").queue();
                        }
                    });
                } catch (Exception e) {
                    event.getHook().sendMessage("New exception\n" + e).queue();
                }
            }
        }
    }
}