package GameEntity;

import GameTile.Tile;
import Mario.Game;
import Mario.Handler;
import Mario.Id;

import javax.swing.*;
import java.awt.*;

public class Player extends Entity {

    private int frame = 0, frameDelay = 0;
    private boolean animate = false;
    //frame delay is the amount of the time the game upddates before it changes the animation

    public Player(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
        super(x, y, width, height, solid, id, handler);
//        velX = 1;
//        velY = -1;
    }


    @Override
    public void render(Graphics g) {
//        g.setColor(Color.BLUE);
//        g.fillRect(x, y, width, height);
        //4 left sided Mario
        if (facing == 0)
            g.drawImage(Game.player[frame + 4].getBufferedImage(), x, y, width, height, null);

        else if (facing == 1)
            g.drawImage(Game.player[frame].getBufferedImage(), x, y, width, height, null);
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;

        if (x <= 0)
            x = 0;

//        if(y<=0)
//            y=0;

        if (x + width >= 1080)
            x = 1080 - width;

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
                        gravity = 0.0;
                        falling = true;
                    }
//                    y=t.getY()+t.getHeight();
                    //BLANK
                }
            }

            if (getBoundsBottom().intersects((t.getBounds()))) {
                setVelY(0);
                y = t.getY() - t.getHeight();
                if (falling)
                    falling = false;
            } else {
                if (!falling && !jumping) {
                    gravity = 0.0;
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

        /**
         * Jumping parabola formula must lie here
         * gravity = acceleration
         */
        if (jumping) {
            gravity -= 0.1;
            setVelY((int) -gravity);
            //JOptionPane.showMessageDialog(null,gravity);

            if (gravity <= 0.0) {
                jumping = false;
                falling = true;
            }
        }

        if (falling) {
            gravity += .01;
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
        } else
            if(facing==1)
            frame = 0;

            else
                frame=3;

    }

    public void setFacing(int f) {
        facing = f;
    }
}
