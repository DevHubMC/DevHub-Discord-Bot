package com.fragmc.Tickets.Utils;

import com.fragmc.DatabaseManager.DatabaseManager;
import com.fragmc.Tickets.TicketCommands.TicketCreate.GeneralButtonListener;
import com.fragmc.Tickets.TicketCommands.TicketCreate.PrisonButtonListener;
import com.fragmc.Tickets.TicketCommands.TicketCreate.SkyblockButtonListener;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;
import java.sql.SQLException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class Method {
    private DatabaseManager databaseManager;

    public Method(DatabaseManager databaseManager){
        this.databaseManager = databaseManager;
    }

    public void CreatePrisonsTicket(Guild guild, Member member, User user){
        // NOTE: GETS THE CURRENT TICKET NUMBER AND ASSIGNS IT INTO THE NAME OF THE CHANNEL
        int currentTicketNumber;
        try {
            currentTicketNumber = databaseManager.getCurrentTicketNumber();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Insert ticket information into the database
        String channelName = "ticket-" + (currentTicketNumber+1);

        // NOTE: GETS EVERY CATEGORY AND ASSIGNS CATEGORY [CATEGORY = PRISONS TICKET]
        Category category = null;
        for (Category categories : guild.getCategories()) {
            if (categories.getName().equalsIgnoreCase("prisons tickets")) {
                category = categories;
                break;
            }
        }

        if (category != null){

            // NOTE: CREATES THE TICKET CHANNEL IN CATEGORY [PRISONS TICKETS]
            int finalCurrentTicketNumber = currentTicketNumber;
            category.createTextChannel(channelName).queue(ticket -> {

                try {
                    databaseManager.insertTicket(user.getName(), finalCurrentTicketNumber + 1, ticket.getIdLong());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                Getters get = new Getters(member, user, guild, ticket);

                // NOTE: SETS THE PERMISSIONS OF THE TICKET CHANNEL SO ONLY @STAFF AND THE TICKET OWNER CAN SEE IT.
                ticket.upsertPermissionOverride(get.getMember())
                        .grant(Permission.VIEW_CHANNEL)
                        .grant(Permission.MESSAGE_SEND)
                        .queue();
                ticket.upsertPermissionOverride(Objects.requireNonNull(get.getGuild().getRolesByName("Prisons Support", true).stream().findFirst().orElse(null)))
                        .grant(Permission.VIEW_CHANNEL)
                        .grant(Permission.MESSAGE_SEND)
                        .queue();
                ticket.upsertPermissionOverride(get.getGuild().getPublicRole())
                        .deny(Permission.VIEW_CHANNEL)
                        .queue();

                // NOTE: SENDS THE EMBED TO THE TICKET CHANNEL [LINE 85]
                ticket.sendMessage(get.getTicketOwner().getAsMention())
                        .setEmbeds(TicketEmbed(get.getGuild()))
                        .queue();

            });
        }else{

            // NOTE: IF THE CATEGORY [PRISONS TICKETS] DOESN'T EXIST, IT SENDS AN ERROR LOG TO CONSOLE
            System.out.println("ERROR >> There is no such category as "+category);
            return;
        }
    }

    public void CreateSkyblockTicket(Guild guild, Member member, User user){
        // NOTE: GETS THE CURRENT TICKET NUMBER AND ASSIGNS IT INTO THE NAME OF THE CHANNEL
        int currentTicketNumber;
        try {
            currentTicketNumber = databaseManager.getCurrentTicketNumber();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Insert ticket information into the database
        String channelName = "ticket-" + (currentTicketNumber +1);

        // NOTE: GETS EVERY CATEGORY AND ASSIGNS CATEGORY [CATEGORY = PRISONS TICKET]
        Category category = null;
        for (Category categories : guild.getCategories()) {
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
                    databaseManager.insertTicket(user.getName(), finalCurrentTicketNumber + 1, ticket.getIdLong());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                Getters get = new Getters(member, user, guild, ticket);

                // NOTE: SETS THE PERMISSIONS OF THE TICKET CHANNEL SO ONLY @STAFF AND THE TICKET OWNER CAN SEE IT.
                ticket.upsertPermissionOverride(get.getMember())
                        .grant(Permission.VIEW_CHANNEL)
                        .grant(Permission.MESSAGE_SEND)
                        .queue();
                ticket.upsertPermissionOverride(Objects.requireNonNull(get.getGuild().getRolesByName("Skyblock Support", true).stream().findFirst().orElse(null)))
                        .grant(Permission.VIEW_CHANNEL)
                        .grant(Permission.MESSAGE_SEND)
                        .queue();
                ticket.upsertPermissionOverride(get.getGuild().getPublicRole())
                        .deny(Permission.VIEW_CHANNEL)
                        .queue();

                // NOTE: SENDS THE EMBED TO THE TICKET CHANNEL [LINE 85]
                ticket.sendMessage(get.getTicketOwner().getAsMention())
                        .setEmbeds(TicketEmbed(get.getGuild()))
                        .queue();

            });
        }else{

            // NOTE: IF THE CATEGORY [PRISONS TICKETS] DOESN'T EXIST, IT SENDS AN ERROR LOG TO CONSOLE
            System.out.println("ERROR >> There is no such category as "+category);
            return;
        }
    }

    public void CreateGeneralTicket(Guild guild, Member member, User user){
        int currentTicketNumber;
        try {
            currentTicketNumber = databaseManager.getCurrentTicketNumber();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Insert ticket information into the database
        String channelName = "ticket-" + (currentTicketNumber+1);

        // NOTE: GETS EVERY CATEGORY AND ASSIGNS CATEGORY [CATEGORY = PRISONS TICKET]
        Category category = null;
        for (Category categories : guild.getCategories()) {
            if (categories.getName().equalsIgnoreCase("general tickets")) {
                category = categories;
                break;
            }
        }

        if (category != null){

            // NOTE: CREATES THE TICKET CHANNEL IN CATEGORY [PRISONS TICKETS]
            int finalCurrentTicketNumber = currentTicketNumber;
            category.createTextChannel(channelName).queue(ticket -> {

                try {
                    databaseManager.insertTicket(user.getName(), finalCurrentTicketNumber + 1, ticket.getIdLong());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                Getters get = new Getters(member, user, guild, ticket);

                // NOTE: SETS THE PERMISSIONS OF THE TICKET CHANNEL SO ONLY @STAFF AND THE TICKET OWNER CAN SEE IT.
                ticket.upsertPermissionOverride(get.getMember())
                        .grant(Permission.VIEW_CHANNEL)
                        .grant(Permission.MESSAGE_SEND)
                        .queue();
                ticket.upsertPermissionOverride(Objects.requireNonNull(get.getGuild().getRolesByName("General Support", true).stream().findFirst().orElse(null)))
                        .grant(Permission.VIEW_CHANNEL)
                        .grant(Permission.MESSAGE_SEND)
                        .queue();
                ticket.upsertPermissionOverride(get.getGuild().getPublicRole())
                        .deny(Permission.VIEW_CHANNEL)
                        .queue();

                // NOTE: SENDS THE EMBED TO THE TICKET CHANNEL [LINE 85]
                ticket.sendMessage(get.getTicketOwner().getAsMention())
                        .setEmbeds(GeneralTicketEmbed(get.getGuild()))
                        .queue();

            });
        }else{

            // NOTE: IF THE CATEGORY [PRISONS TICKETS] DOESN'T EXIST, IT SENDS AN ERROR LOG TO CONSOLE
            System.out.println("ERROR >> There is no such category as "+category);
            return;
        }
    }


    public MessageEmbed TicketEmbed(Guild guild){
        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setTitle("Support Ticket")
                .setDescription("Please answer the questions below in as much detail as possible.")
                .addField("Question/Issue", "➣ What realm are you making a ticket for?\n➣ What is your IGN?\n➣ What do you need help with?", true)
                .setFooter("DevHub", guild.getIconUrl())
                .setColor(Color.GREEN);
        return embedBuilder.build();
    }
    public MessageEmbed GeneralTicketEmbed(Guild guild){
        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setTitle("Support Ticket")
                .setDescription("Please answer the question below in as much detail as possible.")
                .addField("Question/Issue", "➣ What do you need help with?", true)
                .setFooter("DevHub", guild.getIconUrl())
                .setColor(Color.GREEN);
        return embedBuilder.build();
    }

    public void sendTicketEmbed(TextChannel channel) {
        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setTitle("Ticket System")
                .setDescription("Click the button below to create a ticket.")
                .setColor(Color.PINK);

        Button createPrisonButton = Button.primary("tickets:prisons", "Prisons Ticket");
        Button createSkyblockTicket = Button.primary("tickets:skyblock", "Skyblock Ticket");
        Button createGeneralTicket = Button.primary("tickets:general", "General Ticket");

        MessageEmbed embed = embedBuilder.build();

        CompletableFuture<Message> future = channel.sendMessageEmbeds(embed)
                .addActionRow(createPrisonButton, createSkyblockTicket, createGeneralTicket)
                .submit();

        future.thenAccept(message -> {
            // Add a button click listener for the created message
            channel.getJDA().addEventListener(new PrisonButtonListener(databaseManager));
            channel.getJDA().addEventListener(new SkyblockButtonListener(databaseManager));
            channel.getJDA().addEventListener(new GeneralButtonListener(databaseManager));
        });
    }
}
