package hoosk.fabric.chatbinds.client.util;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Casey
 * @since 1.0
 * @version 1.0
 * Utility class for storing chat bind messages
 */
public class ChatBindsStorage {
    private static final Map<KeyBinding, String> KEY_BINDS = new HashMap<>();
    public static final List<KeyBinding> REGISTERED_KEYS = new ArrayList<>();

    public static void setBind(String key, KeyBinding dummyBinding, String message) {
        // Re-assign the actual key to the dummy binding
        dummyBinding.setBoundKey(InputUtil.fromTranslationKey(generateTranslationKeyFromString(key)));

        // This map now associates a KeyBinding instance with the message, not just a key string
        KEY_BINDS.put(dummyBinding, message);

        // Add the keybinding to the registered keys list
        // This may not be necessary if you already have it in the list from initialization phase.
        // Just ensuring we have it in our list.
        if(!REGISTERED_KEYS.contains(dummyBinding)) {
            REGISTERED_KEYS.add(dummyBinding);
        }
    }

    public static String generateTranslationKeyFromString(String inputKey) {
        return "key.keyboard." + inputKey.toLowerCase();
    }

    public static String getBindMessage(KeyBinding key) {
        return KEY_BINDS.getOrDefault(key, "");
    }
}
