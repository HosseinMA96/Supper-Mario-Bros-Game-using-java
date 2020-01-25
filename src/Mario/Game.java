package Mario;
//27 boss
//23 , 24 pipes
//28, Menus and hframe and icons
//30 Drawing and ticking optimization
//31 & 32 koopas
//34 Plants!
//ye marhale ham oon parcham va raftan marhale baad
//37 Soundswwwww
//38 Background levelsImage
//39 Fire balls

import GameEntity.Entity;
import GameGFX.Sprite;
import GameGFX.SpriteSheet;
import Mario.Input.KeyInput;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.System.exit;

public class Game extends Canvas implements Runnable {


    // public static final int WITDH = 240;
    public static final int WITDH = 270;
    //  public static final int HEIGHT = WITDH / 14 * 10;
    //Height is y
    public static final int HEIGHT = WITDH / 16 * 10;
    public static final int SCALE = 4;
    public static final String TITLE = "Super Mario Bros (Hossein & Mammad)";
    public static SpriteSheet sheet;
    public static Handler handler;
    public static Sprite grass, greenMushroom, redMushroom, powerUp, usedPowerUp, pipeBody, coin, castleBrick, castleDoor,prince,fireBall,fireFlower;
    public static Sprite player[][] = new Sprite[3][12];//first index is status, second is frame
    public static Camera cam;
    public static Sprite[] goomba = new Sprite[8], koopa = new Sprite[8],plant=new Sprite[2];
    private ArrayList<BufferedImage> levelsImage = new ArrayList<>();
    private static int deathScreenTime = 0, gameOverTicks, numberOfMaps = 2, currentLevel = 0;
    public static int coins, lives = 3;
    public static boolean startNext=false,totallyFinished=false;
   // private sboolean totallyFinished=false;

    //he 10 you 10

    private Thread thread;
    private static boolean running = false;
    public static boolean showDeathScreen = true, gameOver = false;

    private void init() {
        handler = new Handler();
        sheet = new SpriteSheet("C:\\Users\\erfan\\Desktop\\dummy\\res\\spritesheet.png");


        cam = new Camera();
        addKeyListener(new KeyInput());

        grass = new Sprite(sheet, 1, 1);
        redMushroom = new Sprite(sheet, 2, 1);
        greenMushroom = new Sprite(sheet, 3, 1);

        //   player=new Sprite(sheet,1,1);

        for (int i = 0; i < 8; i++) {
            player[1][i] = new Sprite(sheet, i + 1, 16);
            player[0][i] = new Sprite(sheet, i + 9, 16);
            player[2][i] = new Sprite(sheet, i + 1, 15);

        }

        //sitting faced right 9. small mario cannot sit
        player[1][8] = new Sprite(sheet, 6, 14);
        player[2][8] = new Sprite(sheet, 15, 15);

        //sitting faced left 10. small mario cannot sit
        player[1][9] = new Sprite(sheet, 7, 14);
        player[2][9] = new Sprite(sheet, 16, 15);

        //Jumping to right 10
        player[0][10] = new Sprite(sheet, 9, 15);
        player[1][10] = new Sprite(sheet, 10, 15);
        player[2][10] = new Sprite(sheet, 11, 15);

        //Jumping to left 11
        player[0][11] = new Sprite(sheet, 12, 15);
        player[1][11] = new Sprite(sheet, 13, 15);
        player[2][11] = new Sprite(sheet, 14, 15);


        coin = new Sprite(sheet, 8, 14);

        goomba[0] = new Sprite(sheet, 1, 14);
        goomba[1] = new Sprite(sheet, 2, 14);
        goomba[2] = new Sprite(sheet, 3, 14);

        koopa[0] = new Sprite(sheet, 1, 12);
        koopa[1] = new Sprite(sheet, 2, 12);
        koopa[2] = new Sprite(sheet, 3, 12);
        koopa[3] = new Sprite(sheet, 4, 12);

        //spinning
        koopa[4] = new Sprite(sheet, 5, 12);


        powerUp = new Sprite(sheet, 4, 14);
        usedPowerUp = new Sprite(sheet, 5, 14);

        pipeBody = new Sprite(sheet, 2, 13);

        castleBrick = new Sprite(sheet, 4, 1);
        castleDoor = new Sprite(sheet, 5, 1);
        prince=new Sprite(sheet,6,1);

        plant[0]=new Sprite(sheet, 7, 12);
        plant[1]=new Sprite(sheet, 6, 12);

        // pipeHead=new Sprite(sheet,1,13);
        fireBall=new Sprite(sheet,7,1);
        fireFlower=new Sprite(sheet,8,1);


        // handler.addEntity(new Player(300,512,64,64,true,Id.player1,handler));
        // handler.addTile(new Wall(200,200,64,64,true,Id.wall,handler));

        if(currentLevel<numberOfMaps)
         handler.createLevel(levelsImage.get(currentLevel));
        else
            JOptionPane.showMessageDialog(null,"All levels finished");

    }

    private synchronized void start() {
//        if(!running)
//            return;

        if (running)
            return;

        running = true;
        thread = new Thread(this);
        thread.start();


    }

