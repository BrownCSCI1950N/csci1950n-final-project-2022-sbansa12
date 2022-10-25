package engine.Components;

import engine.support.Vec2d;
import engine.support.Vec2i;

import java.util.List;

public interface GameTileConversion {
    List<Vec2i> gameToTile(Vec2d gameSpacePosition);
    Vec2d tileToGame(Vec2i tilePosition);
}
