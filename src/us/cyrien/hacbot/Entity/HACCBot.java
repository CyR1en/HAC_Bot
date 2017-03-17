package us.cyrien.hacbot.Entity;

import com.google.gson.*;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import us.cyrien.hacbot.Configuration.HACConfig;
import us.cyrien.hacbot.Util.MessengerUtil;
import us.cyrien.hacbot.main.HAC;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HACCBot {

    public static final double TYPING_SPEED = 80.0; //WPM

    private ScheduledExecutorService scheduledExecutorService;
    private Messenger messenger;
    private CleverBotQuery cb;

    public HACCBot() {
        messenger = new Messenger();
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        cb = new CleverBotQuery(HACConfig.get("api-key"), "");
    }

    public void newSession() {
        cb = new CleverBotQuery(HACConfig.get("api-key"), "");
    }

    public void respondTo(String input, MessageReceivedEvent e) {
        final String response;
        try {
            cb.setPhrase(input);
            cb.sendRequest();
            response = cb.getResponse();
            double typingDurationMilli = ((double) (response.length() / 5) / (TYPING_SPEED / 60)) * 1000;
            if (typingDurationMilli > 10000)
                e.getTextChannel().sendTyping().queue(
                        msg -> scheduledExecutorService.schedule(
                                () -> sendMessage(e, response, typingDurationMilli), 10, TimeUnit.SECONDS));
            else
                sendMessage(e, response, typingDurationMilli);

            System.out.println("----------------------------------------------------------------------");
            JsonObject jo = cb.getJsonObject();

            JsonPrimitive elem = new JsonPrimitive(typingDurationMilli + " ms");
            jo.add("typing-duration", elem);

            Gson gb = new GsonBuilder().setPrettyPrinting().create();
            String json = gb.toJson(jo);
            System.out.println(json);

            TextChannel tc = HAC.getInstance().getJda().getTextChannelById(HACConfig.get("log-channel"));
            MessengerUtil.sendMessage(tc, "```json\n" + json + "\n```");
            System.out.println("----------------------------------------------------------------------");
        } catch (Exception e1) {
            e1.printStackTrace();
            e1.getCause();
        }
    }

    public void sendMessage(MessageReceivedEvent e, String response, double typingDurationMilli) {
        e.getChannel().sendTyping().queue(msg -> scheduledExecutorService.schedule(
                () -> messenger.sendMessage(e, response), (long) typingDurationMilli, TimeUnit.MILLISECONDS));
    }

}
