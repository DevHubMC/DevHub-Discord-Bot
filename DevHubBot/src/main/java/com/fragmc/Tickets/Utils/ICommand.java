package com.fragmc.Tickets.Utils;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.io.IOException;
import java.util.List;

public interface ICommand {
    String getName();

    String getDescription();

    List<OptionData> getOptions();

    void execute (SlashCommandInteractionEvent event) throws IOException;

}
