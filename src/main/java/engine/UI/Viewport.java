package engine.UI;

import engine.Direction;
import engine.GameWorld;
import engine.Screen;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.transform.Affine;

import java.math.BigDecimal;
import java.util.List;

public class Viewport extends UIElement {
    Vec2d currentGamePointCenter;
    protected final Vec2d originalViewportSize;
    protected Vec2d currentViewportSize;
    GameWorld gameWorld;
    boolean panning;
    List<KeyCode> panningButtons;
    boolean zooming;
    List<KeyCode> zoomButtons;

    double scale;

    private BigDecimal count;

    private List<Double> panSpeed;
    private List<Double> zoomSpeed;

    /**
     * Constructor for Viewport
     * @param screen - screen this viewport is in
     * @param parent - parent UIElement of this viewport
     * @param startGamePointCenter - coordinate in game space the top left of this viewport is at
     * @param viewportPosition - coordinate in screen space the top left of this viewport is at
     * @param viewportSize - size in screen space the viewport is
     * @param gameWorld - game world this viewport displays
     * @param panningButtons - list of size 4 denoting the keyboard keys used to pan view port [UP, DOWN, RIGHT, LEFT], if null no panning for viewport
     * @param zoomButtons  - list of size 2 denoting the keyboard keys used to zoom view port [ZOOM IN, ZOOM OUT], if null no zooming for viewport
     * @param panSpeed - list of size 4 denoting the speed to pan for each of the pan keys
     * @param zoomSpeed - list of size 2 denoting the speed to zoom for each of the zoom keys
     */
    public Viewport(Screen screen, UIElement parent, Vec2d startGamePointCenter, Vec2d viewportPosition, Vec2d viewportSize, GameWorld gameWorld, double scale, List<KeyCode> panningButtons, List<KeyCode> zoomButtons, List<Double> panSpeed, List<Double> zoomSpeed) throws Exception {
        super(screen, parent, viewportPosition, null);
        this.currentGamePointCenter = startGamePointCenter;
        this.originalViewportSize = new Vec2d(viewportSize);
        this.currentViewportSize = new Vec2d(viewportSize);
        this.gameWorld = gameWorld;
        this.scale = scale;

        if (panningButtons != null) {
            if (panningButtons.size() != 4 || panSpeed.size() != 4) {
                throw new Exception("Too Few or Too Many Panning Buttons Given to Viewport");
            } else {
                this.panning = true;
                this.panningButtons = panningButtons;
                this.panSpeed = panSpeed;
            }
        } else {
            this.panning = false;
        }
        if (zoomButtons != null) {
            if (zoomButtons.size() != 2 || zoomSpeed.size() != 2) {
                throw new Exception("Too Few or Too Many Zooming Buttons Given to Viewport");
            } else {
                this.zooming = true;
                this.zoomButtons = zoomButtons;
                this.zoomSpeed = zoomSpeed;
            }
        } else {
            this.zooming = false;
        }

        this.panZoomDirection = Direction.NONE;
        this.count = new BigDecimal(String.valueOf(0));
    }

    public Vec2d convertScreenCoordinateToGame(Vec2d screenCoordinate) {
        return screenCoordinate.minus(currentViewportSize.sdiv(2)).sdiv(scale).plus(currentGamePointCenter);
    }

    public double getScale() {
        return scale;
    }

    /**
     * Called periodically and used to update the state of your game.
     * @param nanosSincePreviousTick	approximate number of nanoseconds since the previous call
     */
    @Override
    public void onTick(long nanosSincePreviousTick) {

        // Check if panning/zooming and update viewport
        if (panZoomDirection != Direction.NONE) {

            // Only update viewport view every 10 nanosecond
            count = count.add(new BigDecimal(nanosSincePreviousTick));
            if (count.compareTo(new BigDecimal(10)) > 0) {
                this.count = new BigDecimal(String.valueOf(0));

                if (panZoomDirection == Direction.UP) {
                    currentGamePointCenter = currentGamePointCenter.plus(0,-panSpeed.get(0));
                } else if (panZoomDirection == Direction.DOWN) {
                    currentGamePointCenter = currentGamePointCenter.plus(0,panSpeed.get(1));
                } else if (panZoomDirection == Direction.RIGHT) {
                    currentGamePointCenter = currentGamePointCenter.plus(panSpeed.get(2),0);
                } else if (panZoomDirection == Direction.LEFT) {
                    currentGamePointCenter = currentGamePointCenter.plus(-panSpeed.get(3),0);
                } else if (panZoomDirection == Direction.IN) {
                    this.scale = this.scale * zoomSpeed.get(0);
                } else if (panZoomDirection == Direction.OUT) {
                    this.scale = this.scale / zoomSpeed.get(1);
                }
            }
        }

        gameWorld.onTick(nanosSincePreviousTick);

        super.onTick(nanosSincePreviousTick);
    }

    /**
     * Called after onTick().
     */
    @Override
    public void onLateTick() {
        gameWorld.onLateTick();

        super.onLateTick();
    }

    /**
     *  Called periodically and meant to draw graphical components.
     * @param g		a {@link GraphicsContext} object used for drawing.
     */
    @Override
    public void onDraw(GraphicsContext g) {
        Affine originalTransform = g.getTransform();
        Affine gameToScreen = new Affine();

        Vec2d half = currentViewportSize.sdiv(2);
        gameToScreen.appendTranslation(half.x, half.y);
        gameToScreen.appendScale(scale, scale);
        gameToScreen.appendTranslation(-currentGamePointCenter.x, -currentGamePointCenter.y);

        g.setTransform(gameToScreen);

        gameWorld.draw(g);

        g.setTransform(originalTransform);
        super.onDraw(g);
    }

