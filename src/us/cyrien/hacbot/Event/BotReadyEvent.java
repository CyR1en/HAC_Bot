package us.cyrien.hacbot.Event;

import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import us.cyrien.hacbot.Configuration.HACConfig;
import us.cyrien.hacbot.Entity.HACCBot;
import us.cyrien.hacbot.main.HAC;


public class BotReadyEvent extends ListenerAdapter {
    @Override
    public void onReady(ReadyEvent event) {
        System.out.println("========================================================");
        System.out.println("|        HACCBot Ready to Have a Conversation           |");
        System.out.println("========================================================");
        System.out.println("Bound to : " + event.getJDA().getTextChannelById(HACConfig.get("text-channel")));
        System.out.println("Type Speed : " + HACCBot.TYPING_SPEED + " wpm");
        System.out.println("Build Number : " + HAC.BUILD_NUMBER);
    }
}
