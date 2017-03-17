package us.cyrien.hacbot.Handle;

import us.cyrien.hacbot.Commands.Command;
import us.cyrien.hacbot.Parse.CommandParser;
import us.cyrien.hacbot.main.HAC;
import java.util.HashMap;

public class CommandHandler {

    public void handleCommand(CommandParser.CommandContainer cmd) {
        HashMap<String, Command> commands = HAC.getInstance().getCommands();
        if (commands.containsKey(cmd.invoke)) {
            commands.get(cmd.invoke).setSender(cmd.sender);
            if (commands.get(cmd.invoke).preExec(cmd.event, cmd.args)) {
                commands.get(cmd.invoke).execute(cmd.event);
                commands.get(cmd.invoke).postExec();
            } else {
                HAC.LOGGER.warn("Pre command execution failed.");
            }
        }
    }

}
