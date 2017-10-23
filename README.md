# Collision Detection Assignment
HideAndSeek:
-
This is a simple find the coin game where a player goes head to head against a bot to collect more coins
then their opponent. The Game is over when all the coins have been collected.

This Game has 2 play mode:
+ Standard: They way its meant to be played.
+ Debugging: This will show more information, such as bounding boxes and the bot's vision polygon.

Controls:
-
* The player will move towards any clicked location on the screen.
* When near a Coin, press E to collect it.
* The game ends when all the coins have been found.
* Pressing ESCAPE will take you back to the main menu.

Compiling:
-
To compile this code please ensure that the Java Compiler is set to 1.8 style
byte code as this assignment uses lambda expressions.

This code follows standard libGDX runtime procedures. The main method can be found in the "desktop" package.

Features:
-
* Vision: This is the games core feature as the player and the bot must search for coins on the map.
* Coin Collection: The winner is the player who collects the most coins. Each coin awards the player 1 point.
* Sound: Picking up a coin will cause a sound to play.
* Menu: The Game has a main menu which allows the player to select a gamemode. NOTE: There is a graphics bug
if the game is restarted from the main menu. This causes the mouse clicks to not line up perfectly with the game map.
Other then that the game is fully functional on a replay.
* AI: The bot in this game has a simple AI, if the bot cannot see a coin, it will search for one. 
If it sees a coin, it will move to collect the coin.

