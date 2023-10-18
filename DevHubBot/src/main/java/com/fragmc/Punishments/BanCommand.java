package com.fragmc.Punishments;

import com.fragmc.Tickets.Utils.ICommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BanCommand implements ICommand {
    @Override
    public String getName() {
        return "ban";
    }

    @Override
    public String getDescription() {
        return "A command to ban a player from the guild";
    }

    @Override
    public List<OptionData> getOptions() {
        List<OptionData> data = new ArrayList<>();
        data.add(new OptionData(OptionType.STRING, "player", "the player that you want to ban", true));
        data.add(new OptionData(OptionType.INTEGER, "duration", "length of the ban", true));
        data.add(new OptionData(OptionType.STRING, "unit", "the unit of the duration", true));
        return data;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) throws IOException {
        OptionMapping playerOption = event.getOption("player");
        OptionMapping unitOption = event.getOption("unit");
        OptionMapping durationOption = event.getOption("duration");
        String playerName = playerOption.getAsString();
        String unit = unitOption.getAsString();
        int duration = durationOption.getAsInt();

        try{
            TimeUnit banLength = null;
            if (unit.equalsIgnoreCase("hour") || unit.equalsIgnoreCase("hr") || unit.equalsIgnoreCase("h") || unit.equalsIgnoreCase("hours") || unit.equalsIgnoreCase("hrs") || unit.equalsIgnoreCase("hs")){
                banLength = TimeUnit.HOURS;
            } else if (unit.equalsIgnoreCase("day") || unit.equalsIgnoreCase("days") || unit.equalsIgnoreCase("d") || unit.equalsIgnoreCase("ds")) {
                banLength = TimeUnit.DAYS;
            } else if (unit.equalsIgnoreCase("s") || unit.equalsIgnoreCase("seconds") || unit.equalsIgnoreCase("sec") || unit.equalsIgnoreCase("secs")) {
                banLength = TimeUnit.SECONDS;
            } else if (unit.equalsIgnoreCase("min") || unit.equalsIgnoreCase("mins") || unit.equalsIgnoreCase("minute") || unit.equalsIgnoreCase("minutes")|| unit.equalsIgnoreCase("m") || unit.equalsIgnoreCase("ms")) {
                banLength = TimeUnit.MINUTES;
            } else if (banLength == null){
                event.reply("Please use a valid time unit.");
            }else{
                event.reply("Please use a valid time unit");
            }

            Guild guild = event.getGuild();
            Member member = guild.getMembersByName(playerName, false).stream().findFirst().orElse(null);

            member.ban(duration, banLength);

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

