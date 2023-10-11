package com.fragmc.Tickets;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class TicketRenameClass extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String[] command = event.getMessage().getContentRaw().split("\\s+");

        if (command.length >= 2 && command[0].equalsIgnoreCase("!rename")) {
            // Check if the channel name contains "ticket-"
            TextChannel channel = event.getChannel().asTextChannel();
            if (channel.getName().startsWith("ticket-")) {
                // Extract the new name from the command
                String newName = event.getMessage().getContentRaw().substring(8);

                // Rename the channel
                channel.getManager().setName(newName).queue(success -> {
                    Guild guild = event.getGuild();
                    EmbedBuilder embedBuilder = new EmbedBuilder()
                            .setTitle("Rename")
                            .setDescription("This ticket has been renamed\n\""+newName+"\"")
                            .setFooter("DevHub", guild.getIconUrl())
                            .setColor(Color.RED);
                    channel.sendMessage("")
                            .setEmbeds(embedBuilder.build())
                            .queue();
                }, error -> {
                    event.getChannel().sendMessage("Error renaming channel: " + error.getMessage()).queue();
                });
            } else {
                event.getChannel().sendMessage("This command is only applicable to ticket channels.").queue();
            }
        }
    }
}
