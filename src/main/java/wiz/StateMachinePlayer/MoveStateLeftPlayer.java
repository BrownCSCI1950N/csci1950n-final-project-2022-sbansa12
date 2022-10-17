package wiz.StateMachinePlayer;

import engine.Components.*;
import engine.Direction;
import engine.GameObject;
import engine.TerrainGeneration.TileType;
import engine.support.Vec2d;
import wiz.Constants;
import wiz.WizGame;

import java.util.List;

public class MoveStateLeftPlayer extends State {
    StateMachineComponent sm;
    GameObject gameObject;
    ConstantAnimationComponent animation;

    public MoveStateLeftPlayer(StateMachineComponent sm, GameObject gameObject) {
        this.sm = sm;
        this.gameObject = gameObject;
        this.animation = new ConstantAnimationComponent(gameObject, WizGame.images.getResource(TileType.PLAYER.name()), List.of(new Vec2d(1,4), new Vec2d(0,4)), Constants.playerMoveTime);
    }

    @Override
    public void onStart() {
        gameObject.addComponent(animation);
    }

    @Override
    public void onTick(long nanosSincePreviousTick) {

    }

    @Override
    public void onSwitch() {
        MoveKeysComponent c2 = (MoveKeysComponent) gameObject.getComponent("moveKeys");

        if (c2.getMoveDirection().equals(Direction.NONE)) {
            sm.setCurrentState(new IdleStateLeftPlayer(sm, gameObject));
        } else if (c2.getMoveDirection().equals(Direction.UP)) {
            sm.setCurrentState(new IdleStateUpPlayer(sm, gameObject));
        } else if (c2.getMoveDirection().equals(Direction.DOWN)) {
            sm.setCurrentState(new IdleStateLeftPlayer(sm, gameObject));
        } else if (c2.getMoveDirection().equals(Direction.LEFT)) {
            return;
        } else if (c2.getMoveDirection().equals(Direction.RIGHT)) {
            sm.setCurrentState(new MoveStateRightPlayer(sm, gameObject));
        }

        ActionKeysComponent c1 = (ActionKeysComponent) gameObject.getComponent("actionKeys");

        if (c1.isOnceHappened()) {
            Direction directionShoot = WizGame.directionProjectileShoot(gameObject.getTransform().getVelocity());
            if (directionShoot == Direction.UP) {
                sm.setCurrentState(new IdleStateUpPlayer(sm, gameObject));
            } else if (directionShoot == Direction.LEFT) {
                sm.setCurrentState(new ShootStateLeftPlayer(sm, gameObject));
            } else if (directionShoot == Direction.RIGHT) {
                sm.setCurrentState(new ShootStateRightPlayer(sm, gameObject));
            } else if (directionShoot == Direction.DOWN) {
                sm.setCurrentState(new IdleStateLeftPlayer(sm, gameObject));
            } else {
                assert false;
            }
        }
    }

    @Override
    public void onEnd() {
        gameObject.removeComponent(animation);
    }
}
