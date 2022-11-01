package engine.Components;

import engine.TerrainGeneration.TileType;
import javafx.scene.canvas.GraphicsContext;

public class TileComponent implements Component {
    private final TileType tileType;
    public TileComponent(TileType tileType) {
        this.tileType = tileType;
    }

    public TileType getTileType() {
        return tileType;
    }

    public boolean isPlayer() {
        return tileType == TileType.PLAYER;
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
        return "tile";
    }
}
