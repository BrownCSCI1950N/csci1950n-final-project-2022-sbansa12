package wiz;

import engine.*;
import engine.Components.*;
import engine.Shape.AAB;
import engine.Systems.CollisionSystem;
import engine.Systems.KeyProcessSystem;
import engine.Systems.TickSystem;
import engine.TerrainGeneration.SpacePartitioning.SpacePartitioning;
import engine.TerrainGeneration.Terrain;
import engine.TerrainGeneration.TerrainReader.TerrainReader;
import engine.TerrainGeneration.TileType;
import engine.UI.Viewport;
import engine.support.Vec2d;
import engine.support.Vec2i;
import javafx.scene.image.Image;
import wiz.Screens.GameScreen;
import wiz.StateMachineBoss.MoveStateBoss;
import wiz.StateMachinePlayer.IdleStateRightPlayer;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class WizGame {

    private static Integer zIndex = 1;
    public static final Resource<Sprite> images = new Resource<>();
    Integer initialSeed = 0;
    Viewport viewport;
    GameScreen gameScreen;
    static GameWorld currentGameWorld;
    Random rand;
    List<GameObject> secretHidden = new LinkedList<>();

    boolean secretLevel = false;

    Integer deathCount = 0;

    Boolean popUp = false;
    Boolean bossFight = false;

    public WizGame() {
        // Setup Resources
        images.putResource("PLAYER", new Sprite(new Image("file:.\\src\\main\\java\\wiz\\Images\\PLAYER.png"), new Vec2d(2,9)));
        images.putResource("PROJECTILE_PLAYER", new Sprite(new Image("file:.\\src\\main\\java\\wiz\\Images\\PROJECTILE_PLAYER.png"), new Vec2d(1,1)));
        images.putResource("WALL", new Sprite(new Image("file:.\\src\\main\\java\\wiz\\Images\\WALL.png"), new Vec2d(1,1)));
        images.putResource("ROOM", new Sprite(new Image("file:.\\src\\main\\java\\wiz\\Images\\ROOM.png"), new Vec2d(2,1)));
        images.putResource("SPAWN", new Sprite(new Image("file:.\\src\\main\\java\\wiz\\Images\\SPAWN.png"), new Vec2d(1,1)));
        images.putResource("EXIT", new Sprite(new Image("file:.\\src\\main\\java\\wiz\\Images\\EXIT.png"), new Vec2d(1,1)));
        images.putResource("TRAPS", new Sprite(new Image("file:.\\src\\main\\java\\wiz\\Images\\TRAPS.png"), new Vec2d(4,1)));
        images.putResource("STAIRS", new Sprite(new Image("file:.\\src\\main\\java\\wiz\\Images\\STAIRS.png"), new Vec2d(1,1)));
        images.putResource("ENEMY", new Sprite(new Image("file:.\\src\\main\\java\\wiz\\Images\\ENEMY.png"), new Vec2d(2,3)));
        images.putResource("BOSS", new Sprite(new Image("file:.\\src\\main\\java\\wiz\\Images\\BOSS.png"), new Vec2d(3,1)));
        images.putResource("PROJECTILE_BOSS", new Sprite(new Image("file:.\\src\\main\\java\\wiz\\Images\\PROJECTILE_BOSS.png"), new Vec2d(1,1)));
    }

    public Integer getDeathCount() {
        return deathCount;
    }

    public Integer getSeed() {
        return this.initialSeed;
    }
    public void setSeed(Integer seed) {
        this.initialSeed = seed;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    public void setGameScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    private static int getZIndex() {
        int toReturn = zIndex;
        zIndex += 1;
        return toReturn;
    }

    public void startGame() {
        deathCount = 0;
        this.rand = new Random(initialSeed);
        currentGameWorld = generateLevel(rand, true);
//        currentGameWorld = generateSecretLevel(rand);
        viewport.setGameWorld(currentGameWorld);
    }

    Random randomGlobal;

    private GameWorld generateLevel(Random rand, boolean stairs) {
        randomGlobal = rand;
        // Use Space Partition To Generate Level Map
        Terrain terrain = new SpacePartitioning(rand).createTerrain(Constants.mapSize, Constants.minRoomSize, Constants.probabilitySplitRoom, Constants.maxHallWidth);
        if (stairs) {
            terrain.placeSingleTileRandom(TileType.STAIRS, List.of(TileType.ROOM));
        }
        terrain.placeTileRandomlyRooms(TileType.ENEMY, List.of(TileType.ROOM), 0.4F, 0.07F, -1);
        terrain.placeTileRandomlyRoomsCenter(TileType.TRAPS, List.of(TileType.ROOM),0.4F, 0.05F, -1);
        TileType[][] map = terrain.getTileMap();

        // Create Game World
        GameWorld level = createGameWorld(map);

        // Put in Special Tiles
        GameObject spawnPoint = populateGameWorldSpecialTiles(level, terrain.getSpecialTiles());

        // Create Player Game Object
        assert spawnPoint != null;
        GameObject player = createPlayer(spawnPoint);

        level.addGameObject(player);

        return level;
    }

    private GameObject populateGameWorldSpecialTiles(GameWorld level, Map<TileType, List<Vec2i>> specialTiles) {
        GameObject spawnPoint = null;
        for (TileType t: specialTiles.keySet()) {
            for (Vec2i position: specialTiles.get(t)) {
                GameObject g = tileToGameObject(t, position.x, position.y);
                level.addGameObject(g);

                assert g != null;
                if (((TileComponent) g.getComponent("tile")).getTileType().equals(TileType.SPAWN)) {
                    spawnPoint = g;
                }
            }
        }

        return spawnPoint;
    }

    private GameWorld createGameWorld(TileType[][] map) {
        GameWorld level = new GameWorld();

        // Add Appropriate Systems
        CollisionSystem collisionSystem = new CollisionSystem(level, List.of("collision", "damage", "health"), true);
        collisionSystem.setLayersCollide(Constants.layersCollide);
        collisionSystem.addTagsCollide("health", "damage");
        level.prependSystem(collisionSystem);
        level.prependSystem(new KeyProcessSystem(level, List.of("moveKeys", "actionKeys")));
        level.prependSystem(new TickSystem(level, List.of("center", "constantMovement", "randomMovement", "timerAction", "sprite", "stateMachine")));

        // Add Level Map Game Objects
        for (int j = 0; j < map.length; j++) {
            for (int i = 0; i < map[j].length; i++) {
                GameObject gameObject = tileToGameObject(map[j][i], i, j);
                level.addGameObject(gameObject);
            }
        }

        return level;
    }

    private GameObject tileToGameObject(TileType t, int x, int y) {
        TransformComponent transformComponent = new TransformComponent(new Vec2d(Constants.tileSize.x * x, Constants.tileSize.y * y), Constants.tileSize);

        if (t == TileType.ROOM) {
            GameObject gameObject = new GameObject(transformComponent, getZIndex());
            gameObject.addComponent(new SpriteComponent(gameObject, images.getResource(TileType.ROOM.name()), new Vec2d(rand.nextInt(2),0)));
            gameObject.addComponent(new TileComponent(TileType.ROOM));
            return gameObject;
        } else if (t == TileType.WALL) {
            GameObject gameObject = new GameObject(transformComponent, getZIndex());
            gameObject.setName("WALL");
            gameObject.addComponent(new SpriteComponent(gameObject, images.getResource(TileType.WALL.name()), new Vec2d(0,0)));
            gameObject.addComponent(new TileComponent(TileType.WALL));
            gameObject.addComponent(new CollisionComponent(gameObject, new AAB(transformComponent.getCurrentGameSpacePosition(), transformComponent.getSize()), Constants.mapCollisionLayer, true));
            return gameObject;
        } else if (t == TileType.SPAWN) {
            GameObject gameObject = new GameObject(transformComponent, getZIndex());
            gameObject.setName("SPAWN");
            gameObject.addComponent(new SpriteComponent(gameObject, images.getResource(TileType.SPAWN.name()), new Vec2d(0,0)));
            gameObject.addComponent(new TileComponent(TileType.SPAWN));
            return gameObject;
        } else if (t == TileType.EXIT) {
            GameObject gameObject = new GameObject(transformComponent, getZIndex());
            gameObject.setName("EXIT");
            gameObject.addComponent(new SpriteComponent(gameObject, images.getResource(TileType.EXIT.name()), new Vec2d(0,0)));
            gameObject.addComponent(new TileComponent(TileType.EXIT));
            gameObject.addComponent(new CollisionComponent(gameObject, new AAB(transformComponent.getCurrentGameSpacePosition(), transformComponent.getSize()), Constants.mapCollisionLayer, false){
                @Override
                public void onCollide(Collision collision) {
                    if (collision.getCollidedObject().hasComponentTag("player")) {
                        if (secretLevel) {
                            gameScreen.setActiveScreen("win");
                        } else {
                            gameScreen.setActiveScreen("tryAgain");
                        }
                    }
                }
            });
            return gameObject;
        } else if (t == TileType.TRAPS) {
            GameObject gameObject = new GameObject(transformComponent, getZIndex());
            gameObject.setName("TRAP");
            gameObject.addComponent(new SpriteComponent(gameObject, images.getResource(TileType.TRAPS.name()), new Vec2d(rand.nextInt(4),0)));
            gameObject.addComponent(new TileComponent(TileType.TRAPS));
            gameObject.addComponent(new DamageComponent(gameObject, new AAB(transformComponent.getCurrentGameSpacePosition(), transformComponent.getSize()), Constants.mapCollisionLayer, Constants.trapsDamage, false));
            return gameObject;
        } else if (t == TileType.ENEMY) {
            GameObject gameObject = new GameObject(transformComponent, getZIndex());
            gameObject.setName("ENEMY");
            int randColor = rand.nextInt(3);
            gameObject.addComponent(new ConstantAnimationComponent(gameObject, images.getResource(TileType.ENEMY.name()), List.of(new Vec2d(0,randColor), new Vec2d(1, randColor)), Constants.enemyMoveTime));
            gameObject.addComponent(new TileComponent(TileType.ENEMY));
            gameObject.addComponent(new DamageComponent(gameObject, new AAB(transformComponent.getCurrentGameSpacePosition(), transformComponent.getSize()), Constants.mapCollisionLayer, Constants.trapsDamage, false));
            gameObject.addComponent(new HealthComponent(gameObject, new AAB(transformComponent.getCurrentGameSpacePosition(), transformComponent.getSize()), Constants.mapCollisionLayer, Constants.trapsMaxHealth){
                @Override
                public void zeroHealthScript() {
                    currentGameWorld.removeGameObject(this.gameObject);
                }
            });
            return gameObject;
        } else if (t == TileType.STAIRS) {
            GameObject gameObject = new GameObject(transformComponent, getZIndex());
            gameObject.setName("STAIRS");
            gameObject.addComponent(new SpriteComponent(gameObject, images.getResource(TileType.STAIRS.name()), new Vec2d(0,0)));
            gameObject.addComponent(new TileComponent(TileType.STAIRS));
            gameObject.addComponent(new CollisionComponent(gameObject, new AAB(transformComponent.getCurrentGameSpacePosition(), transformComponent.getSize()), Constants.mapCollisionLayer, false){
                @Override
                public void onCollide(Collision collision) {
                    if (collision.getCollidedObject().hasComponentTag("player")) {
                        setBossFight(true);
                        currentGameWorld = generateSecretLevel(rand);
                        viewport.setGameWorld(currentGameWorld);
                    }
                }
            });
            return gameObject;
        } else if (t == TileType.BOSS) {
            GameObject gameObject = new GameObject(transformComponent, getZIndex());
            gameObject.setName("BOSS");
            gameObject.addComponent(new RandomMovementComponent(gameObject, rand, Constants.bossMoveTime));
            gameObject.addComponent(new TileComponent(TileType.BOSS));
            gameObject.addComponent(new DamageComponent(gameObject, new AAB(transformComponent.getCurrentGameSpacePosition(), transformComponent.getSize()), Constants.moveEnemyCollisionLayer, Constants.trapsDamage, false));
            gameObject.addComponent(new HealthComponent(gameObject, new AAB(transformComponent.getCurrentGameSpacePosition(), transformComponent.getSize()), Constants.moveEnemyCollisionLayer, Constants.bossMaxHealth){
                @Override
                public void zeroHealthScript() {
                    currentGameWorld.removeGameObject(this.gameObject);
                    for (GameObject g: secretHidden) {
                        currentGameWorld.removeGameObject(g);
                    }
                }
            });
            gameObject.addComponent(new CollisionComponent(gameObject, new AAB(transformComponent.getCurrentGameSpacePosition(), transformComponent.getSize()), Constants.moveEnemyCollisionLayer, false){
                @Override
                public void onCollide(Collision collision) {

                    CollisionComponent otherObjectCollisionComponent = (CollisionComponent) collision.getCollidedObject().getComponent("collision");

                    // Collision with Static Object
                    if (otherObjectCollisionComponent.isStaticObject()) {
                        // Ignore Certain Static Objects As Can Go Through Them
                        TransformComponent currentPosition = this.gameObject.getTransform();
                        this.gameObject.getTransform().setCurrentGameSpacePositionNoVelocity(currentPosition.getCurrentGameSpacePosition().plus(collision.getMTV()));
                    }
                }
            });

            StateMachineComponent sm = new StateMachineComponent();
            sm.setCurrentState(new MoveStateBoss(sm, gameObject));
            gameObject.addComponent(sm);

            return gameObject;
        } else if (t == TileType.HIDDEN) {
            GameObject gameObject = new GameObject(transformComponent, getZIndex());
            gameObject.setName("HIDDEN");
            gameObject.addComponent(new SpriteComponent(gameObject, images.getResource(TileType.WALL.name()), new Vec2d(0,0)));
            gameObject.addComponent(new TileComponent(TileType.HIDDEN));
            gameObject.addComponent(new CollisionComponent(gameObject, new AAB(transformComponent.getCurrentGameSpacePosition(), transformComponent.getSize()), Constants.mapCollisionLayer, true));
            secretHidden.add(gameObject);
            return gameObject;
        } else {
            assert false;
            return null;
        }
    }

    private GameWorld generateSecretLevel(Random rand) {
        secretLevel = true;
        randomGlobal = rand;
        try {
            Terrain map = new TerrainReader().createTerrain(".\\src\\main\\java\\wiz\\Maps\\boss-room.txt", rand);

            // Create Game World
            GameWorld level = createGameWorld(map.getTileMap());

            // Put in Special Tiles
            GameObject spawnPoint = populateGameWorldSpecialTiles(level, map.getSpecialTiles());

            // Create Player Game Object
            assert spawnPoint != null;
            GameObject player = createPlayer(spawnPoint);

            level.addGameObject(player);

            return level;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            rand = new Random(initialSeed + 10);
            randomGlobal = rand;
            return generateLevel(rand, false);
        }
    }


    private GameObject createPlayer(GameObject spawnPoint) {
        Vec2d respawnLocation = spawnPoint.getTransform().getCurrentGameSpacePosition().plus(Constants.spawnPointAdd, Constants.spawnPointAdd);
        TransformComponent transformComponentPlayer = new TransformComponent(respawnLocation, Constants.playerSize);
        GameObject player = new GameObject(transformComponentPlayer, getZIndex());
        player.setName("PLAYER");
        player.addComponent(new PlayerComponent());
        player.addComponent(new TileComponent(TileType.PLAYER));

        StateMachineComponent sm = new StateMachineComponent();
        sm.setCurrentState(new IdleStateRightPlayer(sm, player));
        player.addComponent(sm);

        player.addComponent(new MoveKeysComponent(player, Constants.movementKeys, Constants.movementSpeed));
        player.addComponent(new RespawnComponent(player, respawnLocation));
        viewport.setCurrentGamePointCenter(respawnLocation);
        player.addComponent(new CenterComponent(player, viewport));
        player.addComponent(new HealthComponent(player, new AAB(transformComponentPlayer.getCurrentGameSpacePosition(), transformComponentPlayer.getSize()), Constants.playerCollisionLayer, Constants.playerMaxHealth){
            @Override
            public void zeroHealthScript() {
                deathCount += 1;
                RespawnComponent respawnComponent = (RespawnComponent) player.getComponent("respawn");
                respawnComponent.script();
            }
        });
        player.addComponent(new CollisionComponent(player, new AAB(transformComponentPlayer.getCurrentGameSpacePosition(), transformComponentPlayer.getSize()), Constants.playerCollisionLayer, false){
            @Override
            public void onCollide(Collision collision) {

                CollisionComponent otherObjectCollisionComponent = (CollisionComponent) collision.getCollidedObject().getComponent("collision");

                // Collision with Static Object
                if (otherObjectCollisionComponent.isStaticObject()) {
                    TransformComponent currentPosition = this.gameObject.getTransform();
                    this.gameObject.getTransform().setCurrentGameSpacePositionNoVelocity(currentPosition.getCurrentGameSpacePosition().plus(collision.getMTV()));
                }
            }
        });
        player.addComponent(new ActionKeysComponent(player, Constants.projectileKey, true){

            @Override
            public void action() {
                createProjectile(player);
            }
        });

        return player;
    }

    public static Direction directionProjectileShoot(Vec2d velocity) {
        if (Math.abs(velocity.x) > Math.abs(velocity.y)) {
            if (velocity.x < 0) {
                return Direction.LEFT;
            } else if (velocity.x > 0) {
                return Direction.RIGHT;
            } else {
                assert false;
                return Direction.NONE;
            }
        } else if (Math.abs(velocity.x) < Math.abs(velocity.y)) {
            if (velocity.y < 0) {
                return Direction.UP;
            } else if (velocity.y > 0) {
                return Direction.DOWN;
            } else {
                assert false;
                return Direction.NONE;
            }
        } else {
            // x == y
            assert velocity.isZero();
            return Direction.DOWN;
        }
    }

    public static void createProjectile(GameObject shooter) {
        Vec2d velocityShooter = shooter.getTransform().getVelocity();

        Direction projectileDirection = directionProjectileShoot(velocityShooter);

        Vec2d initialPosition = null;
        Vec2d velocityProjectile = null;


        if (projectileDirection ==  Direction.LEFT) {
            if (shooter.hasComponentTag("player")) {
                initialPosition = shooter.getTransform().getCurrentGameSpacePosition().minus(new Vec2d(Constants.projectileSize.x, (Constants.projectileSize.y-Constants.playerSize.y)/2));
                velocityProjectile = new Vec2d(-Constants.projectileSpeedPlayer, 0);
            } else {
                initialPosition = shooter.getTransform().getCurrentGameSpacePosition().minus(new Vec2d(Constants.projectileSize.x, (Constants.projectileSize.y-Constants.tileSize.y)/2));
                velocityProjectile = new Vec2d(-Constants.projectileSpeedEnemy, 0);
            }
        } else if (projectileDirection ==  Direction.RIGHT) {
            if (shooter.hasComponentTag("player")) {
                initialPosition = shooter.getTransform().getCurrentGameSpacePosition().minus(new Vec2d(-Constants.playerSize.x, (Constants.projectileSize.y-Constants.playerSize.y)/2));
                velocityProjectile = new Vec2d(Constants.projectileSpeedPlayer, 0);
            } else {
                initialPosition = shooter.getTransform().getCurrentGameSpacePosition().minus(new Vec2d(-Constants.tileSize.x, (Constants.projectileSize.y-Constants.tileSize.y)/2));
                velocityProjectile = new Vec2d(Constants.projectileSpeedEnemy, 0);
            }
        } else if (projectileDirection ==  Direction.UP) {
            if (shooter.hasComponentTag("player")) {
                initialPosition = shooter.getTransform().getCurrentGameSpacePosition().minus(new Vec2d( (Constants.projectileSize.x-Constants.playerSize.x)/2, Constants.projectileSize.y));
                velocityProjectile = new Vec2d(0,-Constants.projectileSpeedPlayer);
            } else {
                initialPosition = shooter.getTransform().getCurrentGameSpacePosition().minus(new Vec2d( (Constants.projectileSize.x-Constants.tileSize.x)/2, Constants.projectileSize.y));
                velocityProjectile = new Vec2d(0,-Constants.projectileSpeedEnemy);
            }
        } else if (projectileDirection ==  Direction.DOWN) {
            if (shooter.hasComponentTag("player")) {
                initialPosition = shooter.getTransform().getCurrentGameSpacePosition().minus(new Vec2d( (Constants.projectileSize.x-Constants.playerSize.x)/2, -Constants.playerSize.y));
                velocityProjectile = new Vec2d(0, Constants.projectileSpeedPlayer);
            } else {
                initialPosition = shooter.getTransform().getCurrentGameSpacePosition().minus(new Vec2d( (Constants.projectileSize.x-Constants.tileSize.x)/2, -Constants.tileSize.y));
                velocityProjectile = new Vec2d(0, Constants.projectileSpeedEnemy);
            }
        }

        TransformComponent transformComponentProjectile = new TransformComponent(initialPosition, Constants.projectileSize);
        GameObject projectile = new GameObject(transformComponentProjectile, getZIndex());
        Integer collisionLayer;
        if (shooter.hasComponentTag("player")) {
            projectile.setName("PROJECTILE_PLAYER");
            projectile.addComponent(new SpriteComponent(projectile, images.getResource("PROJECTILE_PLAYER"), new Vec2d(0,0)));
            collisionLayer = Constants.projectilesPlayerCollisionLayer;
        } else {
            projectile.setName("PROJECTILE_BOSS");
            projectile.addComponent(new SpriteComponent(projectile, images.getResource("PROJECTILE_BOSS"), new Vec2d(0,0)));
            collisionLayer = Constants.projectilesEnemyCollisionLayer;
        }
        projectile.addComponent(new ConstantMovementComponent(projectile, velocityProjectile));
        projectile.addComponent(new CollisionComponent(projectile, new AAB(transformComponentProjectile.getCurrentGameSpacePosition(), transformComponentProjectile.getSize()), collisionLayer, false){
            @Override
            public void onCollide(Collision collision) {

                CollisionComponent otherObjectCollisionComponent = (CollisionComponent) collision.getCollidedObject().getComponent("collision");


                // Collision with Static Object
                if (otherObjectCollisionComponent.isStaticObject()) {
                    currentGameWorld.removeGameObject(projectile);
                }
            }
        });
        projectile.addComponent(new DamageComponent(projectile, new AAB(transformComponentProjectile.getCurrentGameSpacePosition(), transformComponentProjectile.getSize()), collisionLayer, Constants.projectileDamage){
            @Override
            public void onCollide(Collision collision) {
                super.onCollide(collision);
                currentGameWorld.removeGameObject(projectile);
            }
        });
        projectile.addComponent(new TimerActionComponent(Constants.projectileLifeTime){
            @Override
            public void script() {
                currentGameWorld.removeGameObject(projectile);
            }
        });

        currentGameWorld.addGameObject(projectile);
        ((CollisionSystem) currentGameWorld.getSystem("collision")).checkCollision((CollisionComponent) projectile.getComponent("collision"), Constants.mapCollisionLayer);
    }

    public Boolean getPopUp() {
        return popUp;
    }
    public void setPopUp(Boolean newPopUp) {
        this.popUp = newPopUp;
    }

    public Boolean getBossFight() {
        return bossFight;
    }
    public void setBossFight(Boolean newBossFight) {
        this.bossFight = newBossFight;
    }
}
