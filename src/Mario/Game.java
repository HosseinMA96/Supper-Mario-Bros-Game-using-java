package Mario;

import GameEntity.Entity;
import GameEntity.Player;
import GameGFX.Sprite;
import GameGFX.SpriteSheet;
import GameTile.Tile;
import GameTile.Wall;
import Mario.Input.KeyInput;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;

public class Game extends Canvas implements Runnable {


   // public static final int WITDH = 240;
    public static final int WITDH = 270;
  //  public static final int HEIGHT = WITDH / 14 * 10;
    //Height is y
  public static final int HEIGHT = WITDH / 16 * 10;
    public static final int SCALE = 4;
    public static final String TITLE = "Super Mario Bros (Hossein & Mammad)";
    public static  SpriteSheet sheet;
    public static Handler handler;
    public static Sprite grass,greenMushroom,redMushroom,powerUp,usedPowerUp;
    public static Sprite player[][]=new Sprite[3][8];//first index is status, second is frame
    public static Camera cam;
    public static Sprite[] goomba=new Sprite[8];
    private BufferedImage image;
    private int numberOfLives=3;

    //he 10 you 10

    private Thread thread;
    private boolean running = false;

    private void init()
    {
        handler=new Handler();
        sheet=new SpriteSheet("C:\\Users\\erfan\\Desktop\\dummy\\res\\spritesheet.png");


        cam=new Camera();
        addKeyListener(new KeyInput());

        grass=new Sprite(sheet,1,1);
        redMushroom=new Sprite(sheet,2,1);
        greenMushroom=new Sprite(sheet,1,1);

     //   player=new Sprite(sheet,1,1);

        for (int i=0;i<8;i++){
            player[1][i]=new Sprite(sheet,i+1,16);
            player[0][i]=new Sprite(sheet,i+9,16);
            player[2][i]=new Sprite(sheet,i+1,15);

        }

        goomba[0]=new Sprite(sheet,1,14);
        goomba[1]=new Sprite(sheet,2,14);
        goomba[2]=new Sprite(sheet,3,14);


        powerUp=new Sprite(sheet,4,14);
        usedPowerUp=new Sprite(sheet,5,14);






       // handler.addEntity(new Player(300,512,64,64,true,Id.player1,handler));
       // handler.addTile(new Wall(200,200,64,64,true,Id.wall,handler));

        try {
            image = ImageIO.read(new File("C:\\Users\\erfan\\Desktop\\dummy\\res\\level1.png"));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        handler.createLevel(image);
    }

    private synchronized void start() {
        if (running)
            return;

        running = true;
        thread = new Thread(this);
        thread.start();


    }

    private synchronized void stop() {
        if (!running)
            return;

        running = false;

        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        init();
        requestFocus();
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        double delta = 0.0;
        double ns = 1000000000.0 / 60.0;
        int frames = 0;
        int ticks = 0;

        while (running) {
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
        stop();
    }

    /**
     * To draw and display
     * We have Three buffer strategy
     */
    public void render() {
        BufferStrategy bs=getBufferStrategy();

        if(bs==null)
        {
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

        Graphics g=bs.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0,0,getWidth(),getHeight());

        //move camera
        g.translate(cam.getX(),cam.getY());
        handler.render(g);
        g.dispose();
        bs.show();

    }

    /**
     * To update
     */
    public void tick() {
        handler.tick();

        for (Entity e:handler.getEntity()){
            if(e.getId()==Id.player1)
            {
                cam.tick(e);
            }
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
    }

    public int getFrameWidth()
    {
        return WIDTH*SCALE;
    }

    public int getFrameHeight()
    {
        return HEIGHT*SCALE;
    }
}