    @Override
    public void reset() {
        gameWorld.reset();

        super.reset();
    }

    /**
     * Called when a key is typed.
     * @param e		an FX {@link KeyEvent} representing the input event.
     */
    @Override
    public void onKeyTyped(KeyEvent e) {
        gameWorld.onKeyTyped(e);

        super.onKeyTyped(e);
    }

    Direction panZoomDirection;

    /**
     * Called when a key is pressed.
     * @param e		an FX {@link KeyEvent} representing the input event.
     */
    @Override
    public void onKeyPressed(KeyEvent e) {
        if (panning) {
            int directionPan = panningButtons.indexOf(e.getCode());
            if (directionPan != -1) {
                this.count = new BigDecimal(String.valueOf(0));

                if (directionPan == 0) {
                    this.panZoomDirection = Direction.UP;
                } else if (directionPan == 1) {
                    this.panZoomDirection = Direction.DOWN;
                } else if (directionPan == 2) {
                    this.panZoomDirection = Direction.RIGHT;
                } else if (directionPan == 3) {
                    this.panZoomDirection = Direction.LEFT;
                }
            }
        }

        if (zooming) {
            int directionZoom = zoomButtons.indexOf(e.getCode());
            if (directionZoom != -1) {
                this.count = new BigDecimal(String.valueOf(0));

                if (directionZoom == 0) {
                    this.panZoomDirection = Direction.IN;
                } else if (directionZoom == 1) {
                    this.panZoomDirection = Direction.OUT;
                }
            }
        }

        gameWorld.onKeyPressed(e);

        super.onKeyPressed(e);
    }

    /**
     * Called when a key is released.
     * @param e		an FX {@link KeyEvent} representing the input event.
     */
    @Override
    public void onKeyReleased(KeyEvent e) {
        if (panning) {
            int directionPan = panningButtons.indexOf(e.getCode());
            if (directionPan != -1) {
                this.count = new BigDecimal(String.valueOf(0));
                this.panZoomDirection = Direction.NONE;
            }
        }

        if (zooming) {
            int directionPan = zoomButtons.indexOf(e.getCode());
            if (directionPan != -1) {
                this.count = new BigDecimal(String.valueOf(0));
                this.panZoomDirection = Direction.NONE;
            }
        }

        gameWorld.onKeyReleased(e);

        super.onKeyReleased(e);
    }

    /**
     * Called when the mouse is clicked.
     * @param e		an FX {@link MouseEvent} representing the input event.
     */
    @Override
    public void onMouseClicked(MouseEvent e) {
        gameWorld.onMouseClicked(e);

        super.onMouseClicked(e);
    }

    /**
     * Called when the mouse is pressed.
     * @param e		an FX {@link MouseEvent} representing the input event.
     */
    @Override
    public void onMousePressed(MouseEvent e) {
        Vec2d screenCoordinate = new Vec2d(e.getX(), e.getY());
        Vec2d gameCoordinate = convertScreenCoordinateToGame(screenCoordinate);
        gameWorld.onMousePressed(gameCoordinate);

        super.onMousePressed(e);
    }

    /**
     * Called when the mouse is released.
     * @param e		an FX {@link MouseEvent} representing the input event.
     */
    @Override
    public void onMouseReleased(MouseEvent e) {
        Vec2d screenCoordinate = new Vec2d(e.getX(), e.getY());
        Vec2d gameCoordinate = convertScreenCoordinateToGame(screenCoordinate);
        gameWorld.onMouseReleased(gameCoordinate);

        super.onMouseReleased(e);
    }

    /**
     * Called when the mouse is dragged.
     * @param e		an FX {@link MouseEvent} representing the input event.
     */
    @Override
    public void onMouseDragged(MouseEvent e) {
        Vec2d screenCoordinate = new Vec2d(e.getX(), e.getY());
        Vec2d gameCoordinate = convertScreenCoordinateToGame(screenCoordinate);
        gameWorld.onMouseDragged(gameCoordinate);

        super.onMouseDragged(e);
    }

    /**
     * Called when the mouse is moved.
     * @param e		an FX {@link MouseEvent} representing the input event.
     */
    @Override
    public void onMouseMoved(MouseEvent e) {
        gameWorld.onMouseMoved(e);

        super.onMouseMoved(e);
    }

    /**
     * Called when the mouse wheel is moved.
     * @param e		an FX {@link ScrollEvent} representing the input event.
     */
    @Override
    public void onMouseWheelMoved(ScrollEvent e) {
        gameWorld.onMouseWheelMoved(e);

        super.onMouseWheelMoved(e);
    }

    /**
     * Called when the window's focus is changed.
     * @param newVal	a boolean representing the new focus state
     */
    @Override
    public void onFocusChanged(boolean newVal) {
        gameWorld.onFocusChanged(newVal);

        super.onFocusChanged(newVal);
    }

    /**
     * Called when the window is resized.
     * @param newSize	the new size of the drawing area. (scale factor)
     */
    @Override
    public void onResize(Vec2d newSize) {
        this.currentPosition = originalPosition.pmult(newSize);
        this.currentViewportSize = originalViewportSize.pmult(newSize);
        gameWorld.onResize(newSize);

        super.onResize(newSize);
    }

    /**
     * Called when the app is shutdown.
     */
    @Override
    public void onShutdown() {
        gameWorld.onShutdown();

        super.onShutdown();
    }

    /**
     * Called when the app is starting up.s
     */
    @Override
    public void onStartup() {
        gameWorld.onStartup();

        super.onStartup();
    }
}
