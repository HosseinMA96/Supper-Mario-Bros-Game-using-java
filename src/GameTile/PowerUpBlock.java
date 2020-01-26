package GameTile;

import GameEntity.GreenMushroom;
import GameEntity.RedMushroom;
import GameGFX.Sprite;
import Mario.Game;
import Mario.Handler;
import Mario.Id;

import java.awt.*;



public class PowerUpBlock extends Tile{
    private Sprite powerUp;
    boolean poppedUp=false;
    private int hitsTaken=0,spriteY=getY();
    String powerUpName;


    public PowerUpBlock(int x, int y, int width, int height, boolean solid, Id id, Handler handler, Sprite powerUp,String powerUpname) {
        super(x, y, width, height, solid, id, handler);
        this.powerUp=powerUp;
        this.powerUpName=powerUpname;
    }
    public void addHit()
    {
        hitsTaken++;
    }

    @Override
    public void render(Graphics g)
    {
        if(poppedUp==false)
            g.drawImage(powerUp.getBufferedImage(),x,spriteY,width,height,null);

        if(!activated)
            g.drawImage(Game.powerUp.getBufferedImage(),x,y,width,height,null);

        else
            g.drawImage(Game.usedPowerUp.getBufferedImage(),x,y,width,height,null);


    }


    public void setActivated(boolean b)
    {
        activated=b;
    }

    @Override
    public void tick()
    {
        if(Game.paused)
            return;
//        if(poppedUp)
//            JOptionPane.showMessageDialog(null,"poppped up");
        if(activated && !poppedUp)
        {
            if(powerUpName.equals("COIN"))
                spriteY-=6;

            spriteY--;


            //INja farz jardi power up ha faghat gharch and
            if(spriteY<y-height){
//                JOptionPane.showMessageDialog(null,"pre trig");
                //implement flower

                if(powerUpName.equals("RED"))
                handler.addEntity(new RedMushroom(x,spriteY,width,height,Id.redMushroom,handler));

                if (powerUpName.equals("GREEN"))
                    handler.addEntity(new GreenMushroom(x,spriteY,width,height,Id.greenMushroom,handler));

                if(powerUpName.equals("FLOWER"))
                    handler.addEntity(new FireFlower(x,spriteY,width,height,Id.fireFlower,handler));

                if(powerUpName.equals("COIN"))
                {
                    Game.coins++;
                }
                   // handler.addTile(new Coin(x,spriteY,width,height,true,Id.coin,handler));
             //   addTile(new Coin(x*64,y*64,64,64,true,Id.coin,this));

//                JOptionPane.showMessageDialog(null,"Triggered");
                poppedUp=true;
            }


        }

    }

    public boolean isActivated()
    {
        return  activated;
    }
}
