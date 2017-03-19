# Octocat: A Discord Bridge</h1>
## Introduction
<p>Octocat is a Discord Bot written in Java with the <a href="https://www.github.com/DV8FromTheWorld/JDA">JDA (Java Discord API) library</a>. The purpose of the bot is to create a bridge that connects a Discord Guild (or, simply, a Discord User) with a GitHub Account. The user will be able to create new repositories, manage and delete existing ones, add collaborators, submit pull requests and commit on their branches as well as managing their organizations and team members. All these actions are possible with simple and intuitive "discord-commands" and require an authentication (Both username-password as well as OAuth-tokens are supported).</p>
<p>To use the bot, you will have to invite it to your server. Please continue reading for more details.

## How to invite the bot
<p>Simply click <a href="https://discordapp.com/oauth2/authorize?client_id=292036421341020161&scope=bot&permissions=104062017">this link</a>, and the bot will automatically join your server. During the authorization process, the application will ask you to grant them certain permissions. None of these permissions are essential (apart from the one allowing them to read and send messages), however each of them enhances your experience as a user. <b>The bot does not need any administrator privileges</b>.</p>

## How to run the bot on my own computer or webserver
<p>If you wish to run the Bot on your own computer or webserver and enjoy directly controlling the bot, so be it! However, it will be a little more difficult as it requires additional steps to be taken. Fortunately, this guide will explain all of those steps in an simple and <i>newbie-friendly</i> way:</p>
<ol>
<li>Head over to the <a href="https://github.com/ShanerX/Octocat-Discord-Bot/releases">releases page</a> and download the most recent file.</li>
<li>Place the file in the directory you wish to run it in and, inside that directory, run the command: <code>java -jar GitHubBot.jar</code> (Please notice that if you rename the file, you will need to adapt the name inside the command. If you want, you can create a <code>.bat</code> file on windows or a <code>.sh</code> file on OSX/Linux and write the command inside, so that you only need to double-click / run that file for the bot to start.</li>
<li>Once it is booted up for the first time, the bot will generate a file called <code>config.json</code>. The file should look like <a href="https://gist.github.com/ShanerX/d26731b8829f2f37f5c187c2f5d48678">this</a>.</li>
<li>Next, head over to the <a href="https://discordapp.com/developers/applications/me">Discord Applications page</a> and register a new application by clicking on the <b>New App</b> button. Next, write the name of the Bot inside the <b>App Name</b> field, and (optionally) add a short description as well as an avatar (you may edit this later). Upon clicking on <b>Create App</b>, you will have the chance to edit some stuff. Now, click on <b>Create a Bot User</b>, click on <i>Reveal Token</i>, copy the whole string and save the changes. (Do not close the page yet!)</li>
  <li>Lastly, paste the token inside the configuration file, by replacing "<i>INSERT_TOKEN</i>" with the actual token you got from the App panel (Do not remove the double quotes - ""). If you now restart the Bot, unless you missed a step, this is what you should see:
  <p>  <code>[21:49:05] [Info] [JDA]: Login Successful!</code></p>
  <p>  <code>[21:49:05] [Info] [JDASocket]: Connected to WebSocket</code></p>
  <p>  <code>[21:49:06] [Info] [JDA]: Finished Loading!</code></p>
  <li>If you have done everything correctly, you may go back to your Discord App page, and copy the "Client ID", at the top (Not the "Client Secret!") Now, open a new tab and insert <a href="https://discordapp.com/oauth2/authorize?client_id=CLIENT_ID&scope=bot&permissions=104062017">the invite link</a>, replacing <i>CLIENT_ID</i> with your actual Client ID. After selecting your Discord Server and Clicking on <b>Authorize</b>, feel free to close all the open tabs in your browser and get back to the Discord Client. If you now look at the online members, you should see the Bot among them, with the username you specified when creating the Discord Application and the game configured in the <code>config.json</code>. (Every time you modify a setting, you will need to restart the bot for the changes to be applied.</li>
  </ol>
  <p><i>Enjoy!</i></p>
  
  ## I am a Developer and wish to fork the project
  <p>Excellent, We are always happy to hear that other developers are willing to continue our work! Spend a minute, however, on reading about which dependencies you need:</p>
  <ul>
  <li>This project uses the <a href="https://www.github.com/DV8FromTheWorld/JDA">JDA (Java Discord API) library</a>, an extensive API written in Java. You may find the latest version <a href="http://home.dv8tion.com:8080/job/JDA/">here</a> and the javadocs <a href="http://home.dv8tion.net:8080/job/JDA/javadoc/">here</a>. There also is a public support discord server, which you can join through an <a href="https://discord.gg/0hMr4ce0tIl3SLv5">instant invite</a>.</li>
  <li>The Bot also uses the <a href="https://github.com/fangyidong/json-simple">Json-Simple</a> library, which can be downloaded <a href="https://code.google.com/archive/p/json-simple/">here</a>.</li>
  <li>Finally, there is a third dependency, the <a href="http://github-api.kohsuke.org/">GitHub API for Java</a>. All the Maven builds, source and javadocs are on its website.</li>
  </ul>
  <p>If you have added stuff to the bot or fixed bugs, you may submit a pull-request and merge your fork with the original project. You will retain copyright over all your code. If, however, you decide to re-distribute the Bot on your own, make sure you read and follow the <a href="https://www.github.com/ShanerX/Octocat-Discord-Bot/blob/master/LICENSE.md">License</a>.</p>
  
  ## Features
