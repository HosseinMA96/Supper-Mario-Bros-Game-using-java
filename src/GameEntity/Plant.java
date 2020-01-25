package GameEntity;

import Mario.Game;
import Mario.Handler;
import Mario.Id;

import java.awt.*;

public class Plant extends Entity {

    private int wait;
    private int pixelsTraveled = 0,frame=0,frameDelay;

    private boolean moving, insidePipe;

    public Plant(int x, int y, int width, int height, Id id, Handler handler) {
        super(x, y, width, height, id, handler);
        moving = false;
        insidePipe = false;
        velX = 0;


    }

    @Override
    public void render(Graphics g) {

//        g.setColor(Color.red);
//        g.drawRect(x,y,width,height);

        if(!moving && !insidePipe)
        {
            g.drawImage(Game.plant[0].getBufferedImage(),x,y,width,height,null);
            return;
        }

        else
        {
            g.drawImage(Game.plant[frame].getBufferedImage(),x,y,width,height,null);


            frameDelay++;

            if(frameDelay==50)
            {
                frameDelay=0;
                frame++;

                if(frame>1)
                    frame=0;d
            }



            return;

        }
    }

    @Override
    public void tick() {
        y += velY;

        if (!moving)
            wait++;

        if (wait >= 180) {
            if (insidePipe)
                insidePipe = false;

            else
                insidePipe = true;

            moving = true;
            wait = 0;
        }

        if (moving) {
            if (insidePipe)
                setVelY(-3);
            else
                velY = 3;

            pixelsTraveled += velY;

            if (pixelsTraveled >= getHeight() || pixelsTraveled<=-getHeight()) {
                pixelsTraveled = 0;
                moving=false;

                setVelY(0);
            }
        }


    }
}
