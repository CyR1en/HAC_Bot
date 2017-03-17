package us.cyrien.hacbot.main;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.utils.SimpleLog;
import us.cyrien.hacbot.Commands.Command;
import us.cyrien.hacbot.Commands.NewSessionCommand;
import us.cyrien.hacbot.Configuration.HACConfig;
import us.cyrien.hacbot.Entity.HACCBot;
import us.cyrien.hacbot.Entity.Messenger;
import us.cyrien.hacbot.Event.BotReadyEvent;
import us.cyrien.hacbot.Listener.CommandListener;
import us.cyrien.hacbot.Listener.MessageListener;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HAC {

    public static final String BUILD_NUMBER = "0.0.49";
    public static final SimpleLog LOGGER = SimpleLog.getLog("HACCBOT");

    private static HAC instance;

    @FXML
    private TextArea consoleArea;

    private HACCBot hacBot;
    private JDA jda;
    private HashMap<String, Command> commands;
    private List<String> messages;
    private Messenger messenger;
    private boolean initialized;

    public HAC() {
        initialized = HACConfig.load();
        hacBot = new HACCBot();
        commands = new HashMap<>();
        messages = new ArrayList<>();
        messenger = new Messenger();
        if (!initialized) {
            LOGGER.warn("HACConfig.json not found.");
            LOGGER.info("HACConfig generated. Please populate config fields before restarting.");
        }
        if (HACConfig.get("logToFiles", true)) {
            try {
                SimpleLog.addFileLogs(new File("logs/main.txt"), new File("logs/err.txt"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        commands.put("newsession", new NewSessionCommand("New Session Command"));
        instance = this;
    }

    public void run() {
        if (initialized)
            try {
                jda = new JDABuilder(AccountType.CLIENT).setToken(HACConfig.get("token")).buildAsync();
                jda.addEventListener(new MessageListener(hacBot));
                jda.addEventListener(new CommandListener());
                jda.addEventListener(new BotReadyEvent());
            } catch (LoginException | RateLimitedException e) {
                e.printStackTrace();
            }
        instance = this;
        LOGGER.info("Main Class Initialized");
    }

    public void stop() {
        if(jda != null) {
            jda.shutdown(false);
            jda = null;
            LOGGER.info("HACCBOT TURNED OFF");
        }
    }

    public JDA getJda() {
        return jda;
    }

    public Messenger getMessenger() {
        return messenger;
    }

    public List<String> getMessages() {
        return messages;
    }

    public HACCBot getHacBot() {
        return hacBot;
    }

    public HashMap<String, Command> getCommands() {
        return commands;
    }

    public static HAC getInstance() {
        return instance;
    }

}
