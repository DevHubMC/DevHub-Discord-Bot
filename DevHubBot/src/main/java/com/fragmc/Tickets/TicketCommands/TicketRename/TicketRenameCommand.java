package com.fragmc.Tickets.TicketCommands.TicketRename;

import com.fragmc.Tickets.Utils.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TicketRenameCommand implements ICommand {
    @Override
    public String getName() {
        return "rename";
    }

    @Override
    public String getDescription() {
        return "A command to rename a ticket.";
    }

    @Override
    public List<OptionData> getOptions() {
        List<OptionData> data = new ArrayList<>();
        data.add(new OptionData(OptionType.STRING, "name", "The new name of the ticket.", true));
        data.add(new OptionData(OptionType.STRING, "ticket", "The ticket you are renaming", false));
        return data;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        OptionMapping Ticket = event.getOption("name");
        OptionMapping OldTicket = event.getOption("ticket");
        String TicketName = Ticket.getAsString();
        String OldTicketName = null;
        Guild guild = event.getGuild();

        if (OldTicket != null){
            try {
                OldTicketName = OldTicket.getAsString();

                Category PrisonCategory = null;
                Category SkyblockCategory = null;
                Category GeneralCategory = null;
                for (Category category : guild.getCategories()){
                    if (category.getName().equalsIgnoreCase("prisons tickets")) {
                        PrisonCategory = category;
                    }
                    if (category.getName().equalsIgnoreCase("skyblock tickets")){
                        SkyblockCategory = category;
                    }
                    if (category.getName().equalsIgnoreCase("general tickets")){
                        GeneralCategory = category;
                    }
                }

                TextChannel channel = guild.getTextChannelsByName(OldTicketName, true).stream().findFirst().orElse(null);
                Role admin = event.getGuild().getRolesByName("Admin", true).stream().findFirst().orElse(null);
                Role founder = event.getGuild().getRolesByName("Founder", true).stream().findFirst().orElse(null);
                Role general = event.getGuild().getRolesByName("General Support", true).stream().findFirst().orElse(null);
                Role skyblock = event.getGuild().getRolesByName("Skyblock Support", true).stream().findFirst().orElse(null);
                Role prisons = event.getGuild().getRolesByName("Prisons Support", true).stream().findFirst().orElse(null);
                if (event.getMember().getRoles().contains(admin) || event.getMember().getRoles().contains(founder) || event.getMember().getRoles().contains(general) || event.getMember().getRoles().contains(skyblock) || event.getMember().getRoles().contains(prisons)) {
                    if (PrisonCategory.getChannels().contains(channel) || SkyblockCategory.getChannels().contains(channel) || GeneralCategory.getChannels().contains(channel)) {

                        EmbedBuilder embedBuilder = new EmbedBuilder()
                                .setTitle("Ticket Renamed - " + event.getMember().getAsMention())
                                .setDescription("➣ Old Name [" + OldTicketName + "]\n➣ New Name [" + TicketName + "]")
                                .setFooter("DevHub", guild.getIconUrl())
                                .setColor(Color.GREEN);

                        event.replyEmbeds(embedBuilder.build()).queue();
                        channel.getManager().setName(TicketName).queue();
                    } else {
                        event.reply("**Error**: Cannot rename a non-ticket channel.").queue();
                    }
                }
            }catch(NullPointerException e){
                e.printStackTrace();
            }
        }else{
            Category PrisonCategory = null;
            Category SkyblockCategory = null;
            Category GeneralCategory = null;
            for (Category category : guild.getCategories()){
                if (category.getName().equalsIgnoreCase("prisons tickets")) {
                    PrisonCategory = category;
                }
                if (category.getName().equalsIgnoreCase("skyblock tickets")){
                    SkyblockCategory = category;
                }
                if (category.getName().equalsIgnoreCase("general tickets")){
                    GeneralCategory = category;
                }
            }

            TextChannel channel = event.getChannel().asTextChannel();
            Role admin = event.getGuild().getRolesByName("Admin", true).stream().findFirst().orElse(null);
            Role founder = event.getGuild().getRolesByName("Founder", true).stream().findFirst().orElse(null);
            Role general = event.getGuild().getRolesByName("General Support", true).stream().findFirst().orElse(null);
            Role skyblock = event.getGuild().getRolesByName("Skyblock Support", true).stream().findFirst().orElse(null);
            Role prisons = event.getGuild().getRolesByName("Prisons Support", true).stream().findFirst().orElse(null);
            if (event.getMember().getRoles().contains(admin) || event.getMember().getRoles().contains(founder) || event.getMember().getRoles().contains(general) || event.getMember().getRoles().contains(skyblock) || event.getMember().getRoles().contains(prisons)) {
                if (PrisonCategory.getChannels().contains(channel) || SkyblockCategory.getChannels().contains(channel) || GeneralCategory.getChannels().contains(channel)) {
                    EmbedBuilder embedBuilder = new EmbedBuilder()
                            .setTitle("Ticket Renamed - " + event.getMember().getUser().getName())
                            .setDescription("➣ Old Name [\"**" + channel.getName() + "**\"]\n➣ New Name [\"**" + TicketName + "**\"]")
                            .setFooter("DevHub", guild.getIconUrl())
                            .setColor(Color.GREEN);

                    event.replyEmbeds(embedBuilder.build()).queue();
                    channel.getManager().setName(TicketName).queue();
                } else {
                    event.reply("**Error**: Cannot rename a non-ticket channel.").queue();
                }
            }
        }

    }
}
