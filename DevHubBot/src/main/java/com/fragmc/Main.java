package com.fragmc;

import com.fragmc.DatabaseManager.DatabaseManager;
import com.fragmc.Punishments.BanCommand;
import com.fragmc.StaffApps.AppCommandListener;
import com.fragmc.Tickets.TicketCommands.TicketClose.CloseTicketCommand;
import com.fragmc.Tickets.TicketCommands.TicketCreate.CreateTicketCommand;
import com.fragmc.Tickets.TicketCommands.TicketRename.TicketRenameCommand;
import com.fragmc.Tickets.Utils.TicketsEmbedCreator;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

public class Main extends ListenerAdapter {
    private static DatabaseManager databaseManager = null;
    private static JDA jda;


    public Main(DatabaseManager databaseManager, JDA jda) {
        this.databaseManager = databaseManager;
        this.jda = jda;
    }
    public static void main(String[] args) throws LoginException {
            JDABuilder builder = JDABuilder.createDefault(Config.getBotToken());
            builder.setActivity(Activity.watching("PornHub"));
            builder.setStatus(OnlineStatus.DO_NOT_DISTURB);
            builder.enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES);
            builder.enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT);
            builder.enableIntents(GatewayIntent.GUILD_MEMBERS);
            builder.addEventListeners(new Main(new DatabaseManager(), jda));
            CommandManager manager = new CommandManager();
            manager.add(new CreateTicketCommand(databaseManager));
            manager.add(new CloseTicketCommand());
            manager.add(new TicketRenameCommand());

            manager.add(new BanCommand());
            // manager.add(new TicketMoveCommand());
            builder.addEventListeners(manager);
            builder.addEventListeners(new TicketsEmbedCreator(databaseManager));
            builder.addEventListeners(new AppCommandListener(databaseManager));
            builder.build();
    }
}