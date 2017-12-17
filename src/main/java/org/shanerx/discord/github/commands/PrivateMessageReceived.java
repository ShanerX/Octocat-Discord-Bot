package org.shanerx.discord.github.commands;

import java.io.IOException;

import org.kohsuke.github.GitHub;
import org.shanerx.discord.github.GitHubAccount;
import org.shanerx.discord.github.GitHubBot;
import org.shanerx.discord.github.resources.BotData;

import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class PrivateMessageReceived extends ListenerAdapter {
	
	private GitHubBot bot;
	
	public PrivateMessageReceived(GitHubBot bot) {
		this.bot = bot;
	}

	@Override
	public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
		PrivateChannel dm = event.getChannel();
		String msg = event.getMessage().getContent();
		User author = event.getAuthor();
		
		if (!msg.startsWith(bot.prefix)) {
			return;
		}
		
		msg = msg.substring(bot.prefix.length());
		String[] args;
		
		if (msg.startsWith("github login")) {
			
			if (bot.users.containsKey(author)) {
				dm.sendMessage(BotData.ALREADY_LOGGED_IN.replace("PREFIX", bot.prefix)).queue();
				return;
			}
			
			args = msg.split(" ");
			if (args.length == 2) {
				dm.sendMessage(BotData.LOGIN_HELP.replace("PREFIX", bot.prefix)).queue();
				return;
			}
			
			GitHub github = null;
			if (args.length == 3) {
				
				try {
					github = GitHub.connectUsingOAuth(args[2]);
					github.checkApiUrlValidity();
				} catch (IOException e) {
					dm.sendMessage(BotData.LOGIN_ERROR_OAUTH).queue();
					return;
				}
				
				bot.users.put(author, new GitHubAccount(args[2]));
				
				try {
					dm.sendMessage(BotData.LOGIN_SUCCESS.replace("NAME", github.getMyself().getName()).replace("PREFIX", bot.prefix)).queue();
					return;
				} catch (IOException e) {
					dm.sendMessage(BotData.LOGIN_ERROR_OAUTH).queue();
					return;
				}	
			}
			
			if (args.length >= 4) {
				try {
					github = GitHub.connectUsingPassword(args[2], args[3]);
				} catch (IOException e) {
					dm.sendMessage(BotData.LOGIN_ERROR_USER_PASS).queue();
					return;
				}
				bot.users.put(author, new GitHubAccount(args[2], args[3]));
				
				try {
					dm.sendMessage(BotData.LOGIN_SUCCESS.replace("NAME", github.getMyself().getName()).replace("PREFIX", bot.prefix)).queue();
				} catch (IOException e) {
					dm.sendMessage(BotData.LOGIN_ERROR_USER_PASS).queue();
					return;
				}
			}
		}
		
		else if (msg.startsWith("github logout")) {
			
			if (!bot.users.containsKey(author)) {
				dm.sendMessage(BotData.NOT_LOGGED_IN_ERROR.replace("PREFIX", bot.prefix)).queue();
				return;
			}
			
			bot.users.remove(author);
			dm.sendMessage(BotData.LOGOUT).queue();
			return;
		}
	}
}