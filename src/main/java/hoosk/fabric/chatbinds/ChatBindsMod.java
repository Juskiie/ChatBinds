package hoosk.fabric.chatbinds;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ChatBindsMod {
    public static final KeyBinding CHAT_BIND_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.chatbinds.bind",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_B, // Replace with the key to use
            "category.chatbinds"
    ));
}
