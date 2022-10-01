# ALC2 Handin README

## Banner ID: 
- B01601657

## GitHub Repo:
- https://github.com/BrownCSCI1950N/2d-game-engines-2022-stencil-sahilbansal2701

## Version Number:
- Alc 2.0

## Demo Uploaded to Slack: 
- Yes

--------------------------------------------------------------
## Primary Requirements:
| Requirement                                                                                                                                                                 | Location in code or steps to view in game                                                                                                                                                                                                                                                                                                |
|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Your handin must meet all global requirements.                                                                                                                              | ```Completed```                                                                                                                                                                                                                                                                                                                          |
| Your handin only crashes under exceptional circumstances (edge cases).                                                                                                      | ```Completed```                                                                                                                                                                                                                                                                                                                          |
| Your engine must support collision detection between points, circles, and AABs. This includes a collision system and collision behaviors.                                   | ```Look at engine/Shape to see AAB and Circle classes which hold the code to see if the shapes are overlapping. Look at engine/Systems/CollisionSystem to see more code related to collisions. Look at engine/Components/CollisionComponent to see a function called onCollide that can be overridden to hold the collision behavior.``` |
| Your game should display at least 2 “units” in your viewport.                                                                                                               | <code>1. Run the game.</code><br/><code>2. Click the start button</code><br/><code>3. Click and drag on an element name on the menu on the left, to the right, to create a unit.</code><br/><code>4. Repeat step 3 to see two units in the viewport.</code>                                                                              |
| The player should be able to move the “units” around the screen.                                                                                                            | <code>1. Run the game.</code><br/><code>2. Click the start button</code><br/><code>3. Click and drag on an element name on the menu on the left, to the right, to create a unit.</code><br/><code>4. Click and drag the unit wherever you wish.</code>                                                                                   |
| The “units” should have sprites.                                                                                                                                            | <code>1. Run the game.</code><br/><code>2. Click the start button</code><br/><code>3. Click and drag on an element name on the menu on the left, to the right, to create a unit.</code><br/><code>4. The units will have an image/sprite that corresponds to the element it is.</code>                                                   |
| You must have at least 2 base “units”/elements, which can be combined to form a new element.                                                                                | <code>1. Run the game.</code><br/><code>2. Click the start button</code><br/><code>3. The menu on the right has 4 base elements that can be combined to create new elements.</code>                                                                                                                                                      |
| You must be able to add elements to the work-space by dragging them from the menu.                                                                                          | <code>1. Run the game.</code><br/><code>2. Click the start button</code><br/><code>3. Click and drag on an element name on the menu on the left, to the right, to create a unit.</code>                                                                                                                                                  |
| Your game must complete the debugger. Using the debugger, the TAs should quickly be able to verify collisions between any pair of the following: points, circles, and AABs. | ```Completed```                                                                                                                                                                                                                                                                                                                          |

## Secondary Requirements:
| Requirement                                                                                                                                                                                                                                                                        | Location in code or steps to view in game                                                                                                                                                                                                                             |
|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Your engine must meet all primary engine requirements.                                                                                                                                                                                                                             | ```Completed```                                                                                                                                                                                                                                                       |
| Your engine must include a sprite component.                                                                                                                                                                                                                                       | ```Look in engine/Components/SpriteComponent to see the sprite component```                                                                                                                                                                                           |
| Sprites should only be loaded once.                                                                                                                                                                                                                                                | ```Look in alc/AlcGame to see that we call new Image for each type of image only once, and then pass them to a resouce class that is then queried each time we need a sprite. (Done in makeElement)```                                                                |
| Your game must meet all primary game requirements.                                                                                                                                                                                                                                 | ```Completed```                                                                                                                                                                                                                                                       |
| You must be able to remove elements from the work-space by dragging them to the menu or some sort of “trash” area.                                                                                                                                                                 | <code>1. Run the game.</code><br/><code>2. Click the start button</code><br/><code>3. CLick and drag an element off the menu on the right onto the left.</code><br/><code>4. Drag the element to the stop sign on the top left to remove it from the viewport.</code> |
| You must have at least 4 base “units”/elements and at least 3 “units”/elements the player can make through combinations, one of which must be made by combining two non-base “units”/elements, and one of which must be a final “unit”/element (cannot be combined with anything). | <code>Base Units: AIR, WATER, FIRE, EARTH</code><br/><code>Some Non-Base Units: STEAM, LAVA, PRESSURE, ENERGY, MUD</code><br/><code>Non-Base Unit Made of Non-Base Units: GUNPOWDER, GRANITE</code><br/><code>Final Units: GRANITE, OBSIDIAN, ERUPTION</code>         |

--------------------------------------------------------------

## Instructions on How to Run:
1) Run the main function in alc/Main.
2) Wait till the game screen pops up.
3) Click the start button on the title screen.
4) You will be brought to the game screen.
5) There is a menu on the right with element names.
6) You can click over an element name in the menu and drag an element of that type into view in the viewport on the left.
7) You can use the WASD keys to pan the viewport
8) You can use the ZX keys to zoom in and out, respectively.
9) Clicking the escape button at any moment causes the game to exit.
10) Dragging an element onto the stop sign on the top left removes the game object from the viewport
11) Use the up and down keys to shift the element menu up and down

## Note
- Combinations and Sprites are taken from little alchemy 2.

## Known bugs: 
- None

## Hours spent on assignment: 
- 15