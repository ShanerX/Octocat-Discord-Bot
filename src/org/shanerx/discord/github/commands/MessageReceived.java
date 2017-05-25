package org.shanerx.discord.github.commands;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.kohsuke.github.GHOrganization;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;
import org.shanerx.discord.github.GitHubAccount;
import org.shanerx.discord.github.GitHubBot;
import org.shanerx.discord.github.resources.BotData;
import org.shanerx.discord.github.resources.BotUtils;

import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MessageReceived extends ListenerAdapter {
	
	private GitHubBot bot;
	
	public MessageReceived(GitHubBot bot) {
		this.bot = bot;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		
		MessageChannel text = event.getChannel();
		String msg = event.getMessage().getContent();
		User author = event.getAuthor();
		
		if (!msg.startsWith(bot.prefix)) {
			return;
		}
		
		msg = msg.substring(bot.prefix.length());
		String[] args = msg.split(" ");
		
		if (msg.startsWith("github login") || msg.startsWith("github logout")) {
			if (!event.isFromType(ChannelType.PRIVATE)) {
				text.sendMessage(BotData.PRIVATE_ONLY).queue();
				return;
			}
		}
		
		GitHubAccount acc = bot.users.get(author);
		if (acc == null) {
			text.sendMessage(BotData.NOT_LOGGED_IN_ERROR.replace("PREFIX", bot.prefix)).queue();
			return;
		}
		GitHub github = null;
		
		try {
			
			github = acc.getAuthType() == GitHubAccount.AuthenticationType.OAUTH ? GitHub.connectUsingOAuth(acc.getOAuthToken()) : GitHub.connectUsingPassword(acc.getUsername(), acc.getPassword());
		
		/* SELF GETTER COMMANDS */
			if (msg.startsWith("github myrepos") || msg.startsWith("github myrepositories")) {
				StringBuilder  sb;
				sb = new StringBuilder();
				for (String name : github.getMyself().getRepositories().keySet()) {
					GHRepository rep = github.getMyself().getRepositories().get(name);
					if (rep.getOwner().getLogin().equals(github.getMyself().getLogin())) {
						sb.append(rep.getName())
						.append("; ");
					}
				}
				text.sendMessage(BotUtils.getEmbed(github.getMyself().getLogin() + "(" + github.getMyself().getName() + ")'s own repositories: " + sb.toString().substring(0, sb.toString().length() - 2)).build()).queue();
			}
			
			else if (msg.startsWith("github allrepositories") || msg.startsWith("github allrepos")) {
				StringBuilder sb = new StringBuilder();
				for (String name : github.getMyself().getAllRepositories().keySet()) {
					GHRepository rep = github.getMyself().getAllRepositories().get(name);
					sb.append(rep.getOwner().getLogin())
					.append("/")
					.append(rep.getName())
					.append("; ");
				}
				text.sendMessage(BotUtils.getEmbed(github.getMyself().getLogin() + "(" + github.getMyself().getName() + ") has access to: " + sb.toString().substring(0,  sb.toString().length() - 2)).build()).queue();
			}
			
			else if (msg.startsWith("github myavatar")) {
				String avatar;
				avatar = github.getMyself().getAvatarUrl();
				text.sendMessage(BotUtils.getEmbed(github.getMyself().getLogin() + "(" + github.getMyself().getName() + ")'s avatar: " + avatar).build()).queue();
			}
			
			else if (msg.startsWith("github mycompany") || msg.startsWith("github mycomp")) {
				String company;
				company = github.getMyself().getCompany();
				text.sendMessage(BotUtils.getEmbed(github.getMyself().getLogin() + "(" + github.getMyself().getName() + ")'s company: " + company).build()).queue();
			}
			
			else if (msg.startsWith("github myemail")) {
				String email;
				email = github.getMyself().getEmail();
				text.sendMessage(BotUtils.getEmbed(github.getMyself().getLogin() + "(" + github.getMyself().getName() + ")'s email address: " + email).build()).queue();
			}
			
			else if (msg.startsWith("github myloc") || msg.startsWith("github mylocation")) {
				String location;
				location = github.getMyself().getLocation();
				text.sendMessage(BotUtils.getEmbed(github.getMyself().getLogin() + "(" + github.getMyself().getName() + ")'s location: " + location).build()).queue();
			}
			
			else if (msg.startsWith("github myfollowers")) {
				StringBuilder  sb;
				sb = new StringBuilder();
				for (GHUser user : github.getMyself().getFollowers()) {
					sb.append(user.getLogin())
					.append("(")
					.append(user.getName())
					.append("); ");
				}
				text.sendMessage(BotUtils.getEmbed(github.getMyself().getLogin() + "(" + github.getMyself().getName() + ")'s followers: " + sb.toString().substring(0, sb.toString().length() - 2)).build()).queue();
			}
			
			else if (msg.startsWith("github myfollows")) {
				StringBuilder  sb;
				sb = new StringBuilder();
				for (GHUser user : github.getMyself().getFollows()) {
					sb.append(user.getLogin())
					.append("(")
					.append(user.getName())
					.append("); ");
				}
				text.sendMessage(BotUtils.getEmbed(github.getMyself().getLogin() + "(" + github.getMyself().getName() + ")'s follows: " + sb.toString().substring(0, sb.toString().length() - 2)).build()).queue();
			}
			
			else if (msg.startsWith("github myorgs") || msg.startsWith("github myorganizations")) {
				StringBuilder  sb;
				sb = new StringBuilder();
				for (GHOrganization o : github.getMyself().getAllOrganizations()) {
					sb.append(o.getLogin())
					.append("(")
					.append(o.getName())
					.append("); ");
				}
				text.sendMessage(BotUtils.getEmbed(github.getMyself().getLogin() + "(" + github.getMyself().getName() + ")'s a member of: " + sb.toString().substring(0, sb.toString().length() - 2)).build()).queue();
			}
			
			else if (msg.startsWith("github mygists")) {
				int count = 0;
				count = github.getMyself().getPublicGistCount();
				text.sendMessage(BotUtils.getEmbed(github.getMyself().getLogin() + "(" + github.getMyself().getName() + ") has **" + Integer.toString(count) + "** public gists!").build()).queue();
			}
			
			else if (msg.startsWith("github mypublicrepos") || msg.startsWith("github mypublicrepositories")) {
				int count = 0;
				count = github.getMyself().getPublicRepoCount();
				text.sendMessage(BotUtils.getEmbed(github.getMyself().getLogin() + "(" + github.getMyself().getName() + ") has **" + Integer.toString(count) + "** public repositories!").build()).queue();
			}
			
			
			/* REPOSITORY SPECIFIC COMMANDS */

			else if (msg.startsWith("github createrepo") || msg.startsWith("github createrepository")) {
				
				if (args.length < 3 || (args.length == 3 && args[2].equalsIgnoreCase("--priv"))) {
					text.sendMessage("Please specify a name for the repository, for example: ``" + bot.prefix + "github createrepo My-wonderful-repository`` or ``" + bot.prefix + "github createrepo --priv My-wonderful-private-repository``.").queue();
					return;
				}
				StringBuilder sb = new StringBuilder();
				boolean priv = false;
				
				for (int i = 2; i < args.length; i++) {
					if (i == 2 && args[2].equalsIgnoreCase("--priv")) {
						priv = true;
						continue;
					}
					sb.append(args[i])
					.append(" ");
				}
				String repoName = sb.toString().trim().replace(" ", "-");
				if (github.getMyself().getRepositories().containsKey(repoName)) {
						text.sendMessage("Sorry, but it appears that you already own a repository called ``" + github.getMyself().getLogin() + "/" + repoName + "``!").queue();
						return;
				}
					
				github.createRepository(repoName, sb.toString().trim(), null, !priv);
				text.sendMessage("Successfully created ``" + github.getMyself().getLogin() + "/" + repoName + "``: Time to build something amazing!").queue();
			}
			
			else if (msg.startsWith("github deleterepo") || msg.startsWith("github deleterepository")) {
				
				if (args.length < 3) {
					text.sendMessage("Please specify a name for the repository, for example: ``" + bot.prefix + "github deleterepo My-wonderful-repository``.").queue();
					return;
				}
				StringBuilder sb = new StringBuilder();
				
				for (int i = 2; i < args.length; i++) {
					sb.append(args[i])
					.append(" ");
				}
				String repoName = sb.toString().trim().replace(" ", "-");
				if (!github.getMyself().getRepositories().containsKey(repoName)) {
					text.sendMessage("Sorry, but it appears that you do not own a repository called ``" + github.getMyself().getLogin() + "/" + repoName + "``!").queue();
					return;
				}
					
				github.getMyself().getRepository(repoName).delete();
				text.sendMessage("Successfully deleted ``" + github.getMyself().getLogin() + "/" + repoName + "``!").queue();
			}
			
			else if (msg.startsWith("github addcollaborators")) {
				
				if (args.length < 4) {
					text.sendMessage("Please specify a name for the repository and at least one collaborator, for example: ``" + bot.prefix + "github addcontributor Octocat-Discord-Bot ShanerX`` or ``" + bot.prefix + "github addcontributor Octocat-Discord-Bot ShanerX ess3sq DV8FromTheWorld``").queue();
					return;
				}
				String repoName = args[2];
				if (!github.getMyself().getAllRepositories().containsKey(repoName)) {
					text.sendMessage("Sorry, but it appears that you do not own a repository called ``" + github.getMyself().getLogin() + "/" + repoName + "``!").queue();
					return;
				}
					
				GHRepository repo = github.getMyself().getRepository(repoName);
				List<GHUser> collaborators = new ArrayList<>();
				try {
					for (int i = 3; i < args.length; i++) {
						collaborators.add(github.getUser(args[i]));
					}
					repo.addCollaborators(collaborators);
				} catch (FileNotFoundException fnfe) {
					text.sendMessage("Sorry, but at least one of these collaborators could not be found!").queue();
					return;
				}
				text.sendMessage("Successfully added **" + collaborators.size() + "** new collaborators to ``" + github.getMyself().getLogin() + "/" + repoName + "``!").queue();
			}
			
			else if (msg.startsWith("github removecollaborators")) {
				
				if (args.length < 4) {
					text.sendMessage("Please specify a name for the repository and at least one collaborator, for example: ``" + bot.prefix + "github removecontributor Octocat-Discord-Bot ShanerX`` or ``" + bot.prefix + "github removecontributor Octocat-Discord-Bot ShanerX ess3sq DV8FromTheWorld``").queue();
					return;
				}
				String repoName = args[2];
				if (!github.getMyself().getAllRepositories().containsKey(repoName)) {
					text.sendMessage("Sorry, but it appears that you do not own a repository called ``" + github.getMyself().getLogin() + "/" + repoName + "``!").queue();
					return;
				}
					
				GHRepository repo = github.getMyself().getRepository(repoName);
				List<GHUser> collaborators = new ArrayList<>();
				try {
					for (int i = 3; i < args.length; i++) {
						collaborators.add(github.getUser(args[i]));
					}
					repo.removeCollaborators(collaborators);
				} catch (FileNotFoundException fnfe) {
					text.sendMessage("Sorry, but at least one of these collaborators could not be found!").queue();
					return;
				}
				text.sendMessage("Successfully removed **" + collaborators.size() + "** collaborators to ``" + github.getMyself().getLogin() + "/" + repoName + "``!").queue();
			}
			
			else if (msg.startsWith("github fork")) {
				
				if (args.length < 3) {
					text.sendMessage("Please enter a valid repository, for example: ``" + bot.prefix + "github fork Octocat-Discord-Bot``.").queue();
					return;
				}
				String login = null;
				String repoName = null;
				try {
					login = args[2].split("/")[0];
					repoName = args[2].split("/")[1];
				} catch (Exception ex) {
					text.sendMessage("Sorry, but the repository you entered is invalid. Please enter a valid repository, for example: ``" + bot.prefix + "github fork ShanerX/Octocat-Discord-Bot``.").queue();
					return;
				}					
				github.getUser(login).getRepository(repoName).fork();
				text.sendMessage("Successfully forked ``" + login + "/" + repoName + "``!").queue();
			}
			
			else if (msg.startsWith("github enableissues")) {
				
				if (args.length < 3) {
					text.sendMessage("Please enter a valid repository, for example: ``" + bot.prefix + "github enableissues Octocat-Discord-Bot``.").queue();
					return;
				}
				String repoName = args[2];
				if (!github.getMyself().getRepositories().containsKey(repoName)) {
					text.sendMessage("Sorry, but the repository you entered is invalid. Please enter a valid repository, for example: ``" + bot.prefix + "github enableissues Octocat-Discord-Bot``.").queue();
					return;
				}
							
				github.getMyself().getRepository(repoName).enableIssueTracker(true);
				text.sendMessage("Successfully enabled the Issue-Tracker for ``" + github.getMyself().getLogin() + "/" + repoName + "``!").queue();
			}
			
			else if (msg.startsWith("github disableissues")) {
				
				if (args.length < 3) {
					text.sendMessage("Please enter a valid repository, for example: ``" + bot.prefix + "github disableissues Octocat-Discord-Bot``.").queue();
					return;
				}
				String repoName = args[2];
				if (!github.getMyself().getRepositories().containsKey(repoName)) {
					text.sendMessage("Sorry, but the repository you entered is invalid. Please enter a valid repository, for example: ``" + bot.prefix + "github enableissues Octocat-Discord-Bot``.").queue();
					return;
				}
							
				github.getMyself().getRepository(repoName).enableIssueTracker(false);
				text.sendMessage("Successfully **disabled** the Issue-Tracker for ``" + github.getMyself().getLogin() + "/" + repoName + "``!").queue();
			}
			
			else if (msg.startsWith("github enablewiki")) {
				
				if (args.length < 3) {
					text.sendMessage("Please enter a valid repository, for example: ``" + bot.prefix + "github enablewiki Octocat-Discord-Bot``.").queue();
					return;
				}
				String repoName = args[2];
				if (!github.getMyself().getRepositories().containsKey(repoName)) {
					text.sendMessage("Sorry, but the repository you entered is invalid. Please enter a valid repository, for example: ``" + bot.prefix + "github enablewiki Octocat-Discord-Bot``.").queue();
					return;
				}
							
				github.getMyself().getRepository(repoName).enableWiki(true);
				text.sendMessage("Successfully **enabled** the Wiki for ``" + github.getMyself().getLogin() + "/" + repoName + "``!").queue();
			}
			
			else if (msg.startsWith("github disablewiki")) {
				
				if (args.length < 3) {
					text.sendMessage("Please enter a valid repository, for example: ``" + bot.prefix + "github disablewiki Octocat-Discord-Bot``.").queue();
					return;
				}
				String repoName = args[2];
				if (!github.getMyself().getRepositories().containsKey(repoName)) {
					text.sendMessage("Sorry, but the repository you entered is invalid. Please enter a valid repository, for example: ``" + bot.prefix + "github disablewiki Octocat-Discord-Bot``.").queue();
					return;
				}
							
				github.getMyself().getRepository(repoName).enableWiki(false);
				text.sendMessage("Successfully **disabled** the Wiki for ``" + github.getMyself().getLogin() + "/" + repoName + "``!").queue();
			}
			
			else if (msg.startsWith("github enabledownloads")) {
				
				if (args.length < 3) {
					text.sendMessage("Please enter a valid repository, for example: ``" + bot.prefix + "github enabledownloads Octocat-Discord-Bot``.").queue();
					return;
				}
				String repoName = args[2];
				if (!github.getMyself().getRepositories().containsKey(repoName)) {
					text.sendMessage("Sorry, but the repository you entered is invalid. Please enter a valid repository, for example: ``" + bot.prefix + "github enabledownloads Octocat-Discord-Bot``.").queue();
					return;
				}
							
				github.getMyself().getRepository(repoName).enableDownloads(true);
				text.sendMessage("Successfully **enabled** downloads for ``" + github.getMyself().getLogin() + "/" + repoName + "``!").queue();
			}
			
			else if (msg.startsWith("github disabledownloads")) {
				
				if (args.length < 3) {
					text.sendMessage("Please enter a valid repository, for example: ``" + bot.prefix + "github disabledownloads Octocat-Discord-Bot``.").queue();
					return;
				}
				String repoName = args[2];
				if (!github.getMyself().getRepositories().containsKey(repoName)) {
					text.sendMessage("Sorry, but the repository you entered is invalid. Please enter a valid repository, for example: ``" + bot.prefix + "github disabledownloads Octocat-Discord-Bot``.").queue();
					return;
				}
							
				github.getMyself().getRepository(repoName).enableDownloads(false);
				text.sendMessage("Successfully **disabled** downloads for ``" + github.getMyself().getLogin() + "/" + repoName + "``!").queue();
			}
			
			else if (msg.startsWith("github master")) {
				
				if (args.length < 3) {
					text.sendMessage("Please enter a valid repository, for example: ``" + bot.prefix + "github master Octocat-Discord-Bot``.").queue();
					return;
				}
				String repoName = args[2];
				if (!github.getMyself().getRepositories().containsKey(repoName)) {
					text.sendMessage("Sorry, but the repository you entered is invalid. Please enter a valid repository, for example: ``" + bot.prefix + "github master Octocat-Discord-Bot``.").queue();
					return;
				}
							
				String master = github.getMyself().getRepository(repoName).getMasterBranch();
				text.sendMessage("``" + github.getMyself().getLogin() + "/" + repoName + "``'s master branch is ``" + master + "``.").queue();
			}
			
			else if (msg.startsWith("github desc") || msg.startsWith("github description")) {
				
				if (args.length < 3) {
					text.sendMessage("Please enter a valid repository, for example: ``" + bot.prefix + "github desc Octocat-Discord-Bot``.").queue();
					return;
				}
				String repoName = args[2];
				if (!github.getMyself().getRepositories().containsKey(repoName)) {
					text.sendMessage("Sorry, but the repository you entered is invalid. Please enter a valid repository, for example: ``" + bot.prefix + "github desc Octocat-Discord-Bot``.").queue();
					return;
				}
							
				String desc = github.getMyself().getRepository(repoName).getDescription();
				text.sendMessage("``" + github.getMyself().getLogin() + "/" + repoName + "``'s description is ``" + desc + "``.").queue();
			}
			
			else if (msg.startsWith("github branches")) {
				
				if (args.length < 3) {
					text.sendMessage("Please enter a valid repository, for example: ``" + bot.prefix + "github branch Octocat-Discord-Bot``.").queue();
					return;
				}
				String repoName = args[2];
				if (!github.getMyself().getRepositories().containsKey(repoName)) {
					text.sendMessage("Sorry, but the repository you entered is invalid. Please enter a valid repository, for example: ``" + bot.prefix + "github branch Octocat-Discord-Bot``.").queue();
					return;
				}
				StringBuilder sb = new StringBuilder();
				for (String br : github.getMyself().getRepository(repoName).getBranches().keySet()) {
					sb.append(br)
					.append("; ");
				}
				text.sendMessage(BotUtils.getEmbed(github.getMyself().getLogin() + "/" + args[2] + "'s branches are: " + sb.toString().substring(0, sb.toString().length() - 2)).build()).queue();
			}
			
			else if (msg.startsWith("github watchers")) {
				
				if (args.length < 3) {
					text.sendMessage("Please enter a valid repository, for example: ``" + bot.prefix + "github watchers Octocat-Discord-Bot``.").queue();
					return;
				}
				String repoName = args[2];
				if (!github.getMyself().getRepositories().containsKey(repoName)) {
					text.sendMessage("Sorry, but the repository you entered is invalid. Please enter a valid repository, for example: ``" + bot.prefix + "github watchers Octocat-Discord-Bot``.").queue();
					return;
				}
				int watchers = github.getMyself().getRepository(repoName).getWatchers();
				text.sendMessage(BotUtils.getEmbed(github.getMyself().getLogin() + "/" + args[2] + "has **" + watchers + "** watchers.").build()).queue();
			}
			
			else if (msg.startsWith("github readme")) {
				
				if (args.length < 3) {
					text.sendMessage("Please enter a valid repository, for example: ``" + bot.prefix + "github readme Octocat-Discord-Bot``.").queue();
					return;
				}
				String repoName = args[2];
				if (!github.getMyself().getRepositories().containsKey(repoName)) {
					text.sendMessage("Sorry, but the repository you entered is invalid. Please enter a valid repository, for example: ``" + bot.prefix + "github readme Octocat-Discord-Bot``.").queue();
					return;
				}
				try {
					String readme = github.getMyself().getRepository(repoName).getReadme().getHtmlUrl();
					text.sendMessage(BotUtils.getEmbed("``" + github.getMyself().getLogin() + "/" + args[2] + "``'s readme is located here: " + readme).build()).queue();
				} catch (FileNotFoundException fnfe) {
					text.sendMessage("``" + github.getMyself().getLogin() + "/" + repoName + "`` does not have a file called ``README.md``!").queue();
				}
			}
			
			
			
			
			
			
			
			
		} catch (IOException e) {
			text.sendMessage(BotData.GENERIC_ERROR.replace("PREFIX", bot.prefix)).queue();
			e.printStackTrace();
			return;
		}

	}

}