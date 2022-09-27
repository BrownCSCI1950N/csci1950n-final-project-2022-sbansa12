package engine.Systems;

import Pair.Pair;
import engine.Components.MouseDragComponent;
import engine.GameObject;
import engine.GameWorld;
import engine.InputEvents;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;

public class MouseDragSystem extends System {

    private boolean startedDrag;

    public MouseDragSystem(GameWorld gameWorld) {
        super(gameWorld,"mouseDrag");
        this.startedDrag = false;
    }

    @Override
    public void tick(long t) {
        Vec2d onMousePressed = gameWorld.findInputEvent(InputEvents.ONMOUSEPRESSED);
        Vec2d onMouseDragged = gameWorld.findInputEvent(InputEvents.ONMOUSEDRAGGED);
        Vec2d onMouseReleased = gameWorld.findInputEvent(InputEvents.ONMOUSERELEASED);
        if (!startedDrag && onMousePressed != null) {
            this.startedDrag = true;
            for (GameObject gameObject: gameObjects) {
                MouseDragComponent c = (MouseDragComponent) gameObject.getComponent("mouseDrag");
                c.script(new Pair<>(InputEvents.ONMOUSEPRESSED, onMousePressed));
            }
        }

        if (startedDrag && onMouseDragged != null) {
            for (GameObject gameObject: gameObjects) {
                MouseDragComponent c = (MouseDragComponent) gameObject.getComponent("mouseDrag");
                c.script(new Pair<>(InputEvents.ONMOUSEDRAGGED, onMouseDragged));
            }
        }

        if (startedDrag && onMouseReleased != null) {
            this.startedDrag = false;
            for (GameObject gameObject: gameObjects) {
                MouseDragComponent c = (MouseDragComponent) gameObject.getComponent("mouseDrag");
                c.script(new Pair<>(InputEvents.ONMOUSERELEASED, onMouseReleased));
            }
        }
    }

    @Override
    public void lateTick() {

    }

    @Override
    public void draw(GraphicsContext g) {

    }
}
