package nin.Animations.exit;

import engine.Components.SpriteComponent;
import engine.Components.State;
import engine.Components.StateMachineComponent;
import engine.GameObject;
import engine.TerrainGeneration.TileType;
import engine.support.Vec2d;
import nin.NinGame;
import nin.NinImageResource;

public class ClosedExit extends State {
    StateMachineComponent sm;
    GameObject gameObject;
    SpriteComponent image;

    public ClosedExit(StateMachineComponent sm, GameObject gameObject) {
        this.sm = sm;
        this.gameObject = gameObject;
        this.image = new SpriteComponent(gameObject, NinImageResource.images.getResource(TileType.EXIT.name()), new Vec2d(0,0));
    }

    @Override
    public void onStart() {
        gameObject.addComponent(image);
    }

    @Override
    public void onTick(long nanosSincePreviousTick) {

    }

    @Override
    public void onSwitch() {
        if (NinGame.doorOpen) {
            sm.setCurrentState(new OpenExit(sm, gameObject));
        }
    }

    @Override
    public void onEnd() {
        gameObject.removeComponent(image);
    }
}
