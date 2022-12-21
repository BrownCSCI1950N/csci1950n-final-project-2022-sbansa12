package wiz.StateMachinePlayer;

import engine.Components.*;
import engine.Direction;
import engine.GameObject;
import engine.TerrainGeneration.TileType;
import engine.support.Vec2d;
import wiz.WizGame;

public class ShootStateLeftPlayer extends State {

    StateMachineComponent sm;
    GameObject gameObject;
    SpriteComponent animation;

    public ShootStateLeftPlayer(StateMachineComponent sm, GameObject gameObject) {
        this.sm = sm;
        this.gameObject = gameObject;
        this.animation = new SpriteComponent(gameObject, WizGame.images.getResource(TileType.PLAYER1.name()), new Vec2d(1,6));
    }

    @Override
    public void onStart() {
        gameObject.addComponent(animation);
//        createProjectile(gameObject);
    }

    @Override
    public void onTick(long nanosSincePreviousTick) {
    }


    @Override
    public void onSwitch() {
        ActionKeysComponent c1 = (ActionKeysComponent) gameObject.getComponent("actionKeys");

        if (!c1.isOnceHappened()) {
            sm.setCurrentState(new IdleStateLeftPlayer(sm, gameObject));
        }

        MoveKeysComponent c2 = (MoveKeysComponent) gameObject.getComponent("moveKeys");

        if (c2.getMoveDirection().equals(Direction.NONE)) {
            return;
        } else if (c2.getMoveDirection().equals(Direction.UP)) {
            sm.setCurrentState(new IdleStateUpPlayer(sm, gameObject));
        } else if (c2.getMoveDirection().equals(Direction.DOWN)) {
            sm.setCurrentState(new IdleStateLeftPlayer(sm, gameObject));
        } else if (c2.getMoveDirection().equals(Direction.LEFT)) {
            sm.setCurrentState(new MoveStateLeftPlayer(sm, gameObject));
        } else if (c2.getMoveDirection().equals(Direction.RIGHT)) {
            sm.setCurrentState(new MoveStateRightPlayer(sm, gameObject));
        }
    }

    @Override
    public void onEnd() {
        gameObject.removeComponent(animation);
    }
}
