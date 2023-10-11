package com.fragmc.StaffApps;

import com.fragmc.Config;
import com.fragmc.DatabaseManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;
import java.util.concurrent.CompletableFuture;

public class AppCommandListener extends ListenerAdapter {
    private DatabaseManager databaseManager;
    private JDA jda;

    public AppCommandListener(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        this.jda = jda;
    }

    @Override
    public void onReady(ReadyEvent event) {
        // Replace "tickets" with the actual name of your channel
        String channelNameApp = "applications";
        TextChannel appChannel = event.getJDA().getGuildById(Config.getGuildID()).getTextChannelsByName("applications", true).stream().findFirst().orElse(null);

        if (appChannel != null) {
            // Purge (delete) all messages in the channel
            appChannel.purgeMessages(appChannel.getIterableHistory().complete());
        }
        // Get the text channel by name
        event.getJDA().getGuilds().forEach(guild -> {
            guild.getTextChannelsByName(channelNameApp, true).forEach(channel -> {
                sendAppEmbed(channel);
            });
        });
    }


    private void sendAppEmbed(TextChannel channel) {
        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setTitle("Staff Applications")
                .setDescription("Click the button below to apply for staff.")
                .setColor(Color.PINK);

        net.dv8tion.jda.api.interactions.components.buttons.Button createTicketButton = Button.primary("staffApps", "Apply");

        MessageEmbed embed = embedBuilder.build();

        CompletableFuture<Message> future = channel.sendMessageEmbeds(embed)
                .setActionRow(createTicketButton)
                .submit();

        future.thenAccept(message -> {
            // Add a button click listener for the created message
            channel.getJDA().addEventListener(new AppCreateClass(createTicketButton, databaseManager, jda));
        });
    }
}
