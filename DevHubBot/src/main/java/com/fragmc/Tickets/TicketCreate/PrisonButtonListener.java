package com.fragmc.Tickets.TicketCreate;

import com.fragmc.Tickets.Utils.*;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Objects;

public class PrisonButtonListener extends ListenerAdapter {

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (event.getComponentId().equals("tickets:prisons")) {
            int currentTicketNumber = 0;
            String channelName = "ticket-" + (currentTicketNumber) + 1;

            Category category = null;
            for (Category categories : event.getGuild().getCategories()) {
                if (categories.getName().equalsIgnoreCase("prisons tickets")) {
                    category = categories;
                    break;
                }
            }

            if (category != null){

                // NOTE: CREATES THE TICKET CHANNEL IN CATEGORY [PRISONS TICKETS]
                category.createTextChannel(channelName).queue(ticket -> {
                    Getters get = new Getters(event.getMember(), event.getUser(), event.getGuild(), ticket);
                    Method a = new Method();

                    // NOTE: SETS THE PERMISSIONS OF THE TICKET CHANNEL SO ONLY @STAFF AND THE TICKET OWNER CAN SEE IT.
                    ticket.upsertPermissionOverride(get.getMember())
                            .grant(Permission.VIEW_CHANNEL)
                            .grant(Permission.MESSAGE_SEND)
                            .queue();
                    ticket.upsertPermissionOverride(Objects.requireNonNull(get.getGuild().getRolesByName("Staff", true).stream().findFirst().orElse(null)))
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
