package alc.Screens;

import Pair.Pair;
import alc.Constants;
import alc.Element;
import alc.AlcGame;
import engine.*;
import engine.Systems.CollisionSystem;
import engine.Systems.MouseDragSystem;
import engine.UI.UIButton;
import engine.UI.UIElement;
import engine.UI.UIRectangle;
import engine.UI.UIText;
import engine.UI.Viewport;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class GameScreen extends Screen {
    Viewport viewport;
    GameWorld gameWorld;
    AlcGame alcGame;
    UIRectangle elementMenuBackground;
    Double elementMenuStartButtonPositionChange;
    public GameScreen(Application engine) {
        super(engine);

        this.elementMenuStartButtonPositionChange = 0.0;

        // Create Background
        UIElement backgroundGame = new UIRectangle(
                this,
                null,
                new Vec2d(0,0),
                engine.getCurrentStageSize(),
                Constants.backgroundColorGame);
        uiElements.add(backgroundGame);

        // Create Game World
        this.gameWorld = new GameWorld();

        this.gameWorld.appendSystem(new MouseDragSystem(this.gameWorld));
        CollisionSystem collisionSystem = new CollisionSystem(this.gameWorld, Collections.singletonList("collision"),false);
        collisionSystem.setLayersCollide(List.of(new Pair<Integer, Integer>(0,0)));
        this.gameWorld.appendSystem(collisionSystem);

        // Create Alc Game
        this.alcGame = new AlcGame(this, gameWorld);

        // Viewport
        this.viewport = new Viewport(
                this,
                null,
                Constants.startingViewportGameCoordinates,
                Constants.viewportScreenPosition,
                Constants.viewportScreenSize,
                this.gameWorld,
                Constants.scale,
                Constants.panningButtons,
                Constants.zoomingButtons,
                Constants.panSpeed,
                Constants.zoomSpeed
        );
        backgroundGame.addChildren(viewport);

        // Create Element Menu
        elementMenuBackground = new UIRectangle(
                this,
                null,
                Constants.elementMenuPosition,
                Constants.elementMenuSize,
                Constants.elementMenuColor
        ){
            final List<KeyCode> upDownButtons = List.of(KeyCode.UP, KeyCode.DOWN);
            @Override
            public void onKeyPressed(KeyEvent e) {
                int directionPan = upDownButtons.indexOf(e.getCode());
                if (directionPan != -1) {
                    if (directionPan == 0) {
                        elementMenuStartButtonPositionChange -= Constants.elementButtonSize.y;
                        elementMenuStartButtonPositionChange = Math.max(-1 * (alcGame.getNumberOfElements() - 1) * Constants.elementButtonSize.y, elementMenuStartButtonPositionChange);
                    } else if (directionPan == 1) {
                        elementMenuStartButtonPositionChange += Constants.elementButtonSize.y;
                        elementMenuStartButtonPositionChange = Math.min(currentSize.y-(Constants.elementButtonSize.y * 2), elementMenuStartButtonPositionChange);
                    }
                }

                super.onKeyPressed(e);
            }
        };
        viewport.addChildren(elementMenuBackground);

        elementButtonGenerator(Element.FIRE);
        elementButtonGenerator(Element.WATER);
        elementButtonGenerator(Element.EARTH);
        elementButtonGenerator(Element.AIR);

        UIText countElements = new UIText(this, viewport, Constants.countElementsPosition, "4" + "/" + Constants.totalNumberOfElements, Constants.countElementsColor, Constants.countElementsFont){
            @Override
            public void onDraw(GraphicsContext g) {
                this.text = alcGame.getNumberOfElements() + "/" + Constants.totalNumberOfElements;

                super.onDraw(g);
            }
        };
        viewport.addChildren(countElements);
    }

    public Viewport getViewport() {
        return viewport;
    }

    public void elementButtonGenerator(Element ele) {
        String name = ele.name();
        if (alcGame.getFinalElements().contains(ele)) {
            name = ele.name() + ": FINAL";
        }

        UIElement toReturn = new UIButton(
                this,
                elementMenuBackground,
                new Vec2d(Constants.elementMenuPosition.x, alcGame.getNumberOfElements() * Constants.elementButtonSize.y),
                Constants.elementButtonSize,
                Constants.elementButtonColor,
                new Vec2d(0,0),
                name,
                Constants.elementButtonTextPosition,
                Constants.elementButtonTextColor,
                Constants.elementButtonTextFont){

            @Override
            public void onDraw(GraphicsContext g) {
                if (currentPosition.y+elementMenuStartButtonPositionChange+currentSize.y <= elementMenuBackground.getCurrentPosition().plus(elementMenuBackground.getCurrentSize()).y) {
                    g.setFill(color);
                    g.fillRoundRect(currentPosition.x, currentPosition.y+elementMenuStartButtonPositionChange, currentSize.x, currentSize.y, arcSize.x, arcSize.y);
                    if (!Objects.equals(buttonText, "")) {
                        g.setFont(this.currentFont.font());
                        g.setFill(this.colorText);
                        g.fillText(this.buttonText, this.currentPositionText.x, this.currentPositionText.y+elementMenuStartButtonPositionChange);
                    }
                }
            }

            @Override
            public void onMousePressed(MouseEvent e) {
                if (Utility.inBoundingBox(new Vec2d(currentPosition.x, currentPosition.y + elementMenuStartButtonPositionChange), new Vec2d(currentPosition.x, currentPosition.y + elementMenuStartButtonPositionChange).plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    alcGame.makeElement(ele, new Vec2d(e.getX(), e.getY()), false);
                }

                super.onMousePressed(e);
            }

            @Override
            public void onMouseMoved(MouseEvent e) {
                if (Utility.inBoundingBox(new Vec2d(currentPosition.x, currentPosition.y + elementMenuStartButtonPositionChange), new Vec2d(currentPosition.x, currentPosition.y + elementMenuStartButtonPositionChange).plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    this.color = Constants.elementButtonHoverColor;
                } else {
                    this.color = Constants.elementButtonColor;
                }

                super.onMouseMoved(e);
            }
        };
        alcGame.incrementNumberOfElements();
        elementMenuBackground.addChildren(toReturn);
    }
}
