package com.fragmc.MinecraftBridge;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SyncDiscordCommand extends ListenerAdapter {

    public static boolean isReady() {
        // Implementation of the method
        return true; // or false depending on your logic
    }
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        // Your code to handle received messages
    }
}
