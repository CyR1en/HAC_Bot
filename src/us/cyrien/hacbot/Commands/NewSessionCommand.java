package us.cyrien.hacbot.Commands;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import us.cyrien.hacbot.Configuration.HACConfig;
import us.cyrien.hacbot.Util.MessengerUtil;
import us.cyrien.hacbot.main.HAC;

import java.awt.*;

public class NewSessionCommand extends Command {

    public NewSessionCommand(String CommandName) {
        super(CommandName);
    }

    @Override
    public boolean preExec(MessageReceivedEvent e, String[] args) {
        return true;
    }

    @Override
    public boolean basePreExec(String arguments) {
        return true;
    }

    @Override
    public void execute(MessageReceivedEvent e) {
        HAC.getInstance().getHacBot().newSession();
        EmbedBuilder eb = new EmbedBuilder().setColor(new Color(50, 201, 98));
        eb.setTitle("Bot Session Renewed", null);
        getMessenger().sendMessageEmbed(e, eb.build());
        HAC.LOGGER.info("Session Renewed by : " + getSender().getName());
    }

    public void baseExecute() {
        HAC.getInstance().getHacBot().newSession();
        EmbedBuilder eb = new EmbedBuilder().setColor(new Color(50, 201, 98));
        eb.setTitle("Bot Session Renewed", null);
        MessengerUtil.sendMessageEmbed(HAC.getInstance().getJda().getTextChannelById(HACConfig.get("text-channel")), eb.build());
        HAC.LOGGER.info("Session Renewed by : CONSOLE");
    }
}
