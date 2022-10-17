package engine.Components;

public abstract class State {
    public State() {
    }
    public abstract void onStart();
    public abstract void onTick(long nanosSincePreviousTick);
    public abstract void onSwitch();
    public abstract void onEnd();
}
