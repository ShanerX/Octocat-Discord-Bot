# Octocat-Discord-Bot</h1>
## Introduction
<p>Octocat is a Discord Bot written in Java with the [JDA (Java Discord API) library](https://www.github.com/DV8FromTheWorld/JDA). The purpose of the bot is to create a bridge that connects a Discord Guild (or, simply, a Discord User) with a GitHub Account. The user will be able to create new repositories, manage and delete existing ones, add collaborators, submit pull requests and commit on their branches as well as managing their organizations and team members. All these actions are possible with simple and intuitive "discord-commands" and require an authentication (Both username-password as well as OAuth-tokens are supported).</p>
<p>To use the bot, you will have to invite it to your server. Please continue reading for more details.

## How to invite the bot
<p>Okay, simply click [this link](https://discordapp.com/oauth2/authorize?client_id=292036421341020161&scope=bot&permissions=104062017), adn the bot will automatically join your server. During the authorization process, the application will ask you to grant them certain permissions. None of these permissions are essential (apart from the one allowing them to read and send messages), however each of them enhances your experience as a user. <b>The bot does need any administrator privileges</b>.</p>

## How to run the bot on my own computer or webserver
<p>No problem! However, it will be a little more difficult as it requires additional steps to be taken. Fortunately, this guide will explain all of those steps:</p>
<ol>
<li>Head over to the [releases page](https://github.com/ShanerX/Octocat-Discord-Bot/releases) and download the most recent file.</li>
<li>Place the file in the directory you wish to run it in and, inside that directory, run the command: ``java -jar GitHubBot.jar`` (Please notice that if you rename the file, you will need to adapt the name inside the command. If you want, you can create a ``.bat`` file on windows or a ``.sh`` file on OSX/Linux and write the command inside, so that you only need to double-click / run that file for the bot to start.</li>
<li>Once it is booted up for the first time, the bot will create a file called ``config.json``. This file should look like this:
<code>{
<p>  "Access_Token": "INSERT_TOKEN",
<p>  "Game": "GitHub Developer API",
<p>  "Client_ID": "INSERT_ID",
<p>  "Command_Prefix": "/",
<p>  "Bot_Name": "Octocat"
<p>  }</code></li>
  <li>Next, head over to the [Discord Applications page](https://discordapp.com/developers/applications/me) and register a new application, like so:
