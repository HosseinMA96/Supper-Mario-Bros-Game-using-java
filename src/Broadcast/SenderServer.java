package Broadcast;
//Aproach one. First we do not seprate destroyable tile and non-destroyable tile. deleting takes longer time of course. for ticking and drawing both

import GameEntity.Enemy.*;
import GameEntity.Entity;
import GameEntity.FireBall;
import GameEntity.Player;
import GameEntity.RedMushroom;
import GameTile.Brick;
import GameTile.Coin;
import GameTile.FireFlower;
import GameTile.PowerUpBlock;
import Mario.Game;
import Mario.Handler;
import Mario.Id;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
///Destroyed entity

public class SenderServer extends Thread {
    private Handler handler;
    private String host;
    private int port;
    private Socket socket;
    private OutputStream output;
    private InputStream input;
    private PrintWriter pr;
    private BufferedReader br;
    private static Entity e;

    public SenderServer(Handler handler, String host, int port) {
        this.handler = handler;
        this.host = host;



    }

    @Override
    public void run() {

        try {
            socket = new Socket(host, port);
            output = socket.getOutputStream();
            input = socket.getInputStream();
            pr = new PrintWriter(new OutputStreamWriter(output));
            br = new BufferedReader(new InputStreamReader(input));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendEntities() {
        sendPlayer();
    }


    private void sendPlayer() {
        //-1 DEAD
        if (Game.lives > 0) {
            pr.println(Player.status);
            pr.flush();

            pr.println(Player.getFrame());
            pr.flush();

            pr.println(Handler.player.getX());
            pr.flush();

            pr.println(Handler.player.getY());
            pr.flush();
        } else {
            pr.println(-1);
            pr.flush();
        }

    }

    private void sendLiveKoopa(Koopa koopa) {
        pr.println(koopa.getTag());
        pr.flush();

        //0 WALKING ,1 SHELL , 2 SPINING
        //This is normal state
        //no state requires a frame!
        if (koopa.getKoopaState() == KoopaState.WALKING)
            pr.println(0);

        if (koopa.getKoopaState() == KoopaState.SHELL)
            pr.println(1);


        if (koopa.getKoopaState() == KoopaState.SPINNING)
            pr.println(2);

        pr.flush();

//        pr.println(koopa.getFrame());
//        pr.flush();
    }

    private void sendDeadKoopa(Koopa koopa)
    {
        pr.println(koopa.getTag());
        pr.flush();
    }


    private void sendDeadGoomba(Goomba goomba)
    {
        pr.println(goomba.getTag());
        pr.flush();
    }


    private void sendDeadHedgehog(Hedgehog hedgehog)
    {
        pr.println(hedgehog.getTag());
        pr.flush();
    }

    private void sendDeadPlant(Plant plant)
    {
        pr.println(plant.getTag());
        pr.flush();
    }


    private void sendBrokeBrick(Brick brick)
    {
        pr.println(brick.getTag());
        pr.flush();
    }


    private void sendUsedFlower(FireFlower fireFlower)
    {
        pr.println(fireFlower.getTag());
        pr.flush();
    }

    private void sendUsedCoin(Coin coin)
    {
        pr.println(coin.getTag());
        pr.flush();
    }

    private void sendChangedPowerUp(PowerUpBlock powerUpBlock)
    {
        pr.println(powerUpBlock.getTag());
        pr.flush();

        pr.println(powerUpBlock.getHitsTaken());
        pr.flush();
    }


    private void sendFireBall(FireBall fireBall)
    {
        pr.println(fireBall.getX());
        pr.flush();

        pr.println(fireBall.getY());
        pr.flush();
    }

    private void sendNewRedMushroom(RedMushroom redMushroom)
    {
        pr.println(redMushroom.getX());
        pr.flush();

        pr.println(redMushroom.getY());
        pr.println();
    }

    private void sendDeadMushroom(RedMushroom redMushroom)
    {
        pr.println(redMushroom.getTag());
        pr.flush();
    }


//    @Override
//    public void run() {
//        identify();
//        broadcastDeletedFiles();
//
//        try {
//            sendAllFiles();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
