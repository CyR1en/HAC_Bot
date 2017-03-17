package us.cyrien.hacbot.Commands;

import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import us.cyrien.hacbot.Entity.Messenger;

public abstract class Command {

    private User sender;
    private String commandName;
    private Messenger messenger;

    public Command(String commandName) {
        this.commandName = commandName;
        messenger = new Messenger();
    }

    public abstract boolean preExec(MessageReceivedEvent e, String[] args);

    public abstract boolean basePreExec(String arguments);

    public abstract void execute(MessageReceivedEvent e);

    public abstract void baseExecute();

    public void postExec() {
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Messenger getMessenger() {
        return messenger;
    }
}
