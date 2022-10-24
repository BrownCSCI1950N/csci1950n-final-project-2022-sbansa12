package engine.TerrainGeneration.TerrainGraph;

import engine.AStar.Heuristic;
import engine.TerrainGeneration.TerrainGraph.TerrainNode;

public class DistanceHeuristic implements Heuristic<TerrainNode> {
    @Override
    public Double heuristic(TerrainNode object1, TerrainNode object2) {
        return object1.getCurrentPosition().dist(object2.getCurrentPosition());
    }
}
