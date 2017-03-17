package us.cyrien.hacbot.Entity;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;


public class Messenger {

    private ScheduledExecutorService scheduler;

    public Messenger() {
        scheduler = Executors.newScheduledThreadPool(1);
    }

    public void sendMessage(MessageReceivedEvent e, String message, Consumer<Message> consumer) {
        e.getTextChannel().sendMessage(message).queue(consumer);
    }

    public void sendMessageEmbed(MessageReceivedEvent e, MessageEmbed me, Consumer<Message> consumer) {
        e.getTextChannel().sendMessage(me).queue(consumer);
    }

    public void sendMessageEmbed(MessageReceivedEvent e, MessageEmbed me) {
        sendMessageEmbed(e, me, null);
    }

    public void sendMessage(MessageReceivedEvent e, String message) {
        sendMessage(e, message, null);
    }

    public void sendTempMessage(MessageReceivedEvent e, String message, int length) {
        e.getTextChannel().sendMessage(message).queue(msg -> scheduler.schedule(() -> msg.deleteMessage().queue(), length, TimeUnit.SECONDS));
    }

    public void sendTempMessageEmbed(MessageReceivedEvent e, MessageEmbed me, int length) {
        e.getTextChannel().sendMessage(me).queue(msg -> scheduler.schedule(() -> msg.deleteMessage().queue(), length, TimeUnit.SECONDS));
    }

}
