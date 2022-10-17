package wiz.StateMachinePlayer;

import engine.Components.*;
import engine.Direction;
import engine.GameObject;
import engine.TerrainGeneration.TileType;
import engine.support.Vec2d;
import wiz.Constants;
import wiz.WizGame;

import java.math.BigDecimal;

public class ShootStateLeftDownPlayer extends State {

    StateMachineComponent sm;
    GameObject gameObject;
    SpriteComponent animation;

    public ShootStateLeftDownPlayer(StateMachineComponent sm, GameObject gameObject) {
        this.sm = sm;
        this.gameObject = gameObject;
        this.animation = new SpriteComponent(gameObject, WizGame.images.getResource(TileType.PLAYER.name()), new Vec2d(1,7));
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
            return;
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
