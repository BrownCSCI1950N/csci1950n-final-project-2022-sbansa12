package engine.AStar;

import java.util.*;

public class AStar<N extends Node<N, E>, E extends Edge<N, E>> {
    private final Heuristic<N> hCalc;

    public AStar() {
        this.hCalc = null;
    }

    public AStar(Heuristic<N> heuristicCalculator) {
        this.hCalc = heuristicCalculator;
    }

    /**
     * Runs A* or Dijkstra algorithm from a given start and end node.
     * @param start node to start the path from
     * @param end destination node we want to reach optimally
     * @return list of edges from start to end node taken along most optimal path
     */
    public List<E> run(N start, N end) {
        // maps distance from start node to a node, used as PriorityQueue to find next edge to traverse
        TreeMap<Double, N> pq = new TreeMap<>();
        // maps node to current best distance to that node
        Map<N, Double> distToNode = new HashMap<>();
        // maps a node to the most optimal parent edge that leads from the given starting node
        Map<N, E> paths = new HashMap<>();
        // visited nodes
        Set<N> visited = new HashSet<>();
        // maps a node to its distance to the destination.
        Map<N, Double> vertexToHeuristic = new HashMap<>();
        // Put the source vertex into the data structures
        distToNode.put(start, 0.0);
        pq.put(0.0, start);
        paths.put(start, null);
        if (hCalc != null) {
            vertexToHeuristic.put(start, hCalc.heuristic(start, end));
        }

        while (!pq.isEmpty()) {
            N currentNode = pq.pollFirstEntry().getValue();
            if (currentNode.equals(end)) {
                return pathHelper(end, paths);
            }
            if (!visited.contains(currentNode)) {
                visited.add(currentNode);
                List<E> edges = currentNode.getEdges();
                for (E edge: edges) {
                    // edge distance and the cumulative distance up to current currentNode
                    double newDist = edge.getCost() + distToNode.get(currentNode);
                    // cumulative distance up to current currentNode
                    double currDist = distToNode.getOrDefault(edge.getTo(), Double.POSITIVE_INFINITY);
                    if (Double.compare(newDist, currDist) < 0) {
                        // else, update hashmaps accordingly
                        distToNode.put(edge.getTo(), newDist);
                        // calculate heuristic weight if needed.
                        if (hCalc != null) {
                            if (!vertexToHeuristic.containsKey(edge.getTo())) {
                                vertexToHeuristic.put(edge.getTo(), hCalc.heuristic(edge.getTo(), end));
                            }
                            double heuristic = vertexToHeuristic.get(edge.getTo());
                            newDist += heuristic;
                        }
                        if (!visited.contains(edge.getTo())) {
                            pq.put(newDist, edge.getTo());
                        }
                        paths.put(edge.getTo(), edge);
                    }
                }
            }
        }
        return new LinkedList<>();
    }

    /**
     * Returns the optimal path to the end node of A* or Dijkstra's using the paths hashmap.
     * @param destination - the target node to which we need to get an optimal path
     *                    (using the hashmap paths).
     * @param paths - the hashmap used to construct the output path.
     */
    private List<E> pathHelper(N destination, Map<N, E> paths) {
        List<E> output = new LinkedList<>();
        N curr = destination;
        if (paths.containsKey(curr)) {
            while (paths.get(curr) != null) {
                output.add(0, paths.get(curr));
                curr = paths.get(curr).getFrom();
            }
        }
        return output;
    }
}
