package GameEntity;

import GameTile.Tile;
import Mario.Game;
import Mario.Handler;
import Mario.Id;

import java.awt.*;

public class FireBall extends Entity {
    private int numberOfCollisions=0;
    public static int fireBallsInScreen=0;

    public FireBall(int x, int y, int width, int height, Id id, Handler handler, int facing) {
        super(x, y, width, height, id, handler);

        if (facing == 0)
            velX = -8;

        else
            velX = 8;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Game.fireBall.getBufferedImage(), x, y, width, height, null);

    }

    @Override
    public void tick() {
        if(Game.paused)
            return;

        x+=velX;
        y+=velY;

//        if(x>Game.cam.getX() || y> Game.cam.getY() || x<Game.cam.getX()-4*Game.WITDH || y<Game.cam.getY()-4*Game.HEIGHT)
//        {
//            die();
//            return;
//        }

        for (int i = 0; i < handler.getTile().size(); i++) {
            Tile t = handler.getTile().get(i);

            if (t.getId() == Id.redMushroom || t.getId() == Id.greenMushroom || t.getId() == Id.coin || t.getId() == Id.fireFlower  || t.getId() == Id.coin)
                continue;

                if (getBoundsLeft().intersects(t.getBounds()) || getBoundsRight().intersects(t.getBounds())) {
                    die();
                    return;
                }

            if (getBoundsBottom().intersects(t.getBounds())) {
                numberOfCollisions++;


                if(numberOfCollisions==5)
                {
                    die();
                    return;
                }


                jumping = true;
                falling = false;
                gravity = 4.0;
            } else if (!falling && !jumping) {
                falling = true;
                gravity = 1.0;
            }


        }

        for (int i = 0; i < handler.getEntity().size(); i++) {
            Entity e=handler.getEntity().get(i);

            if(e.getId()==Id.goomba || e.getId()==Id.koopa || e.getId()==Id.hedgehog)
            {
                if(getBoundsBottom().intersects(e.getBounds()))
                {
                    e.die();
                    die();
                    return;
                }
            }
        }
        if (jumping) {
            gravity -= .3;
            setVelY((int) -gravity);
            //JOptionPane.showMessageDialog(null,gravity);

            if (gravity <= 0.0) {
                jumping = false;
                falling = true;
            }
        }

        if (falling) {
            gravity += .3;
            setVelY((int) gravity);
        }


    }
}
