package com.fragmc.Tickets;

import com.fragmc.DatabaseManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.ButtonInteraction;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

public class ButtonClickListener extends ListenerAdapter {
    private DatabaseManager databaseManager;
    private Message message;
    private net.dv8tion.jda.api.interactions.components.buttons.Button button;
    private List<String> messageLog;

    public ButtonClickListener(Message message, net.dv8tion.jda.api.interactions.components.buttons.Button button,DatabaseManager databaseManager) {
        this.message = message;
        this.button = button;
        this.databaseManager = databaseManager;
        this.messageLog = new ArrayList<>();

    }

    public ButtonClickListener() {

    }

    private void logMessage(String content) {
        // Add the message to the log
        messageLog.add(content);
    }

    public List<String> getMessageLog() {
        return messageLog;
    }

    public String generateTranscript() {
        // Generate transcript from the logged messages
        StringBuilder transcript = new StringBuilder();
        for (String message : messageLog) {
            transcript.append(message).append("\n");
        }
        return transcript.toString();
    }


    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (event.getComponentId().equals("createTicket")) {
            Member member = event.getMember();
            if (member == null) {
                return;
            }

            String username = member.getUser().getName();

            // Get the current ticket number
            int currentTicketNumber;
            try {
                currentTicketNumber = databaseManager.getCurrentTicketNumber();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Insert ticket information into the database
            try {
                databaseManager.insertTicket(member.getId(), username, currentTicketNumber + 1);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Get the tickets category
            Category ticketsCategory = null;
            for (Category category : event.getGuild().getCategories()) {
                if (category.getName().equalsIgnoreCase("tickets")) {
                    ticketsCategory = category;
                    break;
                }
            }

            if (ticketsCategory != null) {
                // Create a new text channel within the tickets category
                String channelName = "ticket-" + (currentTicketNumber + 1);
                Guild guild = event.getGuild();
                ticketsCategory.createTextChannel(channelName).queue(channel -> {
                    // Send an embed with text
                    channel.upsertPermissionOverride(member)
                            .grant(Permission.VIEW_CHANNEL)
                            .grant(Permission.MESSAGE_SEND)
                            .queue();
                    Role staffRole = guild.getRolesByName("Helper", true).stream().findFirst().orElse(null);
                    channel.upsertPermissionOverride(staffRole)
                            .grant(Permission.VIEW_CHANNEL)
                            .grant(Permission.MESSAGE_SEND)
                            .queue();
                    channel.upsertPermissionOverride(guild.getPublicRole())
                            .setDenied(Permission.VIEW_CHANNEL)
                            .queue();
                    EmbedBuilder embedBuilder = new EmbedBuilder()
                            .setTitle(username + "'s Ticket")
                            .setDescription("Please answer the questions below in as much detail as possible.")
                            .addField("Question/Issue", "1. If the issue is ingame, What is your IGN?\n2. What do you need help with?", true)
                            .setFooter("DevHub", guild.getIconUrl())
                            .setColor(Color.GREEN);
                    channel.sendMessage(member.getAsMention() + " <@&" + staffRole.getId() + ">")
                            .setEmbeds(embedBuilder.build())
                            .queue();
                    System.out.println("Channel **\"" + event.getChannel().getName() + "\"** created successfully.");
                });
            }
            event.deferEdit().queue();
        }
    }
}