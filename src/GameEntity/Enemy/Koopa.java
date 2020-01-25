package GameEntity.Enemy;

import GameEntity.Entity;
import GameTile.Tile;
import Mario.Game;
import Mario.Handler;
import Mario.Id;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Koopa extends Entity {
    private boolean animate = false;
    private boolean isDying = false;
    private int shellCount;
    private int frame = 0, frameDelay = 0, helpFrame, diecounter, facing;

    @Override
    public void setFacing(int facing) {
        this.facing = facing;
    }

    public Koopa(int x, int y, int width, int height, Id id, Handler handler) {
        super(x, y, width, height, id, handler);

        velX = -2;
        koopaState = KoopaState.WALKING;
    }

    @Override
    public void render(Graphics g) {
        if (koopaState != KoopaState.WALKING) {
            //   JOptionPane.showMessageDialog(null,"is snpinn");
            g.drawImage(Game.koopa[4].getBufferedImage(), x, y, width, height, null);
            return;
        }
//        if (isDying) {
//
//            g.drawImage(Game.koopa[4].getBufferedImage(), x, y, width, height, null);
//
//            die();
//
//            return;
//        }

        //go to right
        if (velX > 0)
            g.drawImage(Game.koopa[helpFrame].getBufferedImage(), x, y, width, height, null);

        else
            g.drawImage(Game.koopa[helpFrame + 2].getBufferedImage(), x, y, width, height, null);


        frameDelay++;

        if (frameDelay == 100) {
            helpFrame++;
            frameDelay = 0;
        }

        if (helpFrame == 2)
            helpFrame = 0;


        return;

//   //     if (facing == 0)
//           g.drawImage(Game.goomba[frame + 4].getBufferedImage(), x, y, width, height, null);
//
//        else if (facing == 1)
//            g.drawImage(Game.goomba[frame].getBufferedImage(), x, y, width, height, null);


    }

    public void tick() {
        if (koopaState == KoopaState.SHELL) {
            velX = 0;
            velY = 0;
        }

        x += velX;
        y += velY;

        if (velX != 0)
            animate = true;

        else
            animate = false;


        for (Tile t : handler.getTile()) {
            if (t.getId() == Id.coin)
                continue;

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
                if (koopaState == KoopaState.WALKING)
                    setVelX(2);

                else
                    setVelX(4);
            }

            if (getBoundsRight().intersects((t.getBounds()))) {
                if (koopaState == KoopaState.WALKING)
                    setVelX(-2);

                else
                    setVelX(-4);

            }

        }

        if (falling) {
            gravity += .01;
            setVelY((int) gravity);

        }

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
//
//        if (animate) {
//
//            frameDelay++;
//
//            if (frameDelay >= 3) {
//                frame++;
//
//                if (frame >= 4) {
//                    frame = 0;
//                    frameDelay = 0;
//                }
//
//            }
//        } else if (facing == 1)
//            frame = 0;
//
//        else
//            frame = 3;
        for (int i = 0; i < handler.getEntity().size(); i++)
            if (handler.getEntity().get(i).getId() == Id.koopa && !(handler.getEntity().get(i).equals(this)) && (handler.getEntity().get(i).getBounds().intersects(this.getBounds())) && (handler.getEntity().get(i).getKoopaState() == KoopaState.SPINNING)) {
                die();
                break;
            }

    }
}