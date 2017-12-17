package org.shanerx.discord.github.resources;

import java.awt.Color;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.kohsuke.github.GHOrganization;
import org.shanerx.discord.github.GitHubBot;

import net.dv8tion.jda.core.EmbedBuilder;

@SuppressWarnings("unused")
public class BotUtils {
	
	public static String setToString(Set<String> set) {
		StringBuilder sb = new StringBuilder();
		
		for (String s : set) {
			sb.append(s).append("; ");
		}
		
		return sb.toString().substring(0, sb.toString().length() - 2);
	}
	
	public static String listToString(List<String> list) {
		StringBuilder sb = new StringBuilder();
		
		for (String s : list) {
			sb.append(s).append("; ");
		}
		
		return sb.toString().substring(0, sb.toString().length() - 2);
	}
	
	public static String orgsToString(List<GHOrganization> list) throws IOException {
		StringBuilder sb = new StringBuilder();
		
		for (GHOrganization org : list) {
			sb.append(org.getName()).append("; ");
		}
		
		return sb.toString().substring(0, sb.toString().length() - 2);
	}
	
	@SuppressWarnings("deprecation")
	public static EmbedBuilder getEmbed(String msg) {
		EmbedBuilder eb = new EmbedBuilder();
		eb
		.setAuthor(GitHubBot.getInstance().getBotName(), "https://www.github.com/ShanerX/Octocat-Discord-Bot", "http://i.imgur.com/HreJN2S.png")
		.setTitle("GitHub API Response:", "https://www.github.com/ShanerX/Octocat-Discord-Bot")
		.setColor(Color.BLACK)
		.setThumbnail("https://assets-cdn.github.com/images/modules/open_graph/github-mark.png")
		.setDescription(msg)
		.setFooter("Sent by " + GitHubBot.getInstance().getBotName() + " on " + new Date().toGMTString(), "https://www.github.com/ShanerX/Octocat-Discord-Bot");
		return eb;
	}
}