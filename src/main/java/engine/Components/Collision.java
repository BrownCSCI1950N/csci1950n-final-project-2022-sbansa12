package engine.Components;

import engine.GameObject;
import engine.support.Vec2d;

public class Collision {
    final GameObject collidedObject;
    final Vec2d mtv;

    public Collision(GameObject collidedObject, Vec2d mtv) {
        this.collidedObject = collidedObject;
        this.mtv = mtv;
    }

    public GameObject getCollidedObject() {
        return collidedObject;
    }

    public Vec2d getMTV() {
        return mtv;
    }

    @Override
    public String toString() {
        return "Collision: Object: " + this.collidedObject + ", MTV: " + mtv;
    }
}
