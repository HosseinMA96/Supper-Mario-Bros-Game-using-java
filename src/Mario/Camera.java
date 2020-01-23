package Mario;

import GameEntity.Entity;

public class Camera {
    private int x,y;

    public void tick(Entity player)
    {
        setX(-player.getX()+ Game.WITDH*2);
        setY(-player.getY() +Game.HEIGHT*2);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
