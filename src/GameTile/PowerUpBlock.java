package GameTile;

import GameEntity.RedMushroom;
import GameGFX.Sprite;
import Mario.Game;
import Mario.Handler;
import Mario.Id;

import java.awt.*;
import java.util.Random;


public class PowerUpBlock extends Tile {
    private Sprite powerUp;
 //   boolean poppedUp = false;
    private int hitsTaken = 0, spriteY = getY(),coinsGiven;
    private String powerUpName;
    private boolean hasCoin, hasMushroom,change, normalState =true;
    private Random random = new Random();


    public PowerUpBlock(int x, int y, int width, int height, boolean solid, Id id, Handler handler, Sprite powerUp, String powerUpname) {
        super(x, y, width, height, solid, id, handler);
        this.powerUp = Game.dummy;
        this.powerUpName = powerUpname;

        int temp = random.nextInt(100);

        hasCoin = temp > 50;
        hasMushroom = temp > 80;

        hasCoin = true;
        hasMushroom = true;
    }

    public void addHit() {
        hitsTaken++;
        System.out.println("hit = "+hitsTaken);

        if(hitsTaken<4)
        change=true;
    }

    @Override
    public void render(Graphics g) {
        if (!normalState)
            g.drawImage(powerUp.getBufferedImage(), x, spriteY, width, height, null);

        if (!activated)
            g.drawImage(Game.specialBrick.getBufferedImage(), x, y, width, height, null);

        else
        {
            g.drawImage(Game.destroyedSpecialBreak.getBufferedImage(), x, y, width, height, null);
            super.die();
        }



    }


    @Override
    public void tick() {
        if (Game.paused)
            return;
//        if(poppedUp)
//            JOptionPane.showMessageDialog(null,"poppped up");
        if (change) {
            normalState =false;
            System.out.println(false);
         //   change=false;

            if (hasCoin)
            {
                powerUp=Game.coin;
                spriteY -= 6;
            }


            spriteY--;


            //INja farz jardi power up ha faghat gharch and
            if (spriteY < y - height) {
//                JOptionPane.showMessageDialog(null,"pre trig");
                //implement flower

                normalState =true;
                spriteY=y;
                System.out.println(true);



//
//                if (powerUpName.equals("GREEN"))
//                    handler.addEntity(new GreenMushroom(x, spriteY, width, height, Id.greenMushroom, handler));
//
//                if (powerUpName.equals("FLOWER"))
//                    handler.addEntity(new FireFlower(x, spriteY, width, height, Id.fireFlower, handler));

                if (hasCoin) {

                    Game.coins++;
                    coinsGiven++;
                }
                // handler.addTile(new Coin(x,spriteY,width,height,true,Id.coin,handler));
                //   addTile(new Coin(x*64,y*64,64,64,true,Id.coin,this));

//                JOptionPane.showMessageDialog(null,"Triggered");
                change = false;

                if(hasMushroom && coinsGiven==3)
                {
                    spriteY=getY();
                    activated=true;
                    powerUp=Game.redMushroom;
                    handler.addEntity(new RedMushroom(x, spriteY, width, height, Id.redMushroom, handler));
                }
            }


        }

    }

    public boolean isActivated() {
        return activated;
    }
}
