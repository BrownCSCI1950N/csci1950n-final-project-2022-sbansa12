package engine.AI.GOAP;

import java.util.List;

public abstract class Action {
    private List<Condition> _conditions;
    private double _cost;

    public abstract void changeState(GameState s);
}
