package com.fragmc.Tickets;

import com.fragmc.DatabaseManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.AuditableRestAction;

import java.awt.*;
import java.sql.SQLException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class TicketCloseClass extends ListenerAdapter implements EventListener {

    private final DatabaseManager databaseManager;

    public TicketCloseClass(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        try {
            boolean commandMessage = event.getMessage().getContentRaw().equalsIgnoreCase("!c");
            if (commandMessage) {
                Member member = event.getMember();
                if (member != null) {
                    String username = member.getUser().getName();
                    String userId = member.getId();
                    // ENTER TICKET DELETING CODE
                    Guild guild = event.getGuild();
                    TextChannel channel = event.getMessage().getChannel().asTextChannel();
                    if (channel.getName().contains("ticket")) {
                        if (member.getRoles().stream().anyMatch(role -> role.getName().equalsIgnoreCase("Owner"))) {


                            EmbedBuilder embedBuilder = new EmbedBuilder()
                                    .setTitle("Ticket Deleted")
                                    .setDescription("The ticket will close in 5 seconds.")
                                    .setFooter("DevHub", guild.getIconUrl())
                                    .setColor(Color.GREEN);
                            channel.sendMessageEmbeds(embedBuilder.build()).queue(message -> {
                                // Use delay to wait 5 seconds before deleting the channel
                                channel.delete().delay(100, TimeUnit.SECONDS).queue(
                                        success -> System.out.println("Channel **\"" + event.getChannel().getName() + "\"** deleted successfully."),
                                        error -> System.out.println("**Error**: " + error.getMessage())
                                );
                                // Additional ticket closing code (e.g., databaseManager.closeTicket(userId)) can be placed here if needed.
                            });
                        } else {
                            // Reply that the member doesn't have the required role
                            event.getChannel().sendMessage("**Error**: No Permission.").queue();
                        }
                    } else {
                        // Reply that this command is not applicable to the current channel
                        event.getChannel().sendMessage("**Error**: Incorrect channel format.").queue();
                    }
                }
            }
            ButtonClickListener a = new ButtonClickListener();
            if (event.getMessage().getContentRaw().equals("!getts")){
                a.generateTranscript();
            }
            event.getChannel().getName();
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("Error: NullPointerException");
        }
    }
}
