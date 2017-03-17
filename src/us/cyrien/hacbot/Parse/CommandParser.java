package us.cyrien.hacbot.Parse;

import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import us.cyrien.hacbot.Configuration.HACConfig;

import java.util.ArrayList;

public class CommandParser {

    public CommandContainer parse(String rw, MessageReceivedEvent e) {
        ArrayList<String> split = new ArrayList<>();
        String beheaded = rw.replaceFirst(HACConfig.get("trigger"), "");
        String[] splitBeheaded = beheaded.split(" ");
        for (String s : splitBeheaded)
            split.add(s);
        String invoke = split.get(0).toLowerCase();
        String[] args = new String[split.size() - 1];
        split.subList(1, split.size()).toArray(args);
        User sender = e.getAuthor();
        return new CommandContainer(rw, beheaded, splitBeheaded, invoke, args, e, sender);
    }

    public class CommandContainer {
        public final String raw;
        public final String beheaded;
        public final String[] splitBeheaded;
        public final String invoke;
        public final String[] args;
        public final MessageReceivedEvent event;
        public final User sender;

        public CommandContainer(String raw, String beheaded, String[] splitBeheaded, String invoke, String[] args, MessageReceivedEvent event, User sender) {
            this.raw = raw;
            this.beheaded = beheaded;
            this.splitBeheaded = splitBeheaded;
            this.invoke = invoke;
            this.args = args;
            this.event = event;
            this.sender = sender;
        }
    }
}
