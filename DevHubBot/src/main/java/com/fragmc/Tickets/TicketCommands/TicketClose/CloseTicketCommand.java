package com.fragmc.Tickets.TicketCommands.TicketClose;

import com.fragmc.DatabaseManager.DatabaseManager;
import com.fragmc.Tickets.DiscordHtmlTranscripts;
import com.fragmc.Tickets.Utils.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.awt.*;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    public void execute(SlashCommandInteractionEvent event) throws IOException {
        OptionMapping realm = event.getOption("channel");
        Guild guild = event.getGuild();
        String ChannelName = null;
        if (realm != null) {
            try {
                ChannelName = realm.getAsString();
                if (ChannelName.contains("ticket")) {
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
                    TextChannel transcripts = event.getGuild().getTextChannelsByName("transcripts", true).stream().findFirst().orElse(null);
                    Role admin = event.getGuild().getRolesByName("Admin", true).stream().findFirst().orElse(null);
                    Role founder = event.getGuild().getRolesByName("Founder", true).stream().findFirst().orElse(null);
                    Role general = event.getGuild().getRolesByName("General Support", true).stream().findFirst().orElse(null);
                    Role skyblock = event.getGuild().getRolesByName("Skyblock Support", true).stream().findFirst().orElse(null);
                    Role prisons = event.getGuild().getRolesByName("Prisons Support", true).stream().findFirst().orElse(null);
                    if (event.getMember().getRoles().contains(admin) || event.getMember().getRoles().contains(founder) || event.getMember().getRoles().contains(general) || event.getMember().getRoles().contains(skyblock) || event.getMember().getRoles().contains(prisons)) {
                        if (PrisonCategory.getChannels().contains(channel) || SkyblockCategory.getChannels().contains(channel) || GeneralCategory.getChannels().contains(channel)) {

                            DiscordHtmlTranscripts transcript = DiscordHtmlTranscripts.getInstance();

                            DatabaseManager databaseManager = new DatabaseManager();
                            String ticketOwner = databaseManager.getTicketOwner(Integer.parseInt(channel.getId()));

                            EmbedBuilder transcriptEmbed = new EmbedBuilder()
                                    .setTitle("Transcript - "+channel.getName())
                                    .setDescription("Ticket Transcript")
                                    .setFooter("DevHub", guild.getIconUrl())
                                    .setColor(Color.red);

                            Member ticketOwnerA = event.getGuild().getMembersByName(ticketOwner, true).stream().findFirst().orElse(null);
                            User user = ticketOwnerA.getUser();

                            transcripts.sendMessageEmbeds(transcriptEmbed.build()).queue();
                            transcript.createTranscript(channel, transcripts);
                            transcript.createTranscript(channel, (TextChannel) user.openPrivateChannel());

                            EmbedBuilder embedBuilder = new EmbedBuilder()
                                    .setTitle("Ticket Deleted - " + channel.getName())
                                    .setDescription("Closed a ticket.")
                                    .setFooter("DevHub", guild.getIconUrl())
                                    .setColor(Color.GREEN);

                            event.replyEmbeds(embedBuilder.build())
                                    .delay(Duration.ofSeconds(5))
                                    .queue();
                            channel.delete().completeAfter(5, TimeUnit.SECONDS);
                            // Additional ticket closing code (e.g., databaseManager.closeTicket(userId)) can be placed here if needed.// Additional ticket closing code (e.g., databaseManager.closeTicket(userId)) can be placed here if needed.
                        } else {
                            event.reply("**Error**: Cannot close a non-ticket channel.").queue();
                        }
                    }
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
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
            TextChannel transcripts = event.getGuild().getTextChannelsByName("transcripts", true).stream().findFirst().orElse(null);
            Role admin = event.getGuild().getRolesByName("Admin", true).stream().findFirst().orElse(null);
            Role founder = event.getGuild().getRolesByName("Founder", true).stream().findFirst().orElse(null);
            Role general = event.getGuild().getRolesByName("General Support", true).stream().findFirst().orElse(null);
            Role skyblock = event.getGuild().getRolesByName("Skyblock Support", true).stream().findFirst().orElse(null);
            Role prisons = event.getGuild().getRolesByName("Prisons Support", true).stream().findFirst().orElse(null);
            if (event.getMember().getRoles().contains(admin) || event.getMember().getRoles().contains(founder) || event.getMember().getRoles().contains(general) || event.getMember().getRoles().contains(skyblock) || event.getMember().getRoles().contains(prisons)) {
                if (PrisonCategory.getChannels().contains(channel) || SkyblockCategory.getChannels().contains(channel) || GeneralCategory.getChannels().contains(channel)) {

                    DiscordHtmlTranscripts transcript = DiscordHtmlTranscripts.getInstance();

                    EmbedBuilder transcriptEmbed = new EmbedBuilder()
                            .setTitle("Transcript - "+channel.getName())
                            .setDescription("Ticket Transcript")
                            .setFooter("DevHub", guild.getIconUrl())
                            .setColor(Color.red);

                    transcripts.sendMessageEmbeds(transcriptEmbed.build()).queue();
                    transcript.createTranscript(channel, transcripts);

                    DatabaseManager databaseManager = new DatabaseManager();

                    //Member ticketOwner = guild.getMembersByName(databaseManager.getTicketOwner(event.getChannel().getIdLong()), true).stream().findFirst().orElse(null);
                    //User ticketOwnerUser = ticketOwner.getUser();

                    //PrivateChannel ownerDMs = ticketOwnerUser.openPrivateChannel().complete();
                    //transcript.createDMTranscript(channel, ownerDMs);



                    EmbedBuilder embedBuilder = new EmbedBuilder()
                            .setTitle("Ticket Deleted")
                            .setDescription("The ticket will close in 5 seconds.")
                            .setFooter("DevHub", guild.getIconUrl())
                            .setColor(Color.GREEN);
                    channel.sendMessageEmbeds(embedBuilder.build()).delay(Duration.ofSeconds(0)).queue();
                    channel.delete().delay(Duration.ofSeconds(10)).queue();

                } else {
                    event.reply("**Error**: Cannot close a non-ticket channel.").queue();
                }
            }
        }
    }
}
