package engine.Systems;

import Pair.Pair;
import engine.Components.MouseDragComponent;
import engine.GameObject;
import engine.GameWorld;
import engine.InputEvents;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;

import java.util.Collections;

public class MouseDragSystem extends System {

    private boolean startedDrag;

    public MouseDragSystem(GameWorld gameWorld) {
        super(gameWorld, Collections.singletonList("mouseDrag"));
        this.startedDrag = false;
    }

    @Override
    public void tick(long t) {
        Vec2d onMousePressed = gameWorld.findInputEventMouse(InputEvents.ONMOUSEPRESSED).getRight();
        Vec2d onMouseDragged = gameWorld.findInputEventMouse(InputEvents.ONMOUSEDRAGGED).getRight();
        Vec2d onMouseReleased = gameWorld.findInputEventMouse(InputEvents.ONMOUSERELEASED).getRight();

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

        super.tick(t);
    }

    @Override
    public void lateTick() {

    }

    @Override
    public void draw(GraphicsContext g) {

    }

    @Override
    public String name() {
        return "mouseDrag";
    }
}
