package us.cyrien.hacbot.GUI.jfx;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.utils.SimpleLog;
import us.cyrien.hacbot.Configuration.HACConfig;
import us.cyrien.hacbot.Handle.ConsoleCommandHandler;
import us.cyrien.hacbot.Launcher;
import us.cyrien.hacbot.Parse.ConsoleCommandParser;
import us.cyrien.hacbot.Util.MessengerUtil;
import us.cyrien.hacbot.main.HAC;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TextArea consoleArea;
    @FXML
    private TextField inputField;

    private boolean isON = false;

    public void onInput(ActionEvent ae) {
        if(isON) {
            String input = inputField.getText();
            ConsoleCommandParser.CommandContainer cmd = ConsoleCommandParser.parse(input);
            boolean notCommand = !HAC.getInstance().getCommands().containsKey(cmd.invoke);
            if (notCommand) {
                TextChannel tc = HAC.getInstance().getJda().getTextChannelById(HACConfig.get("text-channel"));
                MessengerUtil.sendMessage(tc, input);
                System.out.println("[CONSOLE]" + " > " + input);
            } else {
                ConsoleCommandHandler.handleCommand(cmd);
            }
            inputField.setText("");
        } else {
            SimpleLog.getLog("HACCBOT").warn("PLEASE RUN THE BOT FIRST");
        }
    }

    public void buttonPress(ActionEvent ae) {
        System.out.println("Button Pressed");
    }

    public void startHAC(ActionEvent ae) {
        if (!isON) {
            SimpleLog.getLog("HAC").info("HACCBOT STARTING.....");
            Launcher.bot.run();
            isON = !isON;
        } else {
            System.out.println("HAC Bot is already running");
        }
    }

    public void stopHAC(ActionEvent ae) {
        if (isON) {
            SimpleLog.getLog("HAC").info("HACCBOT SHUTTING DOWN.....");
            Launcher.bot.stop();
            Launcher.bot = null;
            isON = !isON;
        } else {
            System.out.println("HAC Bot is not running");
        }
    }

    public void newSession(ActionEvent ae) {
        if (isON)
            try {
                HAC.getInstance().getCommands().get("newsession").baseExecute();
            } catch (Exception e) {
                e.getCause().printStackTrace();
                e.printStackTrace();
            }
        else
            System.out.println("HAC Bot is not running");
    }

    public void appendText(String valueOf) {
        Platform.runLater(() -> consoleArea.appendText(valueOf));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        OutputStream out = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                appendText(String.valueOf((char) b));
            }
        };
        System.setOut(new PrintStream(out, true));
        System.setErr(new PrintStream(out, true));
    }
}