package engine.Components;

import engine.GameObject;
import engine.support.Vec2d;

public class Collision {
    final GameObject collidedObject;
    Vec2d mtv;

    double t;

    public Collision(GameObject collidedObject, Vec2d mtv) {
        this.collidedObject = collidedObject;
        this.mtv = mtv;
    }

    public Collision(GameObject collidedObject, double t) {
        this.collidedObject = collidedObject;
        this.mtv = null;
        this.t = t;
    }

    public GameObject getCollidedObject() {
        return collidedObject;
    }

    public Vec2d getMTV() {
        return mtv;
    }

    public double getT() {
        return t;
    }

    @Override
    public String toString() {
        if (mtv != null) {
            return "Collision: Object: " + this.collidedObject + ", MTV: " + mtv;
        } else {
            return "Collision: Object: " + this.collidedObject + ", t: " + t;
        }
    }
}
