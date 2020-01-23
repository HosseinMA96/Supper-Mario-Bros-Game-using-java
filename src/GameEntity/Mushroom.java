package GameEntity;

import Mario.Game;
import Mario.Handler;
import Mario.Id;

import java.awt.*;

public class Mushroom extends Entity {
    public Mushroom(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
        super(x, y, width, height, solid, id, handler);
    }



    public  void render(Graphics g){
        g.drawImage(Game.redMushroom.getBufferedImage(),x,y,width,height,null);
    }

    public  void tick(){
        x+=velX;
        y+=velY;
    }
}
