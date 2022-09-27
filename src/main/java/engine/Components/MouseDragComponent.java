package engine.Components;

import Pair.Pair;
import engine.GameObject;
import engine.InputEvents;
import engine.Utility;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;


public class MouseDragComponent extends Component<Pair<InputEvents,Vec2d>> {
    private final GameObject gameObject;
    private boolean startedDragging;
    private Vec2d startDragCoordinate;
    private Vec2d startDragObjectPosition;

    /**
     * Constructor for MouseDragComponent, cannot drag game object straight away
     * @param gameObject - game object this is a component to
     */
    public MouseDragComponent(GameObject gameObject) {
        this.gameObject = gameObject;
        this.startedDragging = false;
        this.startDragCoordinate = null;
        this.startDragObjectPosition = null;
    }

    /**
     * Constructor for MouseDragComponent, can drag game object straight away
     * @param gameObject - game object this is a component to
     * @param startDragCoordinate - start game coordinate of drag
     * @param startDragObjectPosition - start game coordinate of game object
     */
    public MouseDragComponent(GameObject gameObject, Vec2d startDragCoordinate, Vec2d startDragObjectPosition) {
        this.gameObject = gameObject;
        this.startedDragging = true;
        this.startDragCoordinate = startDragCoordinate;
        this.startDragObjectPosition = startDragObjectPosition;
    }
    @Override
    public void tick(long nanosSinceLastTick) {
    }

    @Override
    public void lateTick() {

    }

    @Override
    public void draw(GraphicsContext g) {

    }

    @Override
    public String getTag() {
        return "mouseDrag";
    }

    @Override
    public void script(Pair<InputEvents, Vec2d> input) {
        if (!startedDragging && input.getLeft().equals(InputEvents.ONMOUSEPRESSED)) {
            Vec2d gameSpacePosition = gameObject.getTransform().getGameSpacePosition();
            Vec2d size = gameObject.getTransform().getSize();

            if (Utility.inBoundingBox(gameSpacePosition, gameSpacePosition.plus(size), input.getRight())) {
                this.startedDragging = true;
                this.startDragCoordinate = input.getRight();
                this.startDragObjectPosition = gameSpacePosition;
            }
        } else if (startedDragging && input.getLeft().equals(InputEvents.ONMOUSEDRAGGED)) {
            gameObject.getTransform().setGameSpacePosition(startDragObjectPosition.plus(input.getRight().minus(startDragCoordinate)));
        } else if (startedDragging && input.getLeft().equals(InputEvents.ONMOUSERELEASED)) {
            startedDragging = false;
            startDragCoordinate = null;
            startDragObjectPosition = null;
        }
    }
}