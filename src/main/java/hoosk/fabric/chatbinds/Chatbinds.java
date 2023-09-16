package hoosk.fabric.chatbinds;

import hoosk.fabric.chatbinds.client.commands.CreateChatBind;
import hoosk.fabric.chatbinds.client.util.ChatBindsStorage;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import java.util.*;

public class Chatbinds implements ModInitializer {

    private static final int MAX_BINDINGS = 10;
    private static final List<KeyBinding> DUMMY_BINDINGS = new ArrayList<>(MAX_BINDINGS);
    private static final long LAST_CHAT_BIND_USED = 0L;
    private static final long COOLDOWN_DURATION = 1000; // 1 second in milliseconds

    /**
     * Runs the mod initializer.
     */
    @Override
    public void onInitialize() {
        System.out.println("Initializing ChatBinds Mod! [FROM: Chatbinds]");

        for (int i = 0; i < MAX_BINDINGS; i++) {
            KeyBinding dummyBinding = new KeyBinding(
                    "key.chatbinds.dummy." + i,
                    InputUtil.Type.KEYSYM,
                    GLFW.GLFW_KEY_UNKNOWN, // Unknown key by default
                    "category.chatbinds"
            );
            KeyBindingHelper.registerKeyBinding(dummyBinding);
            DUMMY_BINDINGS.add(dummyBinding);
        }

        CreateChatBind.register();
        registerKeyChecks();
    }

    public static Optional<KeyBinding> getAvailableDummyBinding() {
        return DUMMY_BINDINGS.stream().filter(KeyBinding::isUnbound).findFirst();
    }

    private void registerKeyChecks() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            for (KeyBinding keyBinding : ChatBindsStorage.REGISTERED_KEYS) {
                if (keyBinding.isPressed()) {
                    String keyName = keyBinding.getTranslationKey().replace("key.chatbinds.", "");
                    String message = ChatBindsStorage.getBindMessage(keyBinding);

                    // DEBUG: Print to console the detected key and associated message
                    System.out.println("Detected key press: " + keyName + ". Message: " + message);

                    if (!message.isEmpty() && client.player != null && System.currentTimeMillis() - LAST_CHAT_BIND_USED > COOLDOWN_DURATION) {
                        System.out.println(message);
                        if(message.startsWith("/")) {
                            // client.player.sendMessage(Text.translatable("chat.run.command", message), false);
                            client.player.networkHandler.sendChatCommand(message.replaceFirst("/", ""));
                        } else {
                            // client.player.sendMessage(Text.translatable("chat.run.message", message), false);
                            client.player.networkHandler.sendChatMessage(message);
                        }
                    }
                }
            }
        });
    }
}
