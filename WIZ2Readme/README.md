# WIZ2 Handin README

## Banner ID: 
- B01601657

## GitHub Repo:
- https://github.com/BrownCSCI1950N/2d-game-engines-2022-stencil-sahilbansal2701

## Version Number:
- Wiz 2.0

## Demo Uploaded to Slack: 
- Yes

--------------------------------------------------------------
## Primary Requirements:
| Requirement                                                                                                                                                                                                                                                                                                                                                                                                                                                                       | Location in code or steps to view in game                                                                                                                                                                                                                                                                                                                                                                                                                                        |
|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Your handin must meet all global requirements.                                                                                                                                                                                                                                                                                                                                                                                                                                    | ```Completed```                                                                                                                                                                                                                                                                                                                                                                                                                                                                  |
| Your handin only crashes under exceptional circumstances (edge cases).                                                                                                                                                                                                                                                                                                                                                                                                            | ```Completed```                                                                                                                                                                                                                                                                                                                                                                                                                                                                  |
| Your engine must implement an AI library using either behavior trees or goal-oriented action planning. (If using behavior trees: You must include a behavior tree, selector, and sequence class. You must also include a node and action/condition interface.) (If using GOAP: You must include a game state class, an action class/interface, and a condition class/interface. You must be able to search over an action set using a predefined start state and goal condition.) | ```I implemented behavior trees, and the code that relates to it can be found in engine/AI/DecisionTrees. A concrete usage of it can be found at the bottom of wiz/WizGame under createBoss().```                                                                                                                                                                                                                                                                                |
| Your engine must have an A* implementation of pathfinding.                                                                                                                                                                                                                                                                                                                                                                                                                        | ```Look in engine/AStar/AStar to find my implementation of A*. Look inside engine/Components/PathfindingMovementComponent to see a concrete use of the algorithm.```                                                                                                                                                                                                                                                                                                             |
| Your game should have a map containing passable and impassable tiles.                                                                                                                                                                                                                                                                                                                                                                                                             | ```The world has impassable dark green tiles representing walls, and passable light green tiles representing the background of the room.```                                                                                                                                                                                                                                                                                                                                      |
| Your game must have a unit that can be controlled by the player.                                                                                                                                                                                                                                                                                                                                                                                                                  | ```The game has a purple wizard whose movement can be controlled with WASD and who can shoot via SPACE.```                                                                                                                                                                                                                                                                                                                                                                       |
| Your game must have at least one enemy unit that moves around deterministically (i.e., the same actions by the player result in the same enemy behavior). There must be a visible reaction when the player and item collide.                                                                                                                                                                                                                                                      | ```The slimes that appear in all levels, except the boss level, move deterministically. When the player is within a certain range of the enemy, the enemy starts moving towards the player. When the enemy touches the player, the player dies and respawns at the start point.```                                                                                                                                                                                               |
| The player-controlled unit must not be able to leave the map.                                                                                                                                                                                                                                                                                                                                                                                                                     | ```Wall blocks line the outer edges of the map, and since they are impassable, the  player-controlled unit cannot leave the map.```                                                                                                                                                                                                                                                                                                                                              |
| The enemy unit should use your engine’s AI framework. All of the AI tools included in the engine requirements should be used when constructing your AI. All of the behaviors defined for your enemy should be visible at some point when playing the game.                                                                                                                                                                                                                        | ```Look in wiz/WizGame and see createBoss() and createEnemy(). Each of them construct a Behavior Tree (the boss one is more complex) that is then added to the game object via the BTComponent. The defined behaviors can be brought about by interacting with the enemies and boss. The enemies move towards the player, once the player is within a certain distance of the enemy. The boss has three main behaviors which are outlined in comments in the code, and below.``` |
| It must be possible to start a new game without restarting the program.                                                                                                                                                                                                                                                                                                                                                                                                           | ```While the game is running, if R is clicked the user is taken back to the seed/world generation screen to restart the game.```                                                                                                                                                                                                                                                                                                                                                 |

## Secondary Requirements:
| Requirement                                                                                        | Location in code or steps to view in game                                                                                                                                                                       |
|----------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Your engine must meet all primary engine requirements.                                             | ```Completed```                                                                                                                                                                                                 |
| Your game must meet all primary game requirements.                                                 | ```Completed```                                                                                                                                                                                                 |
| The enemy unit should move according to a path generated using A*.                                 | ```Look inside wiz/WizGame/MoveAction class to see the AStar algorithm being run to get a path that is then used to update the position of the game object. This class is used by the boss and enemy slimes.``` |
| Your game must meet at least two of the extra game requirements.                                   | ``` The two extra game requirements I have implemented are listed below.```                                                                                                                                     |
| Add a floating minimap UI element                                                                  | ```After running the game, on the bottom left there is a floating minimap UIElement. It shows the current position of the player, and enemies (after they have been discovered).```                             |
| Only show terrain on the minimap that was at some point within range of the player-controlled unit | ```Parts of the minimap are hidden until the player has discovered them, by being within a certain range of them.```                                                                                            |

--------------------------------------------------------------

- Enemy AI:
  - Root: Sequence:
    - Player in View: Condition
    - Move: Action
--------------------------------------------------------------
- Boss AI:
  - Root: Selector:
    - Heal: Sequence:
      - Health Below x%: Condition 
      - 5 Times: Wrapper:
        - RunHeal: Sequence:
          - Move Away: Action
          - Heal: Action
    - Attack: Random Selector
      - Shoot: Sequence:
        - Player in Range: Condition
        - Shoot: Action
      - Move: Sequence:
        - Player in View: Condition
        - Move: Action

--------------------------------------------------------------

## Instructions on How to Run:
1) Run the main function in wiz/Main.
2) Wait till the game screen pops up.
3) Click the start button on the title screen.
4) After reading the instructions, click the "X" to close the instructions screen.
5) Click the textbox and enter a seed number, or just click create new world. (2218 is a special number that leads directly to the boss room)
6) Play the game!
7) You can use WASD to move, and SPACE to shoot projectiles. Clicking R will cause the game to restart by going back to the world generation screen.
8) First goal is to find the blue portal
9) Then to defeat the boss
10) Then you can reach the true exit!

## Known bugs: 
- None

## Hours spent on assignment: 
- 24