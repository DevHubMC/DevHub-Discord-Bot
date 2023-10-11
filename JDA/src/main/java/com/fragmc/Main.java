package com.fragmc;

import com.fragmc.PrivateVCs.JoinVCListener;
import com.fragmc.StaffApps.AppCommandListener;
import com.fragmc.StaffApps.AppCreateClass;
import com.fragmc.Tickets.*;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;

public class Main extends ListenerAdapter {
    private static DatabaseManager databaseManager = null;
    private static long userId;
    private static Message message;
    private static JDA jda;
    private static net.dv8tion.jda.api.interactions.components.buttons.Button button;


    public Main(DatabaseManager databaseManager, long userId, Message message, net.dv8tion.jda.api.interactions.components.buttons.Button button, JDA jda) {
        this.databaseManager = databaseManager;
        this.jda = jda;
    }

    public static void main(String[] args) throws LoginException {
        JDABuilder builder = JDABuilder.createDefault(Config.getBotToken());
//        builder.addEventListeners(new Main(new DatabaseManager(
//                "jdbc:mysql://localhost:3306/ticket_schema",
//                "root",
//                 "CONEmAltgHw1"
//        ), userId, message, button, jda));
        builder.setActivity(Activity.watching("DevHub Tickets"));
        builder.setStatus(OnlineStatus.ONLINE);
        builder.enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES);
        builder.enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT);
        builder.enableIntents(GatewayIntent.GUILD_MEMBERS);
        builder.addEventListeners(new TicketCloseClass(databaseManager));
        builder.addEventListeners(new StaffNicknameChange());
        builder.addEventListeners(new TicketRenameClass());
        builder.addEventListeners(new TicketCommandListener(databaseManager));
        builder.addEventListeners(new JoinVCListener(databaseManager));
        builder.addEventListeners(new AppCommandListener(databaseManager));
        builder.build();
    }
}