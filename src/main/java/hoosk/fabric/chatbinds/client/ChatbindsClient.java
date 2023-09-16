package hoosk.fabric.chatbinds.client;

import hoosk.fabric.chatbinds.client.commands.CreateChatBind;
import net.fabricmc.api.ClientModInitializer;

public class ChatbindsClient implements ClientModInitializer {
    /**
     * Runs the mod initializer on the client environment.
     */
    @Override
    public void onInitializeClient() {
        System.out.println("Initializing ChatBinds Mod! [FROM: ChatbindsClient]");
        CreateChatBind.register();
    }
}