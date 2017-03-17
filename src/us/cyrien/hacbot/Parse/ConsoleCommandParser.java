package us.cyrien.hacbot.Parse;

import us.cyrien.hacbot.Configuration.HACConfig;

import java.util.ArrayList;

public class ConsoleCommandParser {

    public static ConsoleCommandParser.CommandContainer parse(String rw) {
        ArrayList<String> split = new ArrayList<>();
        String beheaded = rw.replaceFirst(HACConfig.get("trigger"), "");
        String[] splitBeheaded = beheaded.split(" ");
        for (String s : splitBeheaded)
            split.add(s);
        String invoke = split.get(0).toLowerCase();
        String[] args = new String[split.size() - 1];
        split.subList(1, split.size()).toArray(args);
        return new ConsoleCommandParser.CommandContainer(rw, beheaded, splitBeheaded, invoke, args);
    }

    public static class CommandContainer {
        public static String raw;
        public static String beheaded;
        public static String[] splitBeheaded;
        public static String invoke;
        public static String[] args;

        public CommandContainer(String raw, String beheaded, String[] splitBeheaded, String invoke, String[] args) {
            ConsoleCommandParser.CommandContainer.raw = raw;
            ConsoleCommandParser.CommandContainer.beheaded = beheaded;
            ConsoleCommandParser.CommandContainer.splitBeheaded = splitBeheaded;
            ConsoleCommandParser.CommandContainer.invoke = invoke;
            ConsoleCommandParser.CommandContainer.args = args;
        }
    }
}
