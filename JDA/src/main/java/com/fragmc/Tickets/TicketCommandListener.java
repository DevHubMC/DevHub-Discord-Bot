package com.fragmc.Tickets;

import com.fragmc.Config;
import com.fragmc.DatabaseManager;
import com.fragmc.StaffApps.AppCreateClass;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonInteraction;

import java.awt.Color;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;


public class TicketCommandListener extends ListenerAdapter {
    private DatabaseManager databaseManager;

    @Override
    public void onReady(ReadyEvent event) {
        // Replace "tickets" with the actual name of your channel
        String channelName = "tickets";
        TextChannel ticketsChannel = event.getJDA().getGuildById(Config.getGuildID()).getTextChannelsByName("tickets", true).stream().findFirst().orElse(null);

        // Check if the channel exists
        if (ticketsChannel != null) {
            // Purge (delete) all messages in the channel
            ticketsChannel.purgeMessages(ticketsChannel.getIterableHistory().complete());
        }
        // Get the text channel by name
        event.getJDA().getGuilds().forEach(guild -> {
            guild.getTextChannelsByName(channelName, true).forEach(channel -> {
                sendTicketEmbed(channel);
            });
        });
    }

    public TicketCommandListener(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] command = event.getMessage().getContentRaw().split("\\s+");

        if (command[0].equalsIgnoreCase("!tickets")) {
            sendTicketEmbed(event.getChannel().asTextChannel());
        }
    }

    private void sendTicketEmbed(TextChannel channel) {
        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setTitle("Ticket System")
                .setDescription("Click the button below to create a ticket.")
                .setColor(Color.BLUE);

        Button createTicketButton = Button.primary("createTicket", "Create Ticket");

        MessageEmbed embed = embedBuilder.build();

        CompletableFuture<Message> future = channel.sendMessageEmbeds(embed)
                .setActionRow(createTicketButton)
                .submit();

        future.thenAccept(message -> {
            // Add a button click listener for the created message
            channel.getJDA().addEventListener(new ButtonClickListener( message, createTicketButton, databaseManager));
        });
    }
}