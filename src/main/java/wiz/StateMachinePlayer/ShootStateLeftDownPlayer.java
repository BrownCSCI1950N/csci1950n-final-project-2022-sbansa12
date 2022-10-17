package wiz.StateMachinePlayer;

import engine.Components.SpriteComponent;
import engine.Components.State;
import engine.Components.StateMachineComponent;
import engine.GameObject;
import engine.TerrainGeneration.TileType;
import engine.support.Vec2d;
import wiz.Constants;
import wiz.WizGame;

import java.math.BigDecimal;

public class ShootStateLeftDownPlayer extends State {

    StateMachineComponent sm;
    GameObject gameObject;
    BigDecimal count;
    BigDecimal timer;
    SpriteComponent animation;

    public ShootStateLeftDownPlayer(StateMachineComponent sm, GameObject gameObject) {
        this.sm = sm;
        this.gameObject = gameObject;
        this.count = new BigDecimal("0");
        this.timer = Constants.playerShootTime;
        this.animation = new SpriteComponent(gameObject, WizGame.images.getResource(TileType.PLAYER.name()), new Vec2d(1,7));
    }

    @Override
    public void onStart() {
        gameObject.addComponent(animation);
//        createProjectile(gameObject);
    }

    @Override
    public void onTick(long nanosSincePreviousTick) {
        count = count.add(new BigDecimal(nanosSincePreviousTick));
    }


    @Override
    public void onSwitch() {
        if (count.compareTo(timer) < 0) {
            return;
        }
        count = new BigDecimal("0");
        sm.setCurrentState(new IdleStateLeftPlayer(sm, gameObject));
    }

    @Override
    public void onEnd() {
        gameObject.removeComponent(animation);
    }
}
