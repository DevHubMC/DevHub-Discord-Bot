package com.fragmc.Tickets.TicketCommands.TicketCreate;

import com.fragmc.DatabaseManager.DatabaseManager;
import com.fragmc.Tickets.Utils.Getters;
import com.fragmc.Tickets.Utils.Method;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.sql.SQLException;

public class GeneralButtonListener extends ListenerAdapter {
    private DatabaseManager databaseManager;

    public GeneralButtonListener(DatabaseManager databaseManager){
        this.databaseManager = databaseManager;
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (this.databaseManager == null) {
            System.out.println("ERROR >> Database manager is null");
            return;
        }

        if (!event.getComponentId().equals("tickets:general")) {
            return;
        }

        Integer currentTicketNumber = null;

        try {
            currentTicketNumber = this.databaseManager.getCurrentTicketNumber();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error getting current ticket number");
        }

        if (currentTicketNumber != null) {
            // Insert ticket information into the database

            String channelName = "ticket-" + (currentTicketNumber + 1);

            Category category = event.getGuild().getCategoriesByName("general tickets", true).stream().findFirst().orElse(null);

            if (category != null) {
                // Create the ticket channel in the category [general tickets]
                int finalCurrentTicketNumber = currentTicketNumber;
                category.createTextChannel(channelName).queue(ticket -> {

                    try {
                        databaseManager.insertTicket(event.getMember().getUser().getName(), finalCurrentTicketNumber + 1, ticket.getIdLong());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    Getters get = new Getters(event.getMember(), event.getUser(), event.getGuild(), ticket);
                    Method a = new Method(databaseManager);
                    Role general = event.getGuild().getRolesByName("General Support", true).stream().findFirst().orElse(null);
                    // Set the permissions of the ticket channel
                    ticket.upsertPermissionOverride(get.getMember())
                            .grant(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND)
                            .queue();
                    ticket.upsertPermissionOverride(general)
                            .grant(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND)
                            .queue();
                    ticket.upsertPermissionOverride(get.getGuild().getPublicRole())
                            .deny(Permission.VIEW_CHANNEL)
                            .queue();

                    // Send the embed to the ticket channel
                    ticket.sendMessage(get.getTicketOwner().getAsMention())
                            .setEmbeds(a.GeneralTicketEmbed(get.getGuild()))
                            .queue();

                });
            } else {
                System.out.println("ERROR >> There is no such category as general tickets");
            }
        }
    }
}
