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
import java.util.Objects;

public class SkyblockButtonListener extends ListenerAdapter {
    private DatabaseManager databaseManager;

    public SkyblockButtonListener(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }


    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (event.getComponentId().equals("tickets:skyblock")) {
            int currentTicketNumber;
            try {
                currentTicketNumber = databaseManager.getCurrentTicketNumber();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Insert ticket information into the database
            String channelName = "ticket-" + (currentTicketNumber +1);

            Category category = null;
            for (Category categories : event.getGuild().getCategories()) {
                if (categories.getName().equalsIgnoreCase("skyblock tickets")) {
                    category = categories;
                    break;
                }
            }

            if (category != null){

                // NOTE: CREATES THE TICKET CHANNEL IN CATEGORY [PRISONS TICKETS]
                int finalCurrentTicketNumber = currentTicketNumber;
                category.createTextChannel(channelName).queue(ticket -> {

                    try {
                        databaseManager.insertTicket(event.getMember().getUser().getName(), finalCurrentTicketNumber + 1, ticket.getIdLong());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    Getters get = new Getters(event.getMember(), event.getUser(), event.getGuild(), ticket);
                    Method a = new Method(databaseManager);
                    Role skyblock = event.getGuild().getRolesByName("Skyblock Support", true).stream().findFirst().orElse(null);

                    // NOTE: SETS THE PERMISSIONS OF THE TICKET CHANNEL SO ONLY @STAFF AND THE TICKET OWNER CAN SEE IT.
                    ticket.upsertPermissionOverride(get.getMember())
                            .grant(Permission.VIEW_CHANNEL)
                            .grant(Permission.MESSAGE_SEND)
                            .queue();
                    ticket.upsertPermissionOverride(Objects.requireNonNull(skyblock))
                            .grant(Permission.VIEW_CHANNEL)
                            .grant(Permission.MESSAGE_SEND)
                            .queue();
                    ticket.upsertPermissionOverride(get.getGuild().getPublicRole())
                            .deny(Permission.VIEW_CHANNEL)
                            .queue();

                    // NOTE: SENDS THE EMBED TO THE TICKET CHANNEL [LINE 85]
                    ticket.sendMessage(get.getTicketOwner().getAsMention())
                            .setEmbeds(a.TicketEmbed(get.getGuild()))
                            .queue();

                });
            }else{

                // NOTE: IF THE CATEGORY [PRISONS TICKETS] DOESN'T EXIST, IT SENDS AN ERROR LOG TO CONSOLE
                System.out.println("ERROR >> There is no such category as "+category);
                return;
            }
        }
    }
}
