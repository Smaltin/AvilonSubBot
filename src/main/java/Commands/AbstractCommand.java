package Commands;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public abstract class AbstractCommand {

    public static final boolean DEVELOPER_MODE = true;

    /**
     * The name of the command that the user will use to reference it
     *
     * @return The command name without the prefix
     */
    public abstract String getCommand();

    /**
     * Returns secondary names that will be accepted if the user calls this command
     *
     * @return array of nicknames
     */
    public String[] getAliases() {
        return new String[0];
    }

    public abstract void runCommand(JDA client, MessageReceivedEvent event, Message msg);
}