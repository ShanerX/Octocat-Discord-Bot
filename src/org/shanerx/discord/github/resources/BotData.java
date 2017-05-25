package org.shanerx.discord.github.resources;

public class BotData {
	
	public static final String LOGIN_HELP = "Sorry, but you either need to provide your username and password or an oauth token. Example:\n``PREFIXgithub login MyUsername MyPassword123`` or ``PREFIXgithub login 77d96f4f6a0b5b82b4f14440a1fd9dd5af6cc135``";
	public static final String LOGIN_ERROR_OAUTH = "Sorry, but an internal error occurred while attempting to login with OAuth. Please check token validity and try again.";
	public static final String LOGIN_ERROR_USER_PASS = "Sorry, but an internal error occurred while attempting to validate your credentials. Please make sure you have entered a valid username and password";
	public static final String LOGIN_SUCCESS = "You have successfully logged into your GitHub account as **NAME**. To revoke access, type ``PREFIXgithub logout``!";
	public static final String ALREADY_LOGGED_IN = "Sorry, but it appears that you're already logged in. Please use ``PREFIXgithub logout`` first, if you wish to login as another user.";
	public static final String GENERIC_ERROR = "Sorry, but an internal error occurred. Please logout and try again: ``PREFIXgithub logout``";
	
	public static final String LOGOUT = "You have successfully revoked access to your account.";
	
	public static final String NOT_LOGGED_IN_ERROR = "Sorry, but you must be logged in to perform this action. Use ``PREFIXgithub login`` for more information.";

	public static final String PRIVATE_ONLY = "Sorry, but due to security reasons you may only perform this action in a Direct Message";
	
}
