package wiz.StateMachineBoss;

import engine.Components.ConstantAnimationComponent;
import engine.Components.State;
import engine.Components.StateMachineComponent;
import engine.GameObject;
import engine.TerrainGeneration.TileType;
import engine.support.Vec2d;
import wiz.Constants;
import wiz.WizGame;

import java.math.BigDecimal;
import java.util.List;

import static wiz.WizGame.createProjectile;

public class ShootStateBoss extends State {
    GameObject gameObject;
    BigDecimal count;
    BigDecimal timer;
    ConstantAnimationComponent animation;
    StateMachineComponent sm;
    public ShootStateBoss(StateMachineComponent sm, GameObject gameObject) {
        this.sm = sm;
        this.gameObject = gameObject;
        this.count = new BigDecimal("0");
        this.timer = Constants.bossMoveTime;
        this.animation = new ConstantAnimationComponent(gameObject, WizGame.images.getResource(TileType.BOSS.name()), List.of(new Vec2d(1,0), new Vec2d(2,0)), Constants.bossMoveTime.divideToIntegralValue(new BigDecimal("2")));
    }

    @Override
    public void onStart() {
        gameObject.addComponent(animation);
        createProjectile(gameObject);
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
        sm.setCurrentState(new MoveStateBoss(sm, gameObject));
    }

    @Override
    public void onEnd() {
        gameObject.removeComponent(animation);
    }
}
