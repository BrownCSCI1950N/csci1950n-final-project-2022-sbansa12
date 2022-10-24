package engine.AStar;

public interface Heuristic<T> {
    Double heuristic(T object1, T object2);
}
