package hoosk.fabric.chatbinds.client.bindings;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public enum ModBindings {
    EXAMPLE(new KeyBinding("key.chat_bind.Example", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_INSERT, "Hoosk Client"));

    private final KeyBinding binding;

    ModBindings(KeyBinding binding) {
        this.binding = binding;
    }

    public KeyBinding getBinding() {
        return this.binding;
    }

    public void onPress() {
        switch (this) {
            case EXAMPLE -> System.out.println("[ChatBinds] This is an example message, only you can see this!");
            default -> {
                // Do nothin.
            }
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
