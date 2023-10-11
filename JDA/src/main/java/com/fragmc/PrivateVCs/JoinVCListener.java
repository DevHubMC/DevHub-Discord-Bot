package com.fragmc.PrivateVCs;

import com.fragmc.DatabaseManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.xml.crypto.Data;

public class JoinVCListener extends ListenerAdapter {
    private DatabaseManager databaseManager;
    public JoinVCListener(DatabaseManager databaseManager){
        this.databaseManager = databaseManager;
    }
    @Override
    public void onGuildVoiceUpdate(GuildVoiceUpdateEvent event) {

        Category ticketsCategory;
        Member member = event.getMember();
        if (member == null) {
            return;
        }

        /**
         * ticketsCategory = event.getGuild().getCategories().stream().filter(category -> category.getName().equalsIgnoreCase("Voice Channels")).findFirst().orElse(null);
         *         if (event.getChannelJoined().equals("Private Voice Channels")) {
         *             if (ticketsCategory != null) {
         *                 // Create a new text channel within the tickets category
         *                 String channelName = event.getMember().getUser().getName() + "'s Private VC";
         *                 String channelWRName = event.getMember().getUser().getName() + "'s Waiting Room";
         *                 Guild guild = event.getGuild();
         *                 ticketsCategory.createVoiceChannel(channelName).queue(channel -> {
         *                     channel.upsertPermissionOverride(member)
         *                             .grant(Permission.VOICE_CONNECT)
         *                             .grant(Permission.VOICE_SPEAK)
         *                             .grant(Permission.VOICE_DEAF_OTHERS)
         *                             .grant(Permission.VOICE_MUTE_OTHERS)
         *                             .grant(Permission.VOICE_MOVE_OTHERS)
         *                             .grant(Permission.MESSAGE_SEND)
         *                             .queue();
         *                     channel.upsertPermissionOverride(guild.getPublicRole())
         *                             .deny(Permission.VOICE_CONNECT)
         *                             .grant(Permission.VIEW_CHANNEL)
         *                             .queue();
         *
         *
         *                     event.getGuild().moveVoiceMember(event.getMember(), channel);
         *                 // Add your custom action here
         *                 // For example, you can send a message to the text channel, play music, etc.
         *                     if (channel.getMembers().isEmpty()){
         *                         channel.delete().queue();
         *                     }
         *                     ticketsCategory.createVoiceChannel(channelWRName).queue(channelWR -> {
         *                         channelWR.upsertPermissionOverride(member)
         *                                 .grant(Permission.VOICE_CONNECT)
         *                                 .grant(Permission.VOICE_SPEAK)
         *                                 .grant(Permission.VOICE_DEAF_OTHERS)
         *                                 .grant(Permission.VOICE_MUTE_OTHERS)
         *                                 .grant(Permission.VOICE_MOVE_OTHERS)
         *                                 .grant(Permission.MESSAGE_SEND)
         *                                 .queue();
         *                         channelWR.upsertPermissionOverride(guild.getPublicRole())
         *                                 .grant(Permission.VOICE_CONNECT)
         *                                 .grant(Permission.VIEW_CHANNEL)
         *                                 .queue();
         *                         // Add your custom action here
         *                         // For example, you can send a message to the text channel, play music, etc.
         *                         if (channelWR.getMembers().isEmpty()){
         *                             channelWR.delete().queue();
         *                         }
         *                     });
         *                 });
         *             }
         */



    }
}
