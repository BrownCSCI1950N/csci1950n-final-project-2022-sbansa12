package alc.Screens;

import alc.Constants;
import alc.Element;
import alc.ElementCreator;
import engine.*;
import engine.Components.SpriteComponent;
import engine.Components.TransformComponent;
import engine.Systems.MouseDragSystem;
import engine.UI.UIButton;
import engine.UI.UIElement;
import engine.UI.UIRectangle;
import engine.UI.Viewport;
import engine.support.Vec2d;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public class GameScreen extends Screen {
    Viewport viewport;
    GameWorld gameWorld;
    Integer numberOfElements;
    public GameScreen(Application engine) throws Exception {
        super(engine);

        this.numberOfElements = 0;

        // Create Background
        UIElement backgroundGame = new UIRectangle(
                this,
                null,
                new Vec2d(0,0),
                engine.getCurrentStageSize(),
                Constants.backgroundColorGame);
        uiElements.add(backgroundGame);

        this.gameWorld = new GameWorld();

        this.gameWorld.addSystem(new MouseDragSystem(this.gameWorld));

        // Background to Game World
        Image sprite = new Image("file:.\\src\\main\\java\\alc\\ElementImages\\" + "BACKGROUND" + ".png");
        TransformComponent backgroundTransformComponent = new TransformComponent(Constants.backgroundPosition, new Vec2d(0,0));
        GameObject background = new GameObject(backgroundTransformComponent, 0);
        background.addComponent(new SpriteComponent(background, sprite));
        this.gameWorld.addGameObject(background);

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

        UIElement elementMenuBackground = new UIRectangle(
                this,
                null,
                Constants.elementMenuPosition,
                Constants.elementMenuSize,
                Constants.elementMenuColor
        );
        viewport.addChildren(elementMenuBackground);

        elementMenuBackground.addChildren(elementButtonGenerator(elementMenuBackground, Element.FIRE));
        elementMenuBackground.addChildren(elementButtonGenerator(elementMenuBackground, Element.WATER));
        elementMenuBackground.addChildren(elementButtonGenerator(elementMenuBackground, Element.EARTH));
        elementMenuBackground.addChildren(elementButtonGenerator(elementMenuBackground, Element.AIR));
    }

    private UIElement elementButtonGenerator(UIElement parent, Element ele) {
        UIElement toReturn = new UIButton(
                this,
                parent,
                new Vec2d(Constants.elementMenuPosition.x, this.numberOfElements * Constants.elementButtonSize.y),
                Constants.elementButtonSize,
                Constants.elementButtonColor,
                new Vec2d(0,0),
                ele.toString(),
                Constants.elementButtonTextPosition,
                Constants.elementButtonTextColor,
                Constants.elementButtonTextFont){

            @Override
            public void onMousePressed(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    ElementCreator.makeElement(viewport, gameWorld, ele, new Vec2d(e.getX(), e.getY()));
                }

                super.onMousePressed(e);
            }

            @Override
            public void onMouseMoved(MouseEvent e) {
                if (Utility.inBoundingBox(currentPosition, currentPosition.plus(currentSize), new Vec2d(e.getX(), e.getY()))) {
                    this.color = Constants.elementButtonHoverColor;
                } else {
                    this.color = Constants.elementButtonColor;
                }

                super.onMouseMoved(e);
            }
        };

        numberOfElements += 1;
        return toReturn;
    }
}
