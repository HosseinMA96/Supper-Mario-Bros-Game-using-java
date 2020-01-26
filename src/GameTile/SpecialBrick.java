package GameTile;

import Mario.Game;
import Mario.Handler;
import Mario.Id;

import java.awt.*;
import java.util.Random;

public class SpecialBrick extends Tile {
    private boolean mustDie = false, hasCoin, hasMushroom;
    private Random random = new Random();
    private int hitsTaken=0;

    public SpecialBrick(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
        super(x, y, width, height, solid, id, handler);


        int temp=random.nextInt(100);

        hasCoin=temp>50;
        hasMushroom=temp>80;

        hasCoin=true;
        hasMushroom=true;

    }

    @Override
    public void render(Graphics g) {

        if (mustDie) {
//            g.drawImage(Game.destroyedBrick.getBufferedImage(), x, y, width, height, null);
            super.die();
            return;
        }

        g.drawImage(Game.ordinaryBrick.getBufferedImage(), getX(), getY(), width, height, null);


    }

    @Override
    public void tick() {

    }

    @Override
    public void die() {
        mustDie = true;
    }

    public void hit()
    {
        hitsTaken++;

        if(hasCoin)
        {
            PowerUpBlock powerUpBlock=new PowerUpBlock(x,y,width,height,true,Id.powerUp,handler,Game.coin,"COIN");
         //   powerUpBlock.setActivated(true);
            powerUpBlock.tick();
//         sdsaasd
        }

    }
}


