# NIN1 Handin README

## Banner ID: 
- B01601657

## GitHub Repo:
- https://github.com/BrownCSCI1950N/2d-game-engines-2022-stencil-sahilbansal2701

## Version Number:
- Nin 1.0

## Demo Uploaded to Slack: 
- Yes

--------------------------------------------------------------
## Primary Requirements:
| Requirement                                                                                                                    | Location in code or steps to view in game                                                                                                                 |
|--------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------|
| Your handin must meet all global requirements.                                                                                 | ```Completed```                                                                                                                                           |
| Your handin only crashes under exceptional circumstances (edge cases).                                                         | ```Completed```                                                                                                                                           |
| Your engine must contain a physics behavior that correctly holds and updates mass, force, impulse, velocity, and acceleration. | ```Look in engine/Components/PhysicsComponent```                                                                                                          |
| Your engine must handle static objects and restitution.                                                                        | ```Look in engine/Components/CollisionComponent to see how static objects and restitution and dealt with. Also look in engine/Systems/CollisionSystem.``` |
| Your game must contain a player-controller unit above an immobile platform.                                                    | ```Completed```                                                                                                                                           |
| The player-controller unit must fall when in the air.                                                                          | ```Completed```                                                                                                                                           |
| The player-controller unit must not fall through the platform.                                                                 | ```Completed```                                                                                                                                           |
| The player-controller unit must have a constant downward acceleration.                                                         | ```Completed```                                                                                                                                           |
| The player-controller unit must be able to jump, but only when standing on top of a platform.                                  | ```Completed```                                                                                                                                           |
| Your game must have three objects with visibly different restitution values that can collide with each other.                  | ```The (player + wall), box and halfbox all have different restitution values, as seen in nin/Constants/ConstantsGameValues.```                           |
| Your game never crashes.                                                                                                       | ```Completed```                                                                                                                                           |

## Secondary Requirements:
| Requirement                                                                                                                           | Location in code or steps to view in game                                                  |
|---------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------|
| Your engine must meet all primary engine requirements.                                                                                | ```Completed```                                                                            |
| Your engine must support collisions/MTVs with convex polygons.                                                                        | ```Look in engine/Shape/Polygon to see the collision and MTV code relating to Polygons.``` |
| Your game must meet all primary game requirements.                                                                                    | ```Completed```                                                                            |
| The TA debugger must be extended to show collisions and MTVs with convex polygons.                                                    | ```Completed```                                                                            |
| Your game must include a convex polygon.                                                                                              | ```Levels 09, 10 and 11 include triangle corner walls that can be interacted with.```      |
| Think about fun mechanics you can add to your platformer – allow the player to double jump, slide down walls, jump off of walls, etc. | ```(OPTIONAL)```                                                                           |
| Add friction (resistance to motion perpendicular to the MTV).                                                                         | ```(OPTIONAL)```                                                                           |

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