package engine.AStar;

import java.util.List;

public interface Node<N extends Node<N, E>, E extends Edge<N, E>> {
    /**
     * Getter for Edges.
     * @return - Edges, that connect to the current node.
     */
    List<E> getEdges();
}
