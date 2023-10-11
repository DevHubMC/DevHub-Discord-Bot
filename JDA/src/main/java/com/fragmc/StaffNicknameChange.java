package com.fragmc;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleAddEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class StaffNicknameChange extends ListenerAdapter implements EventListener {

    @Override
    public void onGuildMemberRoleAdd(GuildMemberRoleAddEvent e){
        Role HelperRole = e.getGuild().getRolesByName("Helper", true).stream().findFirst().orElse(null);
        Role ModRole = e.getGuild().getRolesByName("Mod", true).stream().findFirst().orElse(null);
        Role SrModRole = e.getGuild().getRolesByName("SrMod", true).stream().findFirst().orElse(null);
        Role AdminRole = e.getGuild().getRolesByName("Admin", true).stream().findFirst().orElse(null);
        Role StaffManagerRole = e.getGuild().getRolesByName("Staff Manager", true).stream().findFirst().orElse(null);
        Role ManagerRole = e.getGuild().getRolesByName("Manager", true).stream().findFirst().orElse(null);
        Role HeadManagerRole = e.getGuild().getRolesByName("Head Manager", true).stream().findFirst().orElse(null);
        Role OwnerRole = e.getGuild().getRolesByName("Owner", true).stream().findFirst().orElse(null);
        if (HelperRole != null && e.getRoles().contains(HelperRole)){
            Member member = e.getMember();
            if (member != null) {
                String Nick = "Helper | "+member.getUser().getName();
                if (Nick.length() <= 32){
                    try {
                        // Update the nickname
                        member.modifyNickname(Nick).queue();
                    } catch (Exception ex) {
                        // Log any exceptions
                        ex.printStackTrace();
                    }
                }else{
                    Guild guild = e.getGuild();
                    System.out.println("**Error**: This nickname is too long. Nickname: "+Nick);
                }
            }
        }
        if (ModRole != null && e.getRoles().contains(ModRole)){
            Member member = e.getMember();
            if (member != null) {
                String Nick = "Mod | "+member.getUser().getName();
                if (Nick.length() <= 32){
                    member.modifyNickname(Nick);
                }else{
                    Guild guild = e.getGuild();
                    System.out.println("**Error**: This nickname is too long. Nickname: "+Nick);
                }
            }
        }
        if (SrModRole != null && e.getRoles().contains(SrModRole)){
            Member member = e.getMember();
            if (member != null) {
                String Nick = "SrMod | "+member.getUser().getName();
                if (Nick.length() <= 32){
                    member.modifyNickname(Nick);
                }else{
                    Guild guild = e.getGuild();
                    System.out.println("**Error**: This nickname is too long. Nickname: "+Nick);
                }
            }
        }
        if (AdminRole != null && e.getRoles().contains(AdminRole)){
            Member member = e.getMember();
            if (member != null) {
                String Nick = "Admin | "+member.getUser().getName();
                if (Nick.length() <= 32){
                    member.modifyNickname(Nick);
                }else{
                    Guild guild = e.getGuild();
                    System.out.println("**Error**: This nickname is too long. Nickname: "+Nick);
                }
            }
        }
        if (StaffManagerRole != null && e.getRoles().contains(StaffManagerRole)){
            Member member = e.getMember();
            if (member != null) {
                String Nick = "SM | "+member.getUser().getName();
                if (Nick.length() <= 32){
                    member.modifyNickname(Nick);
                }else{
                    Guild guild = e.getGuild();
                    System.out.println("**Error**: This nickname is too long. Nickname: "+Nick);
                }
            }
        }
        if (ManagerRole != null && e.getRoles().contains(ManagerRole)){
            Member member = e.getMember();
            if (member != null) {
                String Nick = "Manager | "+member.getUser().getName();
                if (Nick.length() <= 32){
                    member.modifyNickname(Nick);
                }else{
                    Guild guild = e.getGuild();
                    System.out.println("**Error**: This nickname is too long. Nickname: "+Nick);
                }
            }
        }
        if (HeadManagerRole != null && e.getRoles().contains(HeadManagerRole)){
            Member member = e.getMember();
            if (member != null) {
                String Nick = "HM | "+member.getUser().getName();
                if (Nick.length() <= 32){
                    member.modifyNickname(Nick);
                }else{
                    Guild guild = e.getGuild();
                    System.out.println("**Error**: This nickname is too long. Nickname: "+Nick);
                }
            }
        }
        if (OwnerRole != null && e.getRoles().contains(OwnerRole)){
            Member member = e.getMember();
            if (member != null) {
                String Nick = "Owner | "+member.getUser().getName();
                if (Nick.length() <= 32){
                    member.modifyNickname(Nick);
                }else{
                    Guild guild = e.getGuild();
                    System.out.println("**Error**: This nickname is too long. Nickname: "+Nick);
                }
            }
        }

    }
    @Override
    public void onGuildMemberRoleRemove(GuildMemberRoleRemoveEvent e){
        e.getMember().modifyNickname(e.getMember().getUser().getName());
    }
}
