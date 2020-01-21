package Mario;

import GameEntity.Player;
import GameTile.Wall;
import Mario.Input.KeyInput;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {

    public static final int WITDH = 270;
    public static final int HEIGHT = WITDH / 14 * 10;
    public static final int SCALE = 4;
    public static final String TITLE = "Super Mario Bros (Hossein & Mammad)";
    public static Handler handler;

    private Thread thread;
    private boolean running = false;

    private void init()
    {
        handler=new Handler();
        addKeyListener(new KeyInput());
        handler.addEntity(new Player(300,512,64,64,true,Id.player1,handler));
       // handler.addTile(new Wall(200,200,64,64,true,Id.wall,handler));
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
                System.out.println(frames + "  Frames Per Second " + ticks + " Updates Per Second");
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
        handler.render(g);
        g.dispose();
        bs.show();

    }

    /**
     * To update
     */
    public void tick() {
        handler.tick();
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
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        game.start();
    }
}