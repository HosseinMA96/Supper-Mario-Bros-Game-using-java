package GameEntity.Enemy;

public class ChangedKoopa {
    private KoopaState prev,next;
    private int velX;

    public ChangedKoopa(KoopaState prev, KoopaState next, int velX) {
        this.prev = prev;
        this.next = next;
        this.velX = velX;
    }

    public KoopaState getPrev() {
        return prev;
    }

    public KoopaState getNext() {
        return next;
    }

    public int getVelX() {
        return velX;
    }
}
