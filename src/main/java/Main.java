import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;

public class Main extends ListenerAdapter{
    @JsonProperty
    int[] targetIds;
    @JsonProperty
    String token;
//647480370249334804
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent guildMessageReceivedEvent)
    {
        String targetId = "554071715878010900";
        if (guildMessageReceivedEvent.getAuthor().isBot()) {
            return;
        }

        if (!guildMessageReceivedEvent.getAuthor().getId().equals(targetId)) {
            return;
        }
        JDA jda = guildMessageReceivedEvent.getJDA();
        Guild guild = guildMessageReceivedEvent.getGuild();
        long responseNumber = guildMessageReceivedEvent.getResponseNumber();
        User author = guildMessageReceivedEvent.getAuthor();
        Message message = guildMessageReceivedEvent.getMessage();

        message.addReaction("Honkler:647480370249334804").queue();
        String msg = message.getContentDisplay();
    }

    public static void main(String...argv) throws LoginException
    {
        try {
            File file = new File("path/to/my/yaml/usersAndGroups.yaml");
            Main.getSettings(file);
            String token = "";
            JDA jda = new JDABuilder(token).addEventListeners(new Main()).build();
            jda.awaitReady();
        } catch (SettingsException settingsException) {
            System.out.println("Could not read settings file");
        } catch (InterruptedException | IOException e){
            e.printStackTrace();
        }
    }

    private static Settings getSettings(final File file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        try {
            return objectMapper.readValue(file, Settings.class);
        } catch (IOException exception) {
            throw new SettingsException();
        }
    }
}
