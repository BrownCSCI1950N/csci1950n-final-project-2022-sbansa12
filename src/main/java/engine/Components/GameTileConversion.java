package engine.Components;

import engine.support.Vec2d;
import engine.support.Vec2i;

public interface GameTileConversion {
    Vec2i gameToTile(Vec2d gameSpacePosition);
    Vec2d tileToGame(Vec2i tilePosition);
}
