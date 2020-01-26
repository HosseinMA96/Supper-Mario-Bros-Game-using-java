package GameEntity;

import GameEntity.Enemy.KoopaState;
import GameTile.PowerUpBlock;
import GameTile.Tile;
import Mario.Game;
import Mario.Handler;
import Mario.Id;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Player extends Entity {
    private Random random;

    private int frame = 0, frameDelay = 0, safeClocks, pixelsTraveled = 0;
    private boolean animate = false, sit;
    private int status = 0;//status is mario size, 0 for small, 1 for medium and 2 for fire mario
    //frame delay is the amount of the time the game upddates before it changes the animation
    //if face ==0 faced left

    public Player(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
        super(x, y, width, height, id, handler);
//        velX = 1;
        random = new Random();
//        velY = -1;
    }


    @Override
    public void render(Graphics g) {

        if (jumping) {
            g.drawImage(Game.player[status][11 - facing].getBufferedImage(), x, y, width, height, null);
            return;
        }

        if (sit && status > 0) {
            //  System.out.println("in sitting wtf");
            g.drawImage(Game.player[status][9 - facing].getBufferedImage(), x, y, width, height, null);
            return;
        }

        if (facing == 0)
            g.drawImage(Game.player[status][frame + 4].getBufferedImage(), x, y, width, height, null);

        else if (facing == 1)
            g.drawImage(Game.player[status][frame].getBufferedImage(), x, y, width, height, null);
    }


    @Override
    public void tick() {

        if(Game.paused)
            return;


        if(Game.isRunning()==false)
            return;

        x += velX;

        if(x<-Game.cam.getLastX())
            x=-Game.cam.getLastX();

        y += velY;

      //  System.out.println("last x "+Game.cam.getLastX());
       // System.out.println("mario X "+x);

        if (goingDownPipe)
            pixelsTraveled += velY;


//        if (x <= 0)
//            x = 0;

//        if(y<=0)
//            y=0;

//        if (x + width >= 1080)
//            x = 1080 - width;

//        if (y + height >= 775)
//            y = 775 - height;

        if (velX != 0)
            animate = true;

        else
            animate = false;

        //Ehtemalan shekaste shodan ro inja bayad begi dawsh
        //  for (Tile t : handler.getTile()) {
        for (int i = 0; i < handler.getTile().size(); i++) {
            Tile t = handler.getTile().get(i);
            if (!t.getSolid() || goingDownPipe || t.getId()==Id.casteBrick)
                continue;

            //next level
            if (t.getId()==Id.prince || t.getId() == Id.castleDoor && getBounds().intersects(t.getBounds())) {
                Game.goNextLevel();
             //   JOptionPane.showMessageDialog(null,"next level");
                return;
            }
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


            if (getBoundsBottom().intersects((t.getBounds())) && t.getId() != Id.coin) {


                setVelY(0);

                int k = 1;

                if (t.getId() == Id.pipe)
                    y = t.getY() - 64;

                else
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


            if (getBoundsLeft().intersects((t.getBounds())) && t.getId() != Id.coin) {
                setVelX(0);
                x = t.getX() + t.getWidth();
            }

            if (getBoundsRight().intersects((t.getBounds())) && t.getId() != Id.coin) {
                setVelX(0);
                x = t.getX() - t.getWidth();
            }

            if (getBounds().intersects(t.getBounds()) && t.getId() == Id.coin) {
                Game.coins++;
                t.die();
            }


        }


        for (int i = 0; i < handler.getEntity().size(); i++) {
            Entity e = handler.getEntity().get(i);

            if (e.getId() == Id.redMushroom) {
                if (getBounds().intersects(e.getBounds())) {
//                    status++;
//
//                    if (status == 3)
//                        status = 2;
//                    inflict();
                    growUp();

                    e.die();
                }


            }

            if(e.getId()==Id.hedgehog)
            {
                if(getBounds().intersects(e.getBounds()))
                {
                    inflict();
                    e.die();
                }
            }

            if (e.getId() == Id.fireFlower) {
                if (getBounds().intersects(e.getBounds())) {
//                    status++;
//
//                    if (status == 3)
//                        status = 2;
                    growUp();
                    Game.fireBalls =5;

                    e.die();
                }


            }

            if (e.getId() == Id.greenMushroom) {
                if (getBounds().intersects(e.getBounds())) {
                    Game.lives++;

                    e.die();
                }


            }

            if (e.getId() == Id.goomba || e.getId()==Id.plant) {

                if (getBoundsBottom().intersects(e.getBoundsTop()) && e.getId()!=Id.plant) {
                    e.die();
                } else if (getBounds().intersects(e.getBounds())) {

                    //PLAYER INTERSECT WITH GOOMBA

//                    if (safeClocks == 0)
//                    status--;
//
////                    safeClocks++;
////
////                    if (safeClocks == 30)
////                        safeClocks = 0;
//
//                    if (status == -1)
//                        die();

                    inflict();

                    e.die();


                }
            }

            if (e.getId() == Id.koopa) {
                if (e.koopaState == KoopaState.WALKING) {
                    if (getBoundsBottom().intersects(e.getBoundsTop())) {
                        e.koopaState = KoopaState.SHELL;
                        jumping = true;
                        falling = false;
                        gravity = 10;


                    } else if (getBounds().intersects(e.getBounds())) {
                        if (safeClocks == 0)
//                            status--;
//
//
//                        if (status == -1)
//                            die();

                            inflict();

                        e.die();
                    }

                    continue;

                }

                if (e.koopaState == KoopaState.SHELL) {
                    if (getBoundsBottom().intersects(e.getBoundsTop())) {
                        e.die();

                    }

                    if (getBoundsLeft().intersects(e.getBoundsRight())) {

                        e.velX = -6;
                        e.koopaState = KoopaState.SPINNING;
                        //   System.out.println("go left t");
                        gravity = 2;
                        continue;

                    } else if (getBoundsRight().intersects(e.getBoundsLeft())) {
                        e.velX = 6;
                        e.koopaState = KoopaState.SPINNING;
                        //  System.out.println("go right t");
                        gravity = 2;
                        continue;
                    }
                    continue;

                }

                if (e.koopaState == KoopaState.SPINNING) {
                    if (getBoundsBottom().intersects(e.getBoundsTop())) {
                        e.die();
                        continue;
                    }

//                    } else if (getBounds().intersects(e.getBounds())) {
//                        if (safeClocks == 0)
//                            status--;
//
////
//
//                        if (status == -1)
//                            die();
//
//                        e.die();
//                    }
                }
            }
        }


        /**
         * Jumping parabola formula must lie here
         * gravity = acceleration
         */
        if (jumping && !goingDownPipe) {
            gravity -= .3;
            setVelY((int) -gravity);
            //JOptionPane.showMessageDialog(null,gravity);

            if (gravity <= 0.0) {
                jumping = false;
                falling = true;
            }
        }

        if (falling && !goingDownPipe) {
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

        if (goingDownPipe) {
            JOptionPane.showMessageDialog(null, "going Down");
            for (int i = 0; i < Game.handler.getTile().size(); i++) {
                Tile t = Game.handler.getTile().get(i);

                if (t.getId() == Id.pipe) {
                    if (getBoundsBottom().intersects(t.getBounds())) {
                        switch (t.getFacing()) {
                            case 0:
                                JOptionPane.showMessageDialog(null, "sefr");
                                setVelY(-2);
                                setVelX(0);
                                break;

                            case 2:
                                JOptionPane.showMessageDialog(null, "Do");
                                setVelY(2);
                                setVelX(0);
                                break;
                        }

                        if (pixelsTraveled > t.getHeight() + height)
                            goingDownPipe = false;
                    }

                }

            }
        }

    }

    public void setSit(boolean sit) {
        this.sit = sit;
    }

    private void inflict()
    {
        status--;


        if(status==-1)
        {
            die();
            status=0;
        }


    }

    private void growUp()
    {
        status++;
        if(status==3)
            status=2;

        //or increase lives?
    }

    public int getStatus() {
        return status;
    }

    public void setFacing(int f) {
        facing = f;
    }
}
