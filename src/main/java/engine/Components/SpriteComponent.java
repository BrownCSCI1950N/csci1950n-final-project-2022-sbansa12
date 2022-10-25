package engine.Components;

import engine.GameObject;
import engine.Sprite;
import engine.TerrainGeneration.TileType;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SpriteComponent implements Component {
    final GameObject gameObject;
    private final Sprite sprite;
    protected Vec2d currentSpritePosition;

    public SpriteComponent(GameObject gameObject, Sprite sprite, Vec2d currentSpritePosition) {
        this.gameObject = gameObject;
        this.sprite = sprite;
        this.currentSpritePosition = currentSpritePosition;
    }

    @Override
    public void tick(long nanosSinceLastTick) {

    }

    @Override
    public void lateTick() {

    }

    @Override
    public void draw(GraphicsContext g) {
        Vec2d gameSpacePosition = gameObject.getTransform().getCurrentGameSpacePosition();
        // Code to Draw Circles of Influence
//        if (gameObject.hasComponentTag("tile")) {
//            TileComponent t = (TileComponent) gameObject.getComponent("tile");
//            if (t.getTileType() == TileType.ENEMY) {
//                double radius  = Math.sqrt(90000);
//                Vec2d center = gameObject.getTransform().getCurrentGameSpacePosition().plus(gameObject.getTransform().getSize().sdiv(2));
//                g.setFill(Color.BLACK);
//                g.strokeOval(center.x-radius, center.y-radius, radius*2, radius*2);
//            }
//        }
        sprite.draw(g, gameSpacePosition, gameObject.getTransform().getSize(), this.currentSpritePosition);
    }

    @Override
    public String getTag() {
        return "sprite";
    }
}
