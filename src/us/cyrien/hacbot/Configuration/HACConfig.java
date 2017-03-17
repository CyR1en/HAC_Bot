package us.cyrien.hacbot.Configuration;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class HACConfig {

    private static final Path configPath = Paths.get("HACBot.json");
    private static JSONObject config;

    public static void set(@NotNull String key, @Nullable Object val) {
        if (val == null) {
            config.remove(key);
        } else {
            config.put(key, val);
        }
        save();
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(@NotNull String key) {
        if (config.has(key)) {
            try {
                return (T) config.get(key);
            } catch (JSONException | ClassCastException ignored) {
            }
        }
        return null;
    }

    public static <T> T get(@NotNull String key, @NotNull T def) {
        T obj = get(key);
        if (obj == null) {
            obj = def;
            config.put(key, def);
            save();
        }
        return obj;
    }

    public static void save() {
        try {
            Files.write(configPath, config.toString(4).getBytes(StandardCharsets.UTF_8));
        } catch (IOException ignored) {
        }
    }

    public static boolean load() {
        boolean exists = Files.exists(configPath);
        try {
            JSONObject def = getDefault();
            if (!exists) {
                Files.write(configPath, def.toString(4).getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);
            } else {
                config = new JSONObject(new String(Files.readAllBytes(configPath), StandardCharsets.UTF_8));
                for (String key : def.keySet()) {
                    if (!config.has(key)) {
                        config.put(key, def.get(key));
                        exists = false;
                    }
                }
                if (!exists) {
                    save();
                }
            }
        } catch (IOException ex) {
            System.err.println("Error reading/writing config file: ");
            ex.printStackTrace();
            return false;
        }
        return exists;
    }

    private static JSONObject getDefault() {
        return new JSONObject()
                .put("token", " ")
                .put("logFile", "true")
                .put("trigger", " ")
                .put("text-channel", " ")
                .put("log-channel", " ")
                .put("api-key", " ");
    }

}