package com.fragmc.Tickets.TicketClose;

import com.fragmc.Tickets.Utils.ICommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.List;

public class CloseTicketCommand implements ICommand {
    @Override
    public String getName() {
        return "close";
    }

    @Override
    public String getDescription() {
        return "A command to close the tickets.";
    }

    @Override
    public List<OptionData> getOptions() {
        List<OptionData> data = new ArrayList<>();
        data.add(new OptionData(OptionType.STRING, "channel", "the ticket that you want to close", false));
        return data;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        OptionMapping option = event.getOptions("channel");
        String channelOption = option.getAsString;

        Category category;
        if (channelOption == null) {
            for (Category categories : event.getGuild()){
                  if (categories.equalsIgnoreCase("Prisons Tickets")){
                      category = categories;
                  }
            }

          
            
        }

        }
    }
}
