package us.cyrien.hacbot.Listener;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import us.cyrien.hacbot.Configuration.HACConfig;
import us.cyrien.hacbot.Entity.HACCBot;

public class MessageListener extends ListenerAdapter {

    private HACCBot bot;

    public MessageListener(HACCBot bot) {
        this.bot = bot;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        boolean notSelf = !event.getMessage().getAuthor().getId().equalsIgnoreCase(event.getJDA().getSelfUser().getId());
        boolean notCommand = !event.getMessage().getContent().startsWith(HACConfig.get("trigger"));
        boolean bound = event.getTextChannel().getId().equalsIgnoreCase(HACConfig.get("text-channel"));
        if(notSelf && notCommand && bound)
            bot.respondTo(event.getMessage().getContent(), event);
    }
}
