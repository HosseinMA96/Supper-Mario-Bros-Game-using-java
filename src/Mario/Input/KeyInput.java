package Mario.Input;

import GameEntity.Entity;
import GameEntity.Player;
import GameTile.Tile;
import Mario.Game;
import Mario.Id;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener {
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        for (Entity en : Game.getHandler().getEntity()) {

            if (en.getId() != Id.player1)
                continue;

//            if (en.getJumping())
//                continue;
            if(en.getGoingDownPipe())
                return;

            switch (key) {

                case KeyEvent.VK_W:
//                    en.setVelY(-5);
                    if (!en.getJumping()  ) {
                        en.setJumping(true);
                        en.setGravity(10.0);
                    }


                    break;

                case KeyEvent.VK_S:
                    ((Player)en).setSit(true);

                    for (int q=0;q<Game.handler.getTile().size();q++)
                    {
                        Tile t=Game.getHandler().getTile().get(q);

                        if(t.getId()==Id.pipe){
//                            System.out.println("mario : "+en.getBoundsBottom());
//                            System.out.println("pipe : "+t.getBounds());
                          //  System.out.println();
                            if(en.getBoundsBottom().intersects(t.getBounds()))
                            {
                                JOptionPane.showMessageDialog(null,"intersect");
                                if(en.getGoingDownPipe() != true)
                                    en.setGoingDownPipe(true);

                            }
                        }
                    }

                    break;


//
//                case KeyEvent.VK_S:
//                    en.setVelY(5);
//                    break;


                case KeyEvent.VK_D:

//                    if(en.getJumping())
//                        return;


                    en.setVelX(5);
                    en.setFacing(1);
                    break;

                case KeyEvent.VK_A:
//                    if(en.getJumping())
//                        return;

                    en.setFacing(0);
                    en.setVelX(-5);
                    break;

            }


        }


    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();


        for (Entity en : Game.getHandler().getEntity()) {
            if (en.getId() == Id.player1) {
//                if(en.getJumping())
//                    continue;

                switch (key) {


                    case KeyEvent.VK_W:
                        en.setVelY(0);
                        break;


                    case KeyEvent.VK_S:
                        en.setVelY(0);
                        ((Player)en).setSit(false);
                        break;


                    case KeyEvent.VK_D:
                        en.setVelX(0);
                        break;

                    case KeyEvent.VK_A:
                        en.setVelX(0);
                        break;




                }
            }


        }


    }

    @Override
    public void keyTyped(KeyEvent e) {
        //For cheats
    }
}
