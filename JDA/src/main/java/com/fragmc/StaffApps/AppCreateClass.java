package com.fragmc.StaffApps;

import com.fragmc.DatabaseManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.data.DataObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

public class AppCreateClass extends ListenerAdapter {

    private DatabaseManager databaseManager;
    private net.dv8tion.jda.api.interactions.components.buttons.Button button;
    private String answer;

    public AppCreateClass(net.dv8tion.jda.api.interactions.components.buttons.Button button,DatabaseManager databaseManager, JDA jda) {
        this.button = button;
        this.databaseManager = databaseManager;
    }

    public enum Question {
        IGN, REALM, AGE, TIME_REQ, PRIOR_APP, PUNISHMENTS, WHY_STAFF, EXPERIENCE, HELPER_DUTY, CLIP
    }

    private Question currentQuestion;
    private Array[] answers = new Array[9];


    @Override
    public void onButtonInteraction(ButtonInteractionEvent e) {
        if (e.getComponentId().equalsIgnoreCase("staffApps")){
            Member member = e.getMember();
            if (member == null){
                return;
            }
            String username = member.getUser().getName();
            Category applicationCategory = null;
            for (Category category : e.getGuild().getCategories()) {
                if (category.getName().equalsIgnoreCase("Staff Applications")) {
                    applicationCategory = category;
                    break;
                }
            }
            if (applicationCategory != null){
                String ChannelName = username+"'s-staff-application";
                Guild guild = e.getGuild();

                applicationCategory.createTextChannel(ChannelName).queue(channel -> {
                    // Set the initial question when the channel is created
                    currentQuestion = Question.IGN;

                    channel.upsertPermissionOverride(member)
                            .grant(Permission.VIEW_CHANNEL)
                            .grant(Permission.MESSAGE_SEND)
                            .deny(Permission.MESSAGE_ADD_REACTION)
                            .queue();
                    Role staffRole = guild.getRolesByName("Helper", true).stream().findFirst().orElse(null);
                    Role staffManager = guild.getRolesByName("Helper", true).stream().findFirst().orElse(null);
                    channel.upsertPermissionOverride(staffRole)
                            .grant(Permission.VIEW_CHANNEL)
                            .grant(Permission.MESSAGE_SEND)
                            .grant(Permission.MESSAGE_ADD_REACTION)
                            .grant(Permission.MESSAGE_MANAGE)
                            .queue();
                    channel.upsertPermissionOverride(staffManager)
                            .grant(Permission.ADMINISTRATOR)
                            .queue();

                    MainEmbed(channel, member, staffRole);
                });
            }
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if (e.getAuthor().isBot()) return;

        TextChannel channel = e.getChannel().asTextChannel();
        String channelName = channel.getName();

        if (channelName.contains("'s-staff-application")) {
            // User is answering a question
            Array answerA = e.getMessage().getContentRaw();

            Arrays.stream(answers).limit(10);

            if (answers.length == 0) {
                IGNQuestion(channel);
                answers[currentQuestion.ordinal()] = answerA;

            }if(answers.length == 1) {
                RealmQuestion(channel);
            }if(answers.length == 2) {
                AgeQuestion(channel);
            }if(answers.length == 3) {
                TimeReqQuestion(channel);
            }if(answers.length == 4) {
                PriorAppQuestion(channel);
            }if(answers.length == 5) {
               PunishmentsQuestion(channel);
            }if(answers.length == 6) {
                WhyStaffQuestion(channel);
            }if(answers.length == 7) {
                ExperienceQuestion(channel);
            }if(answers.length == 8) {
                HelperDutyQuestion(channel);
            }if(answers.length == 9) {
                ClipQuestion(channel);
            }
        }
    }


    public EmbedBuilder MainEmbed(TextChannel channel, Member member, Role staffRole){
        EmbedBuilder FirstEmbed = new EmbedBuilder()
                .setTitle("Staff Application")
                .setDescription("Please answer all the questions asked below")
                .setAuthor("DevHub")
                .setColor(Color.RED);
        channel.sendMessage(member.getAsMention() + " " + staffRole.getAsMention())
                .setEmbeds(FirstEmbed.build())
                .queue();
        return FirstEmbed;
    }
    public EmbedBuilder IGNQuestion(TextChannel channel){
        EmbedBuilder questionOne = new EmbedBuilder()
                .setTitle("**Question One**")
                .setDescription("➣ What is your In-Game Name?")
                .setAuthor("DevHub")
                .setColor(Color.RED);
        channel.sendMessageEmbeds(questionOne.build()).queue();
        return questionOne;
    }
    public EmbedBuilder RealmQuestion(TextChannel channel){
        EmbedBuilder questionOne = new EmbedBuilder()
                .setTitle("**Question Two**")
                .setDescription("➣ What realm are you applying for?")
                .setAuthor("DevHub")
                .setColor(Color.RED);
        channel.sendMessageEmbeds(questionOne.build()).queue();
        return questionOne;
    }

    public EmbedBuilder AgeQuestion(TextChannel channel){
        EmbedBuilder questionOne = new EmbedBuilder()
                .setTitle("**Question Three**")
                .setDescription("➣ How old are you?")
                .setAuthor("DevHub")
                .setColor(Color.RED);
        channel.sendMessageEmbeds(questionOne.build()).queue();
        return questionOne;
    }
    public EmbedBuilder TimeReqQuestion(TextChannel channel){
        EmbedBuilder questionOne = new EmbedBuilder()
                .setTitle("**Question Four**")
                .setDescription("➣ How long per week could you dedicate to the server?")
                .setAuthor("DevHub")
                .setColor(Color.RED);
        channel.sendMessageEmbeds(questionOne.build()).queue();
        return questionOne;
    }
    public EmbedBuilder PriorAppQuestion(TextChannel channel){
        EmbedBuilder questionOne = new EmbedBuilder()
                .setTitle("**Question Five**")
                .setDescription("➣ Have you ever made a staff application before? If so, when?")
                .setAuthor("DevHub")
                .setColor(Color.RED);
        channel.sendMessageEmbeds(questionOne.build()).queue();
        return questionOne;
    }
    public EmbedBuilder PunishmentsQuestion(TextChannel channel){
        EmbedBuilder questionOne = new EmbedBuilder()
                .setTitle("**Question Six**")
                .setDescription("➣ Have you ever been punished on one of our servers? If so, please expand.")
                .setAuthor("DevHub")
                .setColor(Color.RED);
        channel.sendMessageEmbeds(questionOne.build()).queue();
        return questionOne;
    }
    public EmbedBuilder WhyStaffQuestion(TextChannel channel){
        EmbedBuilder questionOne = new EmbedBuilder()
                .setTitle("**Question Seven**")
                .setDescription("➣ Have you ever been punished on one of our servers? If so, please expand.")
                .setAuthor("DevHub")
                .setColor(Color.RED);
        channel.sendMessageEmbeds(questionOne.build()).queue();
        return questionOne;
    }
    public EmbedBuilder ExperienceQuestion(TextChannel channel){
        EmbedBuilder questionOne = new EmbedBuilder()
                .setTitle("**Question Eight**")
                .setDescription("➣ What experience do you have that could help you in this role?")
                .setAuthor("DevHub")
                .setColor(Color.RED);
        channel.sendMessageEmbeds(questionOne.build()).queue();
        return questionOne;
    }
    public EmbedBuilder HelperDutyQuestion(TextChannel channel){
        EmbedBuilder questionOne = new EmbedBuilder()
                .setTitle("**Question Nine**")
                .setDescription("➣ Please explain what your duties as helper would be.")
                .setAuthor("DevHub")
                .setColor(Color.RED);
        channel.sendMessageEmbeds(questionOne.build()).queue();
        return questionOne;
    }
    public EmbedBuilder ClipQuestion(TextChannel channel){
        EmbedBuilder questionOne = new EmbedBuilder()
                .setTitle("**Question Ten**")
                .setDescription("➣ Are you able to clip or record your game of any kind?")
                .setAuthor("DevHub")
                .setColor(Color.RED);
        channel.sendMessageEmbeds(questionOne.build()).queue();
        return questionOne;
    }
    private EmbedBuilder getQuestionByType(Question questionType, TextChannel channel) {
        switch (questionType) {
            case IGN:
                return IGNQuestion(channel);
            case REALM:
                return RealmQuestion(channel);
            case AGE:
                return AgeQuestion(channel);
            case TIME_REQ:
                return TimeReqQuestion(channel);
            case PRIOR_APP:
                return PriorAppQuestion(channel);
            case PUNISHMENTS:
                return PunishmentsQuestion(channel);
            case WHY_STAFF:
                return WhyStaffQuestion(channel);
            case EXPERIENCE:
                return ExperienceQuestion(channel);
            case HELPER_DUTY:
                return HelperDutyQuestion(channel);
            case CLIP:
                return ClipQuestion(channel);

            default:
                return null;
        }
    }
}
