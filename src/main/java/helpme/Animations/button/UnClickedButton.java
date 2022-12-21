package helpme.Animations.button;

import engine.Components.SpriteComponent;
import engine.Components.State;
import engine.Components.StateMachineComponent;
import engine.GameObject;
import engine.TerrainGeneration.TileType;
import engine.support.Vec2d;
import helpme.HelpMeGame;
import helpme.HelpMeImageResource;

public class UnClickedButton extends State {
    StateMachineComponent sm;
    GameObject gameObject;
    SpriteComponent image;

    public UnClickedButton(StateMachineComponent sm, GameObject gameObject) {
        this.sm = sm;
        this.gameObject = gameObject;
        this.image = new SpriteComponent(gameObject, HelpMeImageResource.images.getResource(TileType.BUTTON.name()), new Vec2d(0,0));
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
            sm.setCurrentState(new ClickedButton(sm, gameObject));
        }
    }

    @Override
    public void onEnd() {
        gameObject.removeComponent(image);
    }
}
