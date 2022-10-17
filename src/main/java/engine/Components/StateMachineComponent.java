package engine.Components;

import javafx.scene.canvas.GraphicsContext;

public class StateMachineComponent implements Component {

    private State currentState = null;

    public StateMachineComponent() {
    }

    public void setCurrentState(State nextState) {
        if (currentState != null ) {
            currentState.onEnd();
        }
        currentState = nextState;
        currentState.onStart();
    }

    /**
     * Called periodically and used to update the state of your game.
     * @param nanosSinceLastTick	approximate number of nanoseconds since the previous call
     */
    @Override
    public void tick(long nanosSinceLastTick) {
        currentState.onTick(nanosSinceLastTick);
        currentState.onSwitch();
    }

    @Override
    public void lateTick() {

    }

    @Override
    public void draw(GraphicsContext g) {

    }

    @Override
    public String getTag() {
        return "stateMachine";
    }
}
