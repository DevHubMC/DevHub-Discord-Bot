package com.fragmc.MinecraftBridge;

import com.fragmc.Main;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class SyncCommand extends ListenerAdapter implements CommandExecutor {

    private final Main jdaIntegration; // Replace with your actual JDA integration class
    private final String discordGuildId = "1125141974954033185"; // Replace with your Discord guild ID
    private final String minecraftToDiscordRoleIdMapping = "zeus:1161409779311317053"; // Replace with actual role IDs

    public SyncCommand(Main jdaIntegration) {
        this.jdaIntegration = jdaIntegration;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by a player.");
            return true;
        }

        Player player = (Player) sender;
        String minecraftRole = getMinecraftRole(player);

        if (minecraftRole != null && SyncDiscordCommand.isReady()) {
            String discordRoleId = getDiscordRoleId(minecraftRole);
            if (discordRoleId != null) {
                giveDiscordRole(player, discordRoleId);
                sender.sendMessage("You've been given the Discord role associated with your Minecraft rank.");
            } else {
                sender.sendMessage("Discord role not found for your Minecraft rank.");
            }
        } else {
            sender.sendMessage("Error retrieving Minecraft or Discord roles. Make sure you're connected to Discord.");
        }

        return true;
    }

    private String getMinecraftRole(Player player) {
        // Logic to get the player's Minecraft role using LuckPerms API
        // Replace this with your own implementation based on your permission system
        return minecraftToDiscordRoleIdMapping.split(":")[0]; // Replace with actual Minecraft role
    }

    private String getDiscordRoleId(String minecraftRole) {
        // Logic to get the corresponding Discord role ID from the mapping
        // Replace this with your own implementation based on your role mapping
        return minecraftToDiscordRoleIdMapping.split(":")[1];
    }

    private void giveDiscordRole(Player player, String discordRoleId) {
        // Get the JDA member object based on the player's Discord ID
        //Member member = SyncDiscordCommand.getGuildById(discordGuildId).getMemberById(player.getUniqueId().toString());

        // Get the Discord role based on the role ID
        //Role role = jdaListener.getGuildById(discordGuildId).getRoleById(discordRoleId);

        // Give the role to the member
        //if (member != null && role != null) {
        //    jdaListener.getGuildById(discordGuildId).addRoleToMember(member, role).queue();
        //}
    }
}
