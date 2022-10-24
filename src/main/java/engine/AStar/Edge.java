package engine.AStar;

public interface Edge<N extends Node<N, E>, E extends Edge<N, E>> {
    /**
     * Getter for starting node.
     * @return starting node
     */
    N getFrom();

    /**
     * Getter for ending node.
     * @return ending node
     */
    N getTo();

    /**
     * Getter for cost of edge.
     * @return cost of edge
     */
    Double getCost();
}
