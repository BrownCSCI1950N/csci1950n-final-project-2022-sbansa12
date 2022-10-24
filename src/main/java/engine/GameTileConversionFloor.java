package engine;

import engine.Components.GameTileConversion;
import engine.support.Vec2d;
import engine.support.Vec2i;

public class GameTileConversionFloor implements GameTileConversion {
    Vec2d tileSize;
    public GameTileConversionFloor(Vec2d tileSize) {
        this.tileSize = tileSize;
    }

    @Override
    public Vec2i gameToTile(Vec2d gameSpacePosition) {
        Vec2d newV = gameSpacePosition.pdiv(tileSize).floor();
        return new Vec2i((int) newV.x, (int)newV.y);
    }

    @Override
    public Vec2d tileToGame(Vec2i tilePosition) {
        Vec2i cTS = new Vec2i((int) tileSize.x, (int) tileSize.y);
        return new Vec2d(tilePosition.pmult(cTS));
    }
}
