package com.fragmc.Tickets.Utils;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Getters {
    Member member;
    User ticketOwner;
    Guild guild;
    TextChannel ticket;
    public Getters(Member member, User ticketOwner, Guild guild, TextChannel ticket){
        this.member = member;
        this.ticketOwner = ticketOwner;
        this.guild = guild;
        this.ticket = ticket;
    }

    public User getTicketOwner(){
        return ticketOwner;
    }
    public Member getMember(){
        return member;
    }
    public Guild getGuild(){
        return guild;
    }
    public TextChannel getTicket(){
        return ticket;
    }
}
