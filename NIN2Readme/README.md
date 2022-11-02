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
| Requirement                                                                                                                                                                                                     | Location in code or steps to view in game                                                                                                     |
|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------|
| Your handin must meet all global requirements.                                                                                                                                                                  | ```Completed```                                                                                                                               |
| Your engine must correctly implement saving and loading through serialization.                                                                                                                                  ||
| Your engine must correctly support raycasting for polygons and AABs.                                                                                                                                            | ```Look in engine/Shapes/Polygon/raycast() to see the code. AAB just creates a Polygon of itself and calls the Polygon version of raycast.``` |
| You must complete the debugger to demonstrate correct raycasting for polygons for AABs.                                                                                                                         | ```Completed```                                                                                                                               |
| Your player must be able to fire projectiles.                                                                                                                                                                   ||
| Your game must be loaded from a file. For this requirement, you can save your game using any file type, formatted as you please. You must provide at least one file that we can load in your game successfully. ||
| You must be able to save your game state, restart the game, and then load that game state.                                                                                                                      ||
| The player must always be in view.                                                                                                                                                                              | ```Completed```                                                                                                                               |
| It must be possible to start a new game without restarting the program.                                                                                                                                         ||

## Secondary Requirements:
| Requirement                                                                                                                                                              | Location in code or steps to view in game                                   |
|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------|
| Your engine must meet all primary engine requirements.                                                                                                                   | ```Completed```                                                             |
| Your engine must correctly support raycasting for circles.                                                                                                               | ```Look in engine/Shapes/Circle/raycast() to see the code.```               |
| You must complete the debugger to demonstrate correct raycasting for circles.                                                                                            | ```Completed```                                                             |
| Your game must meet all primary game requirements.                                                                                                                       | ```Completed```                                                             |
| There must be a polished UI for saving and loading.                                                                                                                      ||
| Save files must be written in XML format. This will help organize your saves, and also java has code for parsing these files.                                            ||
| The player must be able to fire projectiles that travel instantly using raycasting. Projectiles must apply an impulse to whatever they hit in the direction of that ray. ||
| Your game must meet at least two of the extra game requirements.                                                                                                         | ``` The two extra game requirements I have implemented are listed below.``` |
|||
|||

--------------------------------------------------------------

## Instructions on How to Run:
1) Run the main function in nin/Main.
2) Wait till the game screen pops up.
3) Click the start button on the title screen.
4) After reading the instructions, click the "X" to close the instructions screen.
5) Click the level you want to play
6) Play the game!
7) Use the Arrow keys to move and jump. Clicking Q will cause the game to restart by going back to the level selection screen. Clicking K will cause the player to appear back at the start.
8) The goal is to click the button then reach the exit!

## Levels Peculiarities
- 00: Used to test gravity.
- 01: Used to test x movement.
- 02: Used to test x movement and gravity.
- 03: Used to test jump.
- 04: Used to test jump.
- 05: Used to test restitution.
- 06: Used to test restitution of multiple objects colliding.
- 07: Used to test jumping on box.
- 08: Used to test jumping on box.
- 09: Used to test polygon.
- 10: Used to test polygon.
- 11: Used to test sliding and polygons.

## Known bugs: 
- None

## Notes
- My polygons require the points to be in counterclockwise order.

## Hours spent on assignment: 
- 30