# Collision Detection Assignment
HideAndSeek:
-
I built the collision detection on top of the basis for my final game. 
The project uses a TiledMap object for the background and collision objects.
The Player class extends the Sprite class.

This Game has 2 play modee:
+ Tile checking. This mode simply used the sprite bounding box to determine collisions.
+ Bit checking. This mode uses bit checking for all collisions. 

Controls:
-
* The player will move towards any clicked location on the screen.
* Pressing ESCAPE will take you back to the main menu.

Compiling:
-
To compile this code please ensure that the Java Compiler is set to 1.8 style
byte code as this assignment uses lambda expressions.

This code follows standard libGDX runtime procedures. The main method can be found in the "desktop" package.

Visual Feedback:
-
Collision feedback is given through the console.
- "Tile Collision!"         - There was a bounding box collision, no further checking done.
- "Bitchecking!"            - Starting the bit checking collision
- "Pixel Color: <Integer>"  - The RGBA value of the pixel causing the collision.
