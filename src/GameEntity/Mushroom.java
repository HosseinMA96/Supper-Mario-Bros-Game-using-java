package GameEntity;

import GameTile.Tile;
import Mario.Game;
import Mario.Handler;
import Mario.Id;

import java.awt.*;
import java.util.Random;

public class Mushroom extends Entity {
    private Random random=new Random();
    private int dir;

    public Mushroom(int x, int y, int width, int height, Id id, Handler handler) {
        super(x, y, width, height, id, handler);
        dir=random.nextInt(2);

        if(dir==0)
            velX=-1;

        else
            velX=1;
    }





    public  void render(Graphics g){
        g.drawImage(Game.redMushroom.getBufferedImage(),x,y,width,height,null);
    }

    public  void tick(){
        x+=velX;
        y+=velY;

        for (Tile t : handler.getTile()) {
            if (!t.getSolid())
                break;

//            if (t.getId() == Id.wall) {
//                if (getBoundsTop().intersects(t.getBounds())) {
//                    setVelY(0);
//
//                    if (jumping) {
//                        jumping = false;
//                        //  gravity = 0.8;
//                        gravity = 2;
//                        falling = true;
//                    }
////                    y=t.getY()+t.getHeight();
//                    //BLANK
//                }
//            }

            if (getBoundsBottom().intersects((t.getBounds()))) {
                setVelY(0);
                y = t.getY() - t.getHeight();
                if (falling)
                    falling = false;
            } else {
                if (!falling) {
                    //   gravity = 0.8;
                    gravity = 2;
                    falling = true;
                }
            }


            if (getBoundsLeft().intersects((t.getBounds()))) {
                setVelX(1);
            }

            if (getBoundsRight().intersects((t.getBounds()))) {
                setVelX(-1);

            }

        }

        if (falling) {
            gravity += .1;
            setVelY((int) gravity);
        }

    }
}
