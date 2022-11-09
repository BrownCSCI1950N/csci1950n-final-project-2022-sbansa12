package engine.TerrainGeneration;

import engine.Components.GameTileConversion;
import engine.support.Vec2d;
import engine.support.Vec2i;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class GameTileConversionClosest implements GameTileConversion {
    Vec2d tileSize;
    public GameTileConversionClosest(Vec2d tileSize) {
        this.tileSize = tileSize;
    }
    @Override
    public List<Vec2i> gameToTile(Vec2d gameSpacePosition) {
        Vec2d newV = gameSpacePosition.pdiv(tileSize);
        Vec2d newV1 = new Vec2d(Math.floor(newV.x), Math.floor(newV.y));
        Vec2d newV2 = new Vec2d(Math.ceil(newV.x), Math.ceil(newV.y));
        Vec2d newV3 = new Vec2d(Math.ceil(newV.x), Math.floor(newV.y));
        Vec2d newV4 = new Vec2d(Math.floor(newV.x), Math.ceil(newV.y));

        double d1 = newV1.dist2(newV);
        double d2 = newV2.dist2(newV);
        double d3 = newV3.dist2(newV);
        double d4 = newV4.dist2(newV);

        List<Double> l = List.of(d1, d2, d3, d4);

        double min = Collections.min(l);

        List<Integer> indexes = findIndexes(l, min);

        List<Vec2i> toReturn = new LinkedList<>();

        for (Integer i : indexes) {
            if (i == 0) {
                toReturn.add(new Vec2i((int) newV1.x, (int)newV1.y));
            } else if (i == 1) {
                toReturn.add(new Vec2i((int) newV2.x, (int)newV2.y));
            } else if (i == 2) {
                toReturn.add(new Vec2i((int) newV3.x, (int)newV3.y));
            } else if (i == 3) {
                toReturn.add(new Vec2i((int) newV4.x, (int)newV4.y));
            }
        }

        return toReturn;
    }

    private List<Integer> findIndexes(List<Double> l, double min) {
        List<Integer> toReturn = new LinkedList<>();
        for (int i = 0; i < l.size(); i++) {
            if (l.get(i).equals(min)) {
                toReturn.add(i);
            }
        }

        return toReturn;
    }

    @Override
    public Vec2d tileToGame(Vec2i tilePosition) {
        Vec2i cTS = new Vec2i((int) tileSize.x, (int) tileSize.y);
        return new Vec2d(tilePosition.pmult(cTS));
    }
}
