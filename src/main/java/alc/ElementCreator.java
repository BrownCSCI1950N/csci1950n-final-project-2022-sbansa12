package alc;

import alc.Components.ElementComponent;
import engine.Components.MouseDragComponent;
import engine.Components.SpriteComponent;
import engine.Components.TransformComponent;
import engine.GameObject;
import engine.GameWorld;
import engine.UI.Viewport;
import engine.support.Vec2d;
import javafx.scene.image.Image;

public class ElementCreator {
    private static Integer zIndex = 1;

    public static void makeElement(Viewport viewport, GameWorld gameWorld, Element e, Vec2d startScreenCoordinate) {
        Image sprite = new Image("file:.\\src\\main\\java\\alc\\ElementImages\\" + e.name() + ".png");

        Vec2d startGameCoordinate = viewport.convertScreenCoordinateToGame(startScreenCoordinate);
        Vec2d size = new Vec2d(sprite.getWidth(),sprite.getHeight());

        TransformComponent transformComponent = new TransformComponent(startGameCoordinate, size);
        GameObject o = new GameObject(transformComponent, zIndex);
        zIndex += 1;
        o.addComponent(new ElementComponent(e));
        o.addComponent(new SpriteComponent(o, sprite));
        o.addComponent(new MouseDragComponent(o, startGameCoordinate, startGameCoordinate));

        gameWorld.addGameObject(o);
    }
}
