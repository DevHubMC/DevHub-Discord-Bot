package com.fragmc.Tickets;

import com.fragmc.Config;
import com.fragmc.Tickets.Utils.Method;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class TicketsEmbedCreator extends ListenerAdapter {

    @Override
    public void onReady(ReadyEvent event) {
        // Replace "tickets" with the actual name of your channel
        String channelName = "tickets";
        TextChannel ticketsChannel = event.getJDA().getGuildById(Config.getGuildID()).getTextChannelsByName("tickets", true).stream().findFirst().orElse(null);

        // Check if the channel exists
        if (ticketsChannel != null) {
            // Purge (delete) all messages in the channel
            ticketsChannel.purgeMessages(ticketsChannel.getIterableHistory().complete());
        }
        // Get the text channel by name
        event.getJDA().getGuilds().forEach(guild -> {
            guild.getTextChannelsByName(channelName, true).forEach(channel -> {
                Method a = new Method();
                a.sendTicketEmbed(channel);
            });
        });
    }
}
