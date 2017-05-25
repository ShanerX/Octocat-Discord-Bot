package org.shanerx.discord.github;

public class GitHubAccount {
	
	private String username;
	private String password;
	
	private String token;
	
	private AuthenticationType authType;
	
	public enum AuthenticationType {
		USER_PASS,
		OAUTH;
	}
	
	public GitHubAccount(final String user, final String pass) {
		username = user;
		password = pass;
		authType = AuthenticationType.USER_PASS;
	}
	
	public GitHubAccount(final String token) {
		this.token = token;
		authType = AuthenticationType.OAUTH;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getOAuthToken() {
		return token;
	}
	
	public AuthenticationType getAuthType() {
		return authType;
	}

}
