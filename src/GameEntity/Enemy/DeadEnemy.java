package GameEntity.Enemy;

import Mario.Id;

public class DeadEnemy {
    private int tag;
    private Id id;

    public DeadEnemy(int tag, Id id) {
        this.tag = tag;
        this.id = id;
    }

    public int getTag() {
        return tag;
    }

    public Id getId() {
        return id;
    }
}
