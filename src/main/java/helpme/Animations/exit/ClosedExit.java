package helpme.Animations.exit;

import engine.Components.SpriteComponent;
import engine.Components.State;
import engine.Components.StateMachineComponent;
import engine.GameObject;
import engine.TerrainGeneration.TileType;
import engine.support.Vec2d;
import helpme.HelpMeGame;
import helpme.HelpMeImageResource;

public class ClosedExit extends State {
    StateMachineComponent sm;
    GameObject gameObject;
    SpriteComponent image;
    TileType t;

    public ClosedExit(StateMachineComponent sm, GameObject gameObject, TileType t) {
        this.sm = sm;
        this.gameObject = gameObject;
        this.image = new SpriteComponent(gameObject, HelpMeImageResource.images.getResource(t.name()), new Vec2d(0,0));
        this.t = t;
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
        if (HelpMeGame.doorOpen) {
            sm.setCurrentState(new OpenExit(sm, gameObject, t));
        }
    }

    @Override
    public void onEnd() {
        gameObject.removeComponent(image);
    }
}
