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
    private int hitsTaken = 0, spriteY = getY(),coinsGiven,deathCounter=0,safeCounter=0;
    private String powerUpName;
    private boolean hasCoin, hasMushroom,change=false, normalState =true,activeHelper=false;
    private Random random = new Random();


    public PowerUpBlock(int x, int y, int width, int height, boolean solid, Id id, Handler handler, Sprite powerUp, String powerUpname) {
        super(x, y, width, height, solid, id, handler);
        this.powerUp = Game.dummy;
        this.powerUpName = powerUpname;



        long temp = System.currentTimeMillis()%2;
        hasCoin = temp >0;

        hasCoin=false;

       temp= random.nextInt(100);
        hasMushroom=temp>80;

        hasMushroom=true;

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

         if(hitsTaken>=3)
        {
            System.out.println("in destoryed "+activated+" "+hitsTaken);
            g.drawImage(Game.destroyedSpecialBreak.getBufferedImage(), x, y, width, height, null);
            deathCounter++;

            if(deathCounter==60)
            super.die();
        }



    }


    @Override
    public void tick() {
        safeCounter++;

        if(safeCounter>60)
            safeCounter=60;


        if (Game.paused)
            return;
//        if(poppedUp)
//            JOptionPane.showMessageDialog(null,"poppped up");
        if (change) {

            normalState =false;
       //     System.out.println(false);
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
             //   System.out.println(true);



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

                if((hasMushroom && coinsGiven==3) || (hasMushroom && hasCoin==false))
                {
                    spriteY=getY();
                    activated=true;
                    powerUp=Game.redMushroom;
                    handler.addEntity(new RedMushroom(x, spriteY, width, height, Id.redMushroom, handler));
                }

                else if(hitsTaken==3)
                {
                    activated=true;
                }
            }


        }

    }

    public boolean isActivated() {
        return activated;
    }
}
