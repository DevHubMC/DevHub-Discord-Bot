package com.fragmc.Tickets.TicketCreate;

import com.fragmc.Tickets.Utils.ICommand;
import com.fragmc.Tickets.Utils.Method;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.List;

public class CreateTicketCommand implements ICommand {
    @Override
    public String getName() {
        return "ticket";
    }

    @Override
    public String getDescription() {
        return "creates a support ticket for the user";
    }

    @Override
    public List<OptionData> getOptions() {
        List<OptionData> data = new ArrayList<>();
        data.add(new OptionData(OptionType.STRING, "realm", "the realm you want to create a ticket for", true));
        return data;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        OptionMapping realm = event.getOption("realm");
        String RealmName = realm.getAsString();

        // NOTE: CHECKS IF THE COMMAND SEND HAD PRISONS AS THE ARGUMENT.
        if (RealmName.equalsIgnoreCase("prisons")) {
            Method ticket = new Method();
            ticket.CreatePrisonsTicket(event.getGuild(), event.getMember(), event.getUser());
        }
        if (RealmName.equalsIgnoreCase("skyblock")){{
            Method ticket = new Method();
            ticket.CreateSkyblockTicket(event.getGuild(), event.getMember(), event.getUser());
        }}
    }
}
