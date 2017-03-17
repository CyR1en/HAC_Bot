package us.cyrien.hacbot.Handle;

import us.cyrien.hacbot.Commands.Command;
import us.cyrien.hacbot.Parse.CommandParser;
import us.cyrien.hacbot.Parse.ConsoleCommandParser;
import us.cyrien.hacbot.main.HAC;

import java.util.HashMap;

/**
 * Created by Ethan on 2/13/2017.
 */
public class ConsoleCommandHandler {
    public static void handleCommand(ConsoleCommandParser.CommandContainer cmd) {
        HashMap<String, Command> commands = HAC.getInstance().getCommands();
        if (commands.containsKey(cmd.invoke)) {
            if (commands.get(cmd.invoke).preExec(null, cmd.args)) {
                commands.get(cmd.invoke).baseExecute();
                commands.get(cmd.invoke).postExec();
            } else {
                HAC.LOGGER.warn("Pre command execution failed.");
            }
        }
    }
}
