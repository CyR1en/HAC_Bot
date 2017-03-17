package us.cyrien.hacbot.Listener;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import us.cyrien.hacbot.Configuration.HACConfig;
import us.cyrien.hacbot.Handle.CommandHandler;
import us.cyrien.hacbot.Parse.CommandParser;

public class CommandListener extends ListenerAdapter {

    private CommandParser parser;
    private CommandHandler cmdHandler;

    public CommandListener() {
        parser = new CommandParser();
        cmdHandler = new CommandHandler();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        boolean execHead = event.getMessage().getContent().startsWith(HACConfig.get("trigger"));
        boolean notSelf = !event.getMember().getUser().getId().equalsIgnoreCase(event.getJDA().getSelfUser().getId());
        if (execHead && notSelf) {
            cmdHandler.handleCommand(parser.parse(event.getMessage().getContent(), event));
        }
    }
}