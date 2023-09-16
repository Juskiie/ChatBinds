package hoosk.fabric.chatbinds.client.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import hoosk.fabric.chatbinds.Chatbinds;
import hoosk.fabric.chatbinds.client.util.ChatBindsStorage;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;

import java.util.Objects;
import java.util.Optional;

import static hoosk.fabric.chatbinds.client.util.ChatBindsStorage.generateTranslationKeyFromString;

/**
 * @author Casey
 * @since 1.0
 * @version 1.0
 * Command class for creating chat binds
 */
public class CreateChatBind {
    public static void register() {
        ClientCommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess) -> {
            dispatcher.register(
                    ClientCommandManager.literal("chatbind").then(
                            ClientCommandManager.argument("key", StringArgumentType.string())
                                    .then(ClientCommandManager.argument("message", StringArgumentType.greedyString())
                                            .executes(context -> bindKey(context, StringArgumentType.getString(context, "key"), StringArgumentType.getString(context, "message")))
                                    )));
        }));
    }

    private static int bindKey(CommandContext<FabricClientCommandSource> context, String key, String message) {
        Optional<KeyBinding> availableBinding = Chatbinds.getAvailableDummyBinding();

        if (availableBinding.isPresent())
        {
            KeyBinding binding = availableBinding.get();
            // Bind the input key and message to the available dummy binding
            binding.setBoundKey(InputUtil.fromTranslationKey(generateTranslationKeyFromString(key)));
            ChatBindsStorage.setBind(key, binding, message);

            Objects.requireNonNull(MinecraftClient.getInstance().player).sendMessage(Text.of("Bound key " + key + " to message/command: " + message));
            context.getSource().sendFeedback(Text.literal("[Chatbinds] Executed successfully!"));
            return 1;
        }
        else
        {
            // Notify user that no more bindings are available or they should increase the MAX_BINDINGS value
            context.getSource().sendError(Text.literal("[Chatbinds] No available key bindings!"));
            return -1;
        }
    }
}