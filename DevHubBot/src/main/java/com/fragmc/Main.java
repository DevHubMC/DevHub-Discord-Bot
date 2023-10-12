package com.fragmc;

import com.fragmc.Tickets.TicketCreate.CreateTicketCommand;
import com.fragmc.Tickets.TicketsEmbedCreator;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

public class Main {
        public static void main(String[] args) throws LoginException {
            JDABuilder builder = JDABuilder.createDefault(Config.getBotToken());
            builder.setActivity(Activity.watching("PornHub"));
            builder.setStatus(OnlineStatus.DO_NOT_DISTURB);
            builder.enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES);
            builder.enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT);
            builder.enableIntents(GatewayIntent.GUILD_MEMBERS);
            CommandManager manager = new CommandManager();
            manager.add(new CreateTicketCommand());
            builder.addEventListeners(manager);
            builder.addEventListeners(new TicketsEmbedCreator());
            builder.build();
    }
}