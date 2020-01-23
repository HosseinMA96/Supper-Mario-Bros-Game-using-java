package GameEntity;

import GameEntity.Enemy.Goomba;
import GameTile.PowerUpBlock;
import GameTile.Tile;
import Mario.Game;
import Mario.Handler;
import Mario.Id;

import javax.swing.*;
import java.awt.*;

public class Player extends Entity {

    private int frame = 0, frameDelay = 0;
    private boolean animate = false;
    private int status = 0; //status is mario size, 0 for small, 1 for medium and 2 for fire mario
    //frame delay is the amount of the time the game upddates before it changes the animation

    public Player(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
        super(x, y, width, height, id, handler);
//        velX = 1;
//        velY = -1;
    }


    @Override
    public void render(Graphics g) {

        if(jumping)
        {
            
        }

        if (facing == 0)
            g.drawImage(Game.player[status][frame + 4].getBufferedImage(), x, y, width, height, null);

        else if (facing == 1)
            g.drawImage(Game.player[status][frame].getBufferedImage(), x, y, width, height, null);
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;

//        if (x <= 0)
//            x = 0;

//        if(y<=0)
//            y=0;

//        if (x + width >= 1080)
//            x = 1080 - width;

        if (y + height >= 775)
            y = 775 - height;

        if (velX != 0)
            animate = true;

        else
            animate = false;

        //Ehtemalan shekaste shodan ro inja bayad begi dawsh
        for (Tile t : handler.getTile()) {
            if (!t.getSolid())
                break;

            if (t.getId() == Id.wall) {
                if (getBoundsTop().intersects(t.getBounds())) {
                    setVelY(0);

                    if (jumping) {
                        jumping = false;
                        //  gravity = 0.8;
                        gravity = 2;
                        falling = true;
                    }


//                    y=t.getY()+t.getHeight();
                    //BLANK
                }
            }

            if (t.getId() == Id.powerUp) {
                if (getBoundsTop().intersects(t.getBounds()) && !((PowerUpBlock) t).isActivated()) {
                    if (jumping) {
                        jumping = false;
                        //  gravity = 0.8;
                        gravity = 2;
                        falling = true;
                    }
                    //   JOptionPane.showMessageDialog(null,"set actv");
                    ((PowerUpBlock) t).setActivated(true);
                    //  JOptionPane.showMessageDialog(null,"hit");
                }


            }


            if (getBoundsBottom().intersects((t.getBounds()))) {
                setVelY(0);
                y = t.getY() - t.getHeight();
                if (falling)
                    falling = false;
            } else {
                if (!falling && !jumping) {
                    //   gravity = 0.8;
                    gravity = 2;
                    falling = true;
                }
            }


            if (getBoundsLeft().intersects((t.getBounds()))) {
                setVelX(0);
                x = t.getX() + t.getWidth();
            }

            if (getBoundsRight().intersects((t.getBounds()))) {
                setVelX(0);
                x = t.getX() - t.getWidth();
            }

        }


        for (int i = 0; i < handler.getEntity().size(); i++) {
            Entity e = handler.getEntity().get(i);

            if (e.getId() == Id.redMushroom) {
                if (getBounds().intersects(e.getBounds())) {
                    status++;

                    if (status == 3)
                        status = 2;

                    e.die();
                }


            }

            if (e.getId() == Id.goomba) {

                if (getBoundsBottom().intersects(e.getBoundsTop())) {
                    e.die();
                } else if (getBounds().intersects(e.getBounds())) {

                    //PLAYER INTERSECT WITH GOOMBA
                    die();
                }
            }
        }
        /**
         * Jumping parabola formula must lie here
         * gravity = acceleration
         */
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

        if (animate) {

            frameDelay++;

            if (frameDelay >= 3) {
                frame++;

                if (frame >= 4) {
                    frame = 0;
                }
                frameDelay = 0;
            }
        } else if (facing == 1)
            frame = 0;

        else
            frame = 3;

    }

    public void setFacing(int f) {
        facing = f;
    }
}
