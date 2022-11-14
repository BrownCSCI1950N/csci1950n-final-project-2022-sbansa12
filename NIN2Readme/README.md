# NIN2 Handin README

## Banner ID: 
- B01601657

## GitHub Repo:
- https://github.com/BrownCSCI1950N/2d-game-engines-2022-stencil-sahilbansal2701

## Version Number:
- Nin 2.0

## Demo Uploaded to Slack: 
- Yes

--------------------------------------------------------------
## Primary Requirements:
| Requirement                                                                                                                                                                                                     | Location in code or steps to view in game                                                                                                                                                   |
|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Your handin must meet all global requirements.                                                                                                                                                                  | ```Completed```                                                                                                                                                                             |
| Your engine must correctly implement saving and loading through serialization.                                                                                                                                  | ```Look in engine/SavingLoading/SaveFile to see how saving and loading is implemented.```                                                                                                   |
| Your engine must correctly support raycasting for polygons and AABs.                                                                                                                                            | ```Look in engine/Shapes/Polygon/raycast() to see the code. AAB just creates a Polygon of itself and calls the Polygon version of raycast.```                                               |
| You must complete the debugger to demonstrate correct raycasting for polygons for AABs.                                                                                                                         | ```Completed```                                                                                                                                                                             |
| Your player must be able to fire projectiles.                                                                                                                                                                   | ```Player is able to shoot a projectile via the left mouse click. Player can only shoot boxes.```                                                                                           |
| Your game must be loaded from a file. For this requirement, you can save your game using any file type, formatted as you please. You must provide at least one file that we can load in your game successfully. | ```My game is saved using the XML format. There is a file in nin/SaveFiles that can be loaded in via the game. Start the game, cancel the instructions, click the button that says load.``` |
| You must be able to save your game state, restart the game, and then load that game state.                                                                                                                      | ```You can save the game in the level selection page, then close the game, go to the save/load screen and load in the game.```                                                              |
| The player must always be in view.                                                                                                                                                                              | ```Completed```                                                                                                                                                                             |
| It must be possible to start a new game without restarting the program.                                                                                                                                         | ```You can start a new game without restarting the program by going back to the save/load screen and clicking new game or deleting one of the load files and creating a new game.```        |

## Secondary Requirements:
| Requirement                                                                                                                                                              | Location in code or steps to view in game                                                                                                                             |
|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Your engine must meet all primary engine requirements.                                                                                                                   | ```Completed```                                                                                                                                                       |
| Your engine must correctly support raycasting for circles.                                                                                                               | ```Look in engine/Shapes/Circle/raycast() to see the code.```                                                                                                         |
| You must complete the debugger to demonstrate correct raycasting for circles.                                                                                            | ```Completed```                                                                                                                                                       |
| Your game must meet all primary game requirements.                                                                                                                       | ```Completed```                                                                                                                                                       |
| There must be a polished UI for saving and loading.                                                                                                                      | ```Completed```                                                                                                                                                       |
| Save files must be written in XML format. This will help organize your saves, and also java has code for parsing these files.                                            | ```Look in engine/SavingLoading/SaveFile to see how XML files are read and written to. Look in nin/SaveFiles to see examples of XML save files for the game.```       |
| The player must be able to fire projectiles that travel instantly using raycasting. Projectiles must apply an impulse to whatever they hit in the direction of that ray. | ```Projectiles shot by player do this. Look into engine/Components/ShootRayComponent and the end of nin/NinGame/addPlayer() to see the code that pertains to this.``` |
| Your game must meet at least two of the extra game requirements.                                                                                                         | ``` The two extra game requirements I have implemented are listed below.```                                                                                           |
| Multiple levels that automatically load and transition as the player continues through the game.                                                                         | ```When a level is completed and SPACE is clicked, the next level is automatically loaded.```                                                                         |
| A non-trivial puzzle that must be solved in order to win the game or complete a level.                                                                                   | ```Level 09 and Level 15```                                                                                                                                           |

--------------------------------------------------------------

## Instructions on How to Run:
1) Run the main function in nin/Main.
2) Wait till the game screen pops up.
3) Click the start button on the title screen.
4) After reading the instructions, click the "X" to close the instructions screen.
5) Click either "new game" to start a new game or "load:" to load in a game with some levels already completed. Clicking "x" deletes that load file.
6) Click the level you want to play
7) Play the game!
8) Use the WASD keys to move and jump. Clicking Q will cause the game to restart by going back to the level selection screen. Clicking K will cause the player to appear back at the start. Left Mouse Click shoots a ray that gives what it hits an impulse in that direction.
9) The goal is to click the button then reach the exit!

## Levels Peculiarities
- 00: Used to test gravity.
- 01: Used to test x movement.
- 02: Used to test x movement and gravity.
- 03: Used to test jump.
- 04: Used to test jump.
- 05: Used to test restitution.
- 06: Used to test restitution of multiple objects colliding.
- 07: Used to test restitution of multiple objects colliding with special physic-y walls.
- 08: Used to test jumping on box.
- 09: Used to test jumping on box.
- 10: Used to test polygon.
- 11: Used to test polygon.
- 12: Used to test sliding and polygons.
- 13: Used to test box falling due to gravity.
- 14: Used to test falling box on wall with physics
- 15: Used to test everything in tandem.

## Known bugs: 
- None

## Hours spent on assignment: 
- 30