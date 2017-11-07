# Artificial Intelligence Assignment
HideAndSeek:
-
This is a simple find the coin game where a player goes head to head against a bot to collect more coins
then their opponent. The Game is over when all the coins have been collected.

This Game has 2 play mode:
+ Standard: They way its meant to be played.
+ Debugging: This will show more information, such as bounding boxes, the bot's vision polygon as well as 
pathfinding nodes.

Controls:
-
* The player will move towards any clicked location on the screen.
* When near a Coin, press E to collect it.
* The game ends when all the coins have been found.
* Pressing ESCAPE will take you back to the main menu.

Compiling:
-
To compile this code please ensure that the Java Compiler Target byte code version is set to 1.8 or higher.

This code follows standard libGDX runtime procedures. The main method can be found in the "desktop" package.

There is a JAR file in HideAndSeek/out/../HideAndSeek_JAR/

Features:
-
* Vision: This is the games core feature as the player and the bot must search for coins on the map.
* Coin Collection: The winner is the player who collects the most coins. Each coin awards the player 1 point.
* Sound: Picking up a coin will cause a sound to play.
* Menu: The Game has a main menu which allows the player to select a gamemode. NOTE: There is a graphics bug
if the game is restarted from the main menu. This causes the mouse clicks to not line up perfectly with the game map.
Other then that the game is fully functional on a replay.

Artificial Intelligence:
-
MOVEMENT:

* 2: A*: If the target location is out of vision/not in a direct path, the bot will use the A* algorithm to find a path.

STATE MACHINES:
* SEARCH: In the search state, the bot will move towards a random point on the map. If the bot sees a coin,
it will break from this mode and enter the VISION state.
* VISIBLE: In this state the bot moves towards a visible token. Should any new coin be seen, which is closer
then the current target, the bot will move towards this new coin.
* SEEN: This will move the bot to a location that is out of vision, but where the bot has seen a coin previously. 
Should the coin (or any other coin) come into vision, the bot will move towards this now visible coin (transitioning into
the VISIBLE state).

Known Bugs:
-
* Last Coin throws NullPointer, this only happens if a bot picks up the last coin.
* Bot is frozen if not visible by player.