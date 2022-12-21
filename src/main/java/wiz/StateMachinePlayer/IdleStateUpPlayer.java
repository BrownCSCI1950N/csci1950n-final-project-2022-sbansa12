package wiz.StateMachinePlayer;

import engine.Components.*;
import engine.Direction;
import engine.GameObject;
import engine.TerrainGeneration.TileType;
import engine.support.Vec2d;
import wiz.WizGame;

public class IdleStateUpPlayer extends State {

    StateMachineComponent sm;
    GameObject gameObject;
    SpriteComponent idle;

    public IdleStateUpPlayer(StateMachineComponent sm, GameObject gameObject) {
        this.sm = sm;
        this.gameObject = gameObject;
        this.idle = new SpriteComponent(gameObject, WizGame.images.getResource(TileType.PLAYER1.name()), new Vec2d(0,0));
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
        ActionKeysComponent c1 = (ActionKeysComponent) gameObject.getComponent("actionKeys");

        if (c1.isOnceHappened()) {
            Direction directionShoot = WizGame.directionProjectileShootPlayer(gameObject.getTransform().getVelocity());
            if (directionShoot == Direction.UP) {
                return;
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

        MoveKeysComponent c2 = (MoveKeysComponent) gameObject.getComponent("moveKeys");

        if (c2.getMoveDirection().equals(Direction.NONE)) {
            return;
        } else if (c2.getMoveDirection().equals(Direction.UP)) {
            return;
        }else if (c2.getMoveDirection().equals(Direction.DOWN)) {
            sm.setCurrentState(new IdleStateRightPlayer(sm, gameObject));
        }else if (c2.getMoveDirection().equals(Direction.LEFT)) {
            sm.setCurrentState(new MoveStateLeftPlayer(sm, gameObject));
        }else if (c2.getMoveDirection().equals(Direction.RIGHT)) {
            sm.setCurrentState(new MoveStateRightPlayer(sm, gameObject));
        }
    }

    @Override
    public void onEnd() {
        gameObject.removeComponent(idle);
    }
}
