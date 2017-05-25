package org.shanerx.discord.github;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.login.LoginException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.shanerx.discord.github.commands.MessageReceived;
import org.shanerx.discord.github.commands.PrivateMessageReceived;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

public class GitHubBot {
	
	public Map<User, GitHubAccount> users;
	public String prefix;
	
	private static GitHubBot instance;
	private static String botName;
	
	private GitHubBot() {
		users = new HashMap<>();
		instance = this;
	}
	
	public String getBotName() {
		return botName;
	}
	
	public static GitHubBot getInstance() {
		return instance;
	}

	public static final String INVITE = "https://discordapp.com/oauth2/authorize?client_id=292036421341020161&scope=bot&permissions=104062017";
	
	public static void main(String[] args) {
		File config = new File("config.json");
		if (!config.exists()) {
			try {
				config.createNewFile();
				initConfig(config);
				System.out.println("Creating configuration file...\nPlease edit the values accordingly and restart the bot.");
				System.exit(-1);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		JSONParser parser = new JSONParser();
		JSONObject obj = null;
		
		try {
			obj = (JSONObject) parser.parse(new FileReader(config));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		final String TOKEN = (String) obj.get("Access_Token");
		botName = (String) obj.get("Bot_Name");
		
		GitHubBot bot = new GitHubBot();
		bot.prefix = (String) obj.get("Command_Prefix");
		
		JDABuilder jb = new JDABuilder(AccountType.BOT);
				jb.setAutoReconnect(true)
				.setToken(TOKEN)
				.setGame(Game.of((String) obj.get("Game")));
				
		JDA jda;
		try {
			jda = jb.buildAsync();
			jda.addEventListener(new PrivateMessageReceived(bot));
			jda.addEventListener(new MessageReceived(bot));
		} catch (LoginException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (RateLimitedException e) {
			e.printStackTrace();
		}
	}
	
	private static void initConfig(File f) {
		try {
			PrintWriter pw = new PrintWriter(f);
			pw.write("{\n  \"Access_Token\": \"INSERT_TOKEN\",\n  \"Game\": \"GitHub Developer API\",\n  \"Client_ID\": \"INSERT_ID\",\n  \"Command_Prefix\": \"/\",\n  \"Bot_Name\": \"Octocat\"\n}");
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}

}
