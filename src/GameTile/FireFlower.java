package GameTile;

import GameEntity.Entity;
import Mario.Game;
import Mario.Handler;
import Mario.Id;

import java.awt.*;

public class FireFlower extends Entity {
    public FireFlower(int x, int y, int width, int height, Id id, Handler handler) {
        super(x, y, width, height, id, handler);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Game.fireFlower.getBufferedImage(),x,y,width,height,null);
    }

    @Override
    public void tick() {

    }
}
