package com.fragmc.Tickets.TicketCommands.TicketCreate;

import com.fragmc.DatabaseManager.DatabaseManager;
import com.fragmc.Tickets.Utils.ICommand;
import com.fragmc.Tickets.Utils.Method;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.List;

public class CreateTicketCommand implements ICommand {
    private DatabaseManager databaseManager;

    public CreateTicketCommand(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }


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
        if (event.getChannel() != event.getGuild().getTextChannelById(1162751244608225370L)){
            event.reply("Go to <@#"+1162751244608225370L+"> to execute this command.");
            return;
        }
        if (RealmName.equalsIgnoreCase("prisons")) {
            Method ticket = new Method(databaseManager);
            ticket.CreatePrisonsTicket(event.getGuild(), event.getMember(), event.getUser());
        }
        if (RealmName.equalsIgnoreCase("skyblock")){
            Method ticket = new Method(databaseManager);
            ticket.CreateSkyblockTicket(event.getGuild(), event.getMember(), event.getUser());
        }
        if (RealmName.equalsIgnoreCase("general")){
            Method ticket = new Method(databaseManager);
            ticket.CreateGeneralTicket(event.getGuild(), event.getMember(), event.getUser());
        }
    }
}
