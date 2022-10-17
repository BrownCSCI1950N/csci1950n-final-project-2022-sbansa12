package wiz.StateMachinePlayer;

import engine.Components.*;
import engine.Direction;
import engine.GameObject;
import engine.TerrainGeneration.TileType;
import engine.support.Vec2d;
import wiz.WizGame;

public class IdleStateRightPlayer extends State {
    StateMachineComponent sm;
    GameObject gameObject;
    SpriteComponent idle;

    public IdleStateRightPlayer(StateMachineComponent sm, GameObject gameObject) {
        this.sm = sm;
        this.gameObject = gameObject;
        this.idle = new SpriteComponent(gameObject, WizGame.images.getResource(TileType.PLAYER.name()), new Vec2d(0,1));
    }
    @Override
    public void onStart() {
        gameObject.addComponent(idle);
    }

    @Override
    public void onTick(long nanosSincePreviousTick) {

    }

    @Override
    public void onSwitch() {
        MoveKeysComponent c2 = (MoveKeysComponent) gameObject.getComponent("moveKeys");

        if (c2.getMoveDirection().equals(Direction.NONE)) {

        } else if (c2.getMoveDirection().equals(Direction.UP)) {
            sm.setCurrentState(new IdleStateUpPlayer(sm, gameObject));
        }else if (c2.getMoveDirection().equals(Direction.DOWN)) {
            return;
        }else if (c2.getMoveDirection().equals(Direction.LEFT)) {
            sm.setCurrentState(new MoveStateLeftPlayer(sm, gameObject));
        }else if (c2.getMoveDirection().equals(Direction.RIGHT)) {
            sm.setCurrentState(new MoveStateRightPlayer(sm, gameObject));
        }

        ActionKeysComponent c1 = (ActionKeysComponent) gameObject.getComponent("actionKeys");

        if (c1.isOnceHappened()) {
            Direction directionShoot = WizGame.directionProjectileShoot(gameObject.getTransform().getVelocity());
            if (directionShoot == Direction.UP) {
                sm.setCurrentState(new IdleStateUpPlayer(sm, gameObject));
            } else if (directionShoot == Direction.RIGHT) {
                sm.setCurrentState(new ShootStateRightPlayer(sm, gameObject));
            } else if (directionShoot == Direction.LEFT) {
                sm.setCurrentState(new ShootStateLeftPlayer(sm, gameObject));
            } else if (directionShoot == Direction.DOWN) {
                sm.setCurrentState(new ShootStateRightDownPlayer(sm, gameObject));
            } else {
                assert false;
            }
        }
    }

    @Override
    public void onEnd() {
        gameObject.removeComponent(idle);
    }
}
