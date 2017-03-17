# Octocat-Discord-Bot</h1>
## Introduction
<p>Octocat is a Discord Bot written in Java with the <a href="https://www.github.com/DV8FromTheWorld/JDA">JDA (Java Discord API) library</a>. The purpose of the bot is to create a bridge that connects a Discord Guild (or, simply, a Discord User) with a GitHub Account. The user will be able to create new repositories, manage and delete existing ones, add collaborators, submit pull requests and commit on their branches as well as managing their organizations and team members. All these actions are possible with simple and intuitive "discord-commands" and require an authentication (Both username-password as well as OAuth-tokens are supported).</p>
<p>To use the bot, you will have to invite it to your server. Please continue reading for more details.

## How to invite the bot
<p>Simply click <a href="https://discordapp.com/oauth2/authorize?client_id=292036421341020161&scope=bot&permissions=104062017">this link</a>, and the bot will automatically join your server. During the authorization process, the application will ask you to grant them certain permissions. None of these permissions are essential (apart from the one allowing them to read and send messages), however each of them enhances your experience as a user. <b>The bot does need any administrator privileges</b>.</p>

## How to run the bot on my own computer or webserver
<p>No problem! However, it will be a little more difficult as it requires additional steps to be taken. Fortunately, this guide will explain all of those steps:</p>
<ol>
<li>Head over to the <a href="https://github.com/ShanerX/Octocat-Discord-Bot/releases">releases page</a> and download the most recent file.</li>
<li>Place the file in the directory you wish to run it in and, inside that directory, run the command: <code>java -jar GitHubBot.jar</code> (Please notice that if you rename the file, you will need to adapt the name inside the command. If you want, you can create a <code>.bat</code> file on windows or a <code>.sh</code> file on OSX/Linux and write the command inside, so that you only need to double-click / run that file for the bot to start.</li>
<li>Once it is booted up for the first time, the bot will create a file called <code>config.json</code>. This file should look like <a href="https://gist.github.com/ShanerX/d26731b8829f2f37f5c187c2f5d48678">this</a>.</li>
<li>Next, head over to the <a href="https://discordapp.com/developers/applications/me">Discord Applications page</a> and register a new application by clicking on the <b>New App</b> button. Next, write the name of the Bot inside the <b>App Name</b> field, and (optionally) add a short description as well as an avatar (you may edit this later). Upon clicking on <b>Create App</b>, you will have the chance to edit some stuff. Next, click on <b>Create a Bot User</b>, click on <i>Reveal Token</i>, copy the whole string and save the changes. (Do not close the page yet!)</li>
  <li>Lastly, paste the token inside the configuration file, by replacing "<i>INSERT_TOKEN</i>" with the actual token (Do not remove the double quotes - ""). If you now restart the Bot, unless you missed a step, this is what you should see:
  <p>  <code>[21:49:05] [Info] [JDA]: Login Successful!</code></p>
  <p>  <code>[21:49:05] [Info] [JDASocket]: Connected to WebSocket</code></p>
  <p>  <code>[21:49:06] [Info] [JDA]: Finished Loading!</code></p>
  <li>If you have done everything correctly, you may go back to your Discord App page, and copy the "Client ID", at the top (Not the "Client Secret!") Now, open a new tab and insert <a href="https://discordapp.com/oauth2/authorize?client_id=CLIENT_ID&scope=bot&permissions=104062017">the invite link</a>, replacing <i>CLIENT_ID</i> with your actual Client ID. After selecting your Discord Server and Clicking on <b>Authorize</b>, feel free to close all the open tabs in your browser and get back to the Discord Client. If you now look at the online members, you should see the Bot among them, with the username you specified when creating the Discord Application.</li>
  </ol>
  <p><i>Enjoy!</i></p>
