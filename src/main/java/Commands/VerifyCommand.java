package Commands;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class VerifyCommand extends AbstractCommand {

    private static final String[] joinMessages = new String[]{" just joined. Can I get a heal?", " is here. Welcome!", " we hope you brought pizza.", " leave your weapons by the door.", " appeared. Welcome!", " showed up!", " just arrived, wellcum!"};
    private final Random random = new Random(System.currentTimeMillis());

    @Override
    public String getCommand() {
        return "verify";
    }

    @Override
    public void runCommand(JDA client, MessageReceivedEvent event, Message msg) {
        Member author = msg.getMember();
        assert author != null;

        if (!msg.getChannel().getId().equals("836222286432043018")) {
            return;
        }

        String[] args = msg.getContentRaw().split(" ");
        if (args.length == 2) {
            String password = "booba";
            if (args[1].equals(password)) {
                //author.getGuild().removeRoleFromMember(author, findRole(author, "836221898357604352")).submit();
                author.getGuild().removeRoleFromMember(author, Objects.requireNonNull(author.getGuild().getRoleById("836221898357604352"))).submit();
                TextChannel general = (TextChannel) client.getGuildChannelById("812666547520667669");
                assert general != null;
                general.sendMessage(author.getAsMention() + joinMessages[random.nextInt(joinMessages.length)]).queue((result) -> {
                }, (failure) -> System.out.println("Message failed: " + Arrays.toString(failure.getStackTrace())));
            } else { //.delete().queueAfter(delay, TimeUnit.SECONDS); TODO make it delete message after 5 or so seconds
                msg.getChannel().sendMessage(author.getAsMention() + ", wrong password given.").queue((result) -> {
                    result.delete().queueAfter(5, TimeUnit.SECONDS);
                }, (failure) -> {
                    System.out.println("Message failed: " + Arrays.toString(failure.getStackTrace()));
                });
            }
        }
    }
/*
    private Role findRole(Member member, String roleId) {
        List<Role> roles = member.getRoles();
        return roles.stream()
                .filter(role -> role.getId().equals(roleId))
                .findFirst()
                .orElse(null);
    }
 */
}