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
        super(x, y, width, height, id, handler,0);

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

        handler.fireBallX.add(x);
        handler.fireBallY.add(y);

        x+=velX;
        y+=velY;

//        System.out.println("Game cam lastx "+Game.cam.getLastX());
//        System.out.println("Game cam getY "+Game.cam.getY());
//        System.out.println("x "+x);
//        System.out.println("Y "+y);

        //|| y>Math.abs(Game.cam.getY())+400 || y<Math.abs(Game.cam.getY())-400

        int lastY=Math.abs(Game.cam.getY());

        if(x<Math.abs(Game.cam.getLastX()) || x>Math.abs(Game.cam.getLastX())+1400 ||   y>Game.cam.getPlayerY()+400 ||  y<Game.cam.getPlayerY()-400 )
        {
            System.out.println(lastY);
            System.out.println(y);
            System.out.println();
            Player.liveFireBalls--;
            die();
            return;
        }

        for (int i = 0; i < handler.getTile().size(); i++) {
            Tile t = handler.getTile().get(i);

            if(t.getSolid()==false)
                continue;

            if (t.getId() == Id.redMushroom || t.getId() == Id.greenMushroom || t.getId() == Id.coin || t.getId() == Id.fireFlower  || t.getId() == Id.coin)
                continue;

                if (getBoundsLeft().intersects(t.getBounds()) || getBoundsRight().intersects(t.getBounds())) {
                    die();
                    Player.liveFireBalls--;
                    return;
                }

            if (getBoundsBottom().intersects(t.getBounds())) {
                numberOfCollisions++;


                if(numberOfCollisions==5)
                {
                    die();
                    Player.liveFireBalls--;
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
                    Player.liveFireBalls--;
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
