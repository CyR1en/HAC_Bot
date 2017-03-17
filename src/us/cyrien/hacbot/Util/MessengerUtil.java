package us.cyrien.hacbot.Util;

import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.TextChannel;

public class MessengerUtil {

    public static void sendMessageEmbed(TextChannel tc, MessageEmbed message) {
        tc.sendMessage(message).queue();
    }

    public static void sendMessage(TextChannel tc, String message) {
        tc.sendMessage(message).queue();
    }
}