    private synchronized void stop() {
   //     System.out.println("in stop startNext is "+startNext);

        if (!running)
            return;

      //  System.out.println("after return stop");

        running = false;

        try {
            thread.join();
            System.out.println("afterJoined");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addCoint() {
        coins++;
    }

    @Override
    public void run() {
        createLevels(numberOfMaps);
        while(!totallyFinished) {
            System.out.println("in mother loop with current map "+currentLevel);

            if(currentLevel==numberOfMaps)
                break;

            init();
            requestFocus();
            long lastTime = System.nanoTime();
            long timer = System.currentTimeMillis();
            double delta = 0.0;
            double ns = 1000000000.0 / 50;
            int frames = 0;
            int ticks = 0;
            running=true;
            showDeathScreen=true;

            while (running) {

             //   System.out.println(handler.getTile().size());

                //      System.out.println("im in game loop");

                //  System.out.println(currentLevel);

                long now = System.nanoTime();
                delta += (now - lastTime) / ns;
                lastTime = now;

                while (delta > -1) {
                    tick();
                    ticks++;
                    delta--;
                }

                render();
                frames++;

                if (System.currentTimeMillis() - timer > 1000) {
                    timer += 1000;
                    //    System.out.println(frames + "  Frames Per Second " + ticks + " Updates Per Second");
                    frames = 0;
                    ticks = 0;
                }
            }
        }
            stop();

    }

    /**
     * To draw and display
     * We have Three buffer strategy
     */
    public void render() {
        BufferStrategy bs = getBufferStrategy();

        if (bs == null) {
            createBufferStrategy(3);
            return;
        }


//        Graphics g=bs.getDrawGraphics();
//        g.setColor(Color.BLACK);
//        g.fillRect(0,0,getWidth(),getHeight());
//
//        g.setColor(Color.RED);
//        g.fillRect(200,200,getWidth()-400,getHeight()-400);
//        g.dispose();
//        bs.show();

        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        //handle gameOver
        if (gameOver) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Courier", Font.BOLD, 50));
            g.drawString("Game over", 610, 400);

            long currentTime = System.currentTimeMillis();

            gameOverTicks++;


            if (gameOverTicks == 500)
                exit(0);

        }


        //handle Coinds
        if (!showDeathScreen && !gameOver) {
            g.drawImage(coin.getBufferedImage(), 20, 20, 30, 30, null);
            g.setColor(Color.BLUE.WHITE);
            g.setFont(new Font("Courier", Font.BOLD, 20));
            g.drawString("x" + coins, 46, 46);

        } else if (!gameOver) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Courier", Font.BOLD, 30));
            g.drawImage(Game.player[0][0].getBufferedImage(), 500, 300, 100, 100, null);

            g.drawString("x" + lives, 600,400);
            g.drawString("Level "+ currentLevel+1,500,280);
        }


        //move camera
        g.translate(cam.getX(), cam.getY());

        if (!showDeathScreen)
            handler.render(g);

        g.dispose();
        bs.show();

    }

    /**
     * To update
     */

    public void tick() {
        handler.tick();


        for (Entity e : handler.getEntity()) {
            if (e.getId() == Id.player1) {
                if (!e.getGoingDownPipe())
                    cam.tick(e);
            }
        }

        //showDeathScreen=false;
        if (showDeathScreen)
            deathScreenTime++;

        //frames * 3 seconds
        if (deathScreenTime == 180) {
            showDeathScreen = false;
            deathScreenTime = 0;
            handler.clearLevel();
            handler.createLevel(levelsImage.get(currentLevel));
        }


    }

    public Game() {
        Dimension size = new Dimension(WITDH * SCALE, HEIGHT * SCALE);
        setPreferredSize(size);
        setMaximumSize(size);
        setMinimumSize(size);
    }

    public static Handler getHandler() {
        return handler;
    }


    public static void main(String[] args) {
        Game game = new Game();
        JFrame frame = new JFrame(TITLE);
        frame.add(game);
        frame.pack();
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        game.start();

        //System.out.println("After start");



//        while (true) {
//           JOptionPane.showMessageDialog(null,"in while true");
//
//            while(!startNext){
//             //   System.out.println(startNext);
//
//            }

//            System.out.println(startNext);
//
//
//
//            JOptionPane.showMessageDialog(null,"passed first");
//
//            if (Game.currentLevel == Game.numberOfMaps) {
//                JOptionPane.showMessageDialog(null, "Game has finished !");
//                break;
//            }
//            JOptionPane.showMessageDialog(null,"passed second");
//            frame.remove(game);
//             game = new Game();frame = new JFrame(TITLE);
//            frame.add(game);
//            frame.pack();
//            frame.setResizable(true);
//            frame.setLocationRelativeTo(null);
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.setVisible(true);
//            game.start();
//
//
//            startNext=false;
//        }

    }

    public static void setRunning() {
        running = true;
    }

    public static int getFrameWidth() {
        return WIDTH * SCALE;
    }

    public static Rectangle getVisibleArea() {
        for (int i = 0; i < handler.getEntity().size(); i++) {
            Entity e = handler.getEntity().get(i);

            if (e.getId() == Id.player1) {
                return new Rectangle(e.getX() - (getFrameWidth() / 2 - 5), e.getY() - (getFrameHeight()), getFrameWidth() + 10, getFrameHeight() + 10);
            }
        }

        return null;
    }

    public static int getFrameHeight() {
        return HEIGHT * SCALE;
    }

    public static void goNextLevel() {
      //  System.out.println("in next level method");

      //  System.out.println("current level"+ currentLevel);
      //  System.out.println("number of maps "+numberOfMaps);

        if (currentLevel == numberOfMaps) {
//            JOptionPane.showMessageDialog(null,"Game has finished !");
            running = false;
            totallyFinished=true;
            //Handle endgame
            return;

        } else {
            currentLevel++;
            running = false;

        }

    }

    public static boolean isRunning() {
        return running;
    }

    public void createLevels(int numberOfMaps) {
        for (int i = 0; i < numberOfMaps; i++) {
            try {
                BufferedImage bf;
                bf = ImageIO.read(new File("C:\\Users\\erfan\\Desktop\\dummy\\res\\level" + (i + 1) + ".png"));
                levelsImage.add(bf);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Unable to find map number" + i + 1);
            }
        }
    }
}