package com.fragmc.StaffApps;

import com.fragmc.DatabaseManager.DatabaseManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AppCreateClass extends ListenerAdapter {

    private DatabaseManager databaseManager;
    private String answer;

    public AppCreateClass(DatabaseManager databaseManager, JDA jda) {
        this.databaseManager = databaseManager;
    }

    public enum Question {
        IGN, REALM, AGE, TIME_REQ, PRIOR_APP, PUNISHMENTS, WHY_STAFF, EXPERIENCE, HELPER_DUTY, CLIP
    }

    private Question currentQuestion;
    private final List<String> answers = new ArrayList<>();


    @Override
    public void onButtonInteraction(ButtonInteractionEvent e) {
        if (e.getComponentId().equalsIgnoreCase("staffApps")) {
            Member member = e.getMember();
            if (member == null) {
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
            if (applicationCategory != null) {
                String ChannelName = username + "'s-staff-application";
                Guild guild = e.getGuild();

                applicationCategory.createTextChannel(ChannelName).queue(channel -> {
                    // Set the initial question when the channel is created
                    currentQuestion = Question.IGN;

                    channel.upsertPermissionOverride(member)
                            .grant(Permission.VIEW_CHANNEL)
                            .grant(Permission.MESSAGE_SEND)
                            .deny(Permission.MESSAGE_ADD_REACTION)
                            .queue();
                    Role staffRole = guild.getRolesByName("General Support", true).stream().findFirst().orElse(null);
                    Role staffManager = guild.getRolesByName("Admin", true).stream().findFirst().orElse(null);
                    channel.upsertPermissionOverride(staffRole)
                            .grant(Permission.VIEW_CHANNEL)
                            .grant(Permission.MESSAGE_SEND)
                            .grant(Permission.MESSAGE_ADD_REACTION)
                            .grant(Permission.MESSAGE_MANAGE)
                            .queue();
                    channel.upsertPermissionOverride(staffManager)
                            .grant(Permission.ADMINISTRATOR)
                            .queue();
                    channel.upsertPermissionOverride(guild.getPublicRole())
                            .deny(Permission.VIEW_CHANNEL)
                            .queue();

                    MainEmbed(channel, member, staffRole);
                    IGNQuestion(channel);
                });
            }
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if (e.getAuthor().isBot()) return;

        TextChannel channel = e.getChannel().asTextChannel();
        String channelName = channel.getName();

        if (channelName.contains("-staff-application")) {
            // User is answering a question
            if (answers.isEmpty()) {

                RealmQuestion(channel);
            } else if (answers.size() == 1) {

                AgeQuestion(channel);
            } else if (answers.size() == 2) {

                TimeReqQuestion(channel);
            } else if (answers.size() == 3) {

                PriorAppQuestion(channel);
            } else if (answers.size() == 4) {

                PunishmentsQuestion(channel);
            } else if (answers.size() == 5) {

                WhyStaffQuestion(channel);
            } else if (answers.size() == 6) {

                ExperienceQuestion(channel);
            } else if (answers.size() == 7) {

                HelperDutyQuestion(channel);
            } else if (answers.size() == 8) {

                ClipQuestion(channel);
            } else if (answers.size() == 9) {

            }
            //answers.clear();
        }
    }


    public EmbedBuilder MainEmbed(TextChannel channel, Member member, Role staffRole) {
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

    public EmbedBuilder IGNQuestion(TextChannel channel) {
        EmbedBuilder questionOne = new EmbedBuilder()
                .setTitle("**Question One**")
                .setDescription("➣ What is your In-Game Name?")
                .setAuthor("DevHub")
                .setColor(Color.RED);
        channel.sendMessageEmbeds(questionOne.build()).queue();
        return questionOne;
    }

    public EmbedBuilder RealmQuestion(TextChannel channel) {
        EmbedBuilder questionOne = new EmbedBuilder()
                .setTitle("**Question Two**")
                .setDescription("➣ What realm are you applying for?")
                .setAuthor("DevHub")
                .setColor(Color.RED);
        channel.sendMessageEmbeds(questionOne.build()).queue();
        return questionOne;
    }

    public EmbedBuilder AgeQuestion(TextChannel channel) {
        EmbedBuilder questionOne = new EmbedBuilder()
                .setTitle("**Question Three**")
                .setDescription("➣ How old are you?")
                .setAuthor("DevHub")
                .setColor(Color.RED);
        channel.sendMessageEmbeds(questionOne.build()).queue();
        return questionOne;
    }

    public EmbedBuilder TimeReqQuestion(TextChannel channel) {
        EmbedBuilder questionOne = new EmbedBuilder()
                .setTitle("**Question Four**")
                .setDescription("➣ How long per week could you dedicate to the server?")
                .setAuthor("DevHub")
                .setColor(Color.RED);
        channel.sendMessageEmbeds(questionOne.build()).queue();
        return questionOne;
    }

    public EmbedBuilder PriorAppQuestion(TextChannel channel) {
        EmbedBuilder questionOne = new EmbedBuilder()
                .setTitle("**Question Five**")
                .setDescription("➣ Have you ever made a staff application before? If so, when?")
                .setAuthor("DevHub")
                .setColor(Color.RED);
        channel.sendMessageEmbeds(questionOne.build()).queue();
        return questionOne;
    }

    public EmbedBuilder PunishmentsQuestion(TextChannel channel) {
        EmbedBuilder questionOne = new EmbedBuilder()
                .setTitle("**Question Six**")
                .setDescription("➣ Have you ever been punished on one of our servers? If so, please expand.")
                .setAuthor("DevHub")
                .setColor(Color.RED);
        channel.sendMessageEmbeds(questionOne.build()).queue();
        return questionOne;
    }

    public EmbedBuilder WhyStaffQuestion(TextChannel channel) {
        EmbedBuilder questionOne = new EmbedBuilder()
                .setTitle("**Question Seven**")
                .setDescription("➣ Why would you like to become staff on your server?.")
                .setAuthor("DevHub")
                .setColor(Color.RED);
        channel.sendMessageEmbeds(questionOne.build()).queue();
        return questionOne;
    }

    public EmbedBuilder ExperienceQuestion(TextChannel channel) {
        EmbedBuilder questionOne = new EmbedBuilder()
                .setTitle("**Question Eight**")
                .setDescription("➣ What experience do you have that could help you in this role?")
                .setAuthor("DevHub")
                .setColor(Color.RED);
        channel.sendMessageEmbeds(questionOne.build()).queue();
        return questionOne;
    }

    public EmbedBuilder HelperDutyQuestion(TextChannel channel) {
        EmbedBuilder questionOne = new EmbedBuilder()
                .setTitle("**Question Nine**")
                .setDescription("➣ Please explain what your duties as helper would be.")
                .setAuthor("DevHub")
                .setColor(Color.RED);
        channel.sendMessageEmbeds(questionOne.build()).queue();
        return questionOne;
    }

    public EmbedBuilder ClipQuestion(TextChannel channel) {
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
    private void sendSummaryEmbed(TextChannel channel, List<String> answers) {
        EmbedBuilder summaryEmbed = new EmbedBuilder()
                .setTitle("Staff Application Summary")
                .setDescription("Thank you for submitting your staff application. Here is a summary of your answers:")
                .setColor(Color.GREEN);

        for (int i = 0; i < Question.values().length; i++) {
            Question question = Question.values()[i];
            summaryEmbed.addField(question.name(), answers.get(i), false);
        }

        channel.sendMessageEmbeds(summaryEmbed.build()).queue();
    }

    // Helper method to get the corresponding answer column based on the question
    private String getAnswerForQuestion(Question question) {
        // This is a simplistic approach; you might need to adjust this based on your specific logic
        return "answer" + (question.ordinal() + 1);
    }

}
