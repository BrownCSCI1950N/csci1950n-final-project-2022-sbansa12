package engine.TerrainGeneration.TerrainGraph;

import engine.AStar.Edge;

import java.util.Objects;

public class TerrainEdge implements Edge<TerrainNode, TerrainEdge> {

    TerrainNode from;
    TerrainNode to;
    Double cost;

    public TerrainEdge(TerrainNode from, TerrainNode to, Double cost) {
        this.from = from;
        this.to = to;
        this.cost = cost;
    }

    @Override
    public TerrainNode getFrom() {
        return this.from;
    }

    @Override
    public TerrainNode getTo() {
        return this.to;
    }

    @Override
    public Double getCost() {
        return this.cost;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                from,
                to,
                cost);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TerrainEdge) {
            TerrainEdge edge = (TerrainEdge) obj;
            return edge.from.equals(this.from)
                    && edge.to.equals(this.to)
                    && edge.cost.equals(this.cost);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "E: (From: " + from + ", To: " + to + ", Cost: " + cost + ")";
    }
}
