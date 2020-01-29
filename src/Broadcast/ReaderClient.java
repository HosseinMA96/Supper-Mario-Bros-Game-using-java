package Broadcast;

import GameEntity.Enemy.ChangedKoopa;
import GameEntity.Enemy.Koopa;
import GameEntity.Enemy.KoopaState;
import GameEntity.RedMushroom;
import GameTile.PowerUpBlock;
import Mario.DeadObject;
import Mario.Game;
import Mario.Handler;
import Mario.Id;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ReaderClient extends Thread {
    private int port;
    private String host, command = ".";
    private InputStream input;
    private OutputStream output;
    private PrintWriter pr;
    private BufferedReader br;
    private Socket socket;
    private Handler handler;
    public static int otherPlayerX, otherPlayerY, otherPlayerStatus, otherPlayerFrame;
    public static ArrayList<ChangedKoopa> changedKoopas = new ArrayList<>();
    public static ArrayList<DeadObject> deadObjects = new ArrayList<>();
    public static ArrayList<Integer> fireBallX = new ArrayList<>(), fireBallY = new ArrayList<>(), mushroomX = new ArrayList<>(), mushroomY = new ArrayList<>();


    public ReaderClient(int port, String host,Handler h) {
        this.port = port;
        this.host = host;
        handler=h;

    }

    private void receivePlayer() throws Exception {
        command = br.readLine();

        if (command.equals("-1")) {
            otherPlayerStatus = -1;
            return;
        } else {
            //  command = br.readLine();
            otherPlayerStatus = Integer.parseInt(command);

            command = br.readLine();
            otherPlayerFrame = Integer.parseInt(command);

            command = br.readLine();
            otherPlayerX = Integer.parseInt(command);

            command = br.readLine();
            otherPlayerY = Integer.parseInt(command);
        }

    }

    private void receiveLiveKoopas() throws Exception {
        while (true) {
            command = br.readLine();

            if (command.equals("OK"))
                return;

            int tg = Integer.parseInt(command);
            int vel = Integer.parseInt(br.readLine());

            changedKoopas.add(new ChangedKoopa(tg, vel));
        }
    }

    private void receiveDeadThings() throws Exception {
        while (true) {


            command = br.readLine();

            if (command.equals("OK"))
                return;

            int tg;
            int x, y;

            switch (command) {

                case "goomba":
                    tg = Integer.parseInt(br.readLine());
                    deadObjects.add(new DeadObject(tg, Id.goomba));
                    break;

                case "hedgehog":
                    tg = Integer.parseInt(br.readLine());
                    deadObjects.add(new DeadObject(tg, Id.hedgehog));
                    break;

                case "koopa":
                    tg = Integer.parseInt(br.readLine());
                    deadObjects.add(new DeadObject(tg, Id.koopa));
                    break;

                case "plant":
                    tg = Integer.parseInt(br.readLine());
                    deadObjects.add(new DeadObject(tg, Id.plant));
                    break;


                case "brick":
                    x = Integer.parseInt(br.readLine());
                    y = Integer.parseInt(br.readLine());
                    deadObjects.add(new DeadObject(x, y, Id.brick));
                    break;

                case "coin":
                  //  JOptionPane.showMessageDialog(null, "A DEAD COIN !");
                    x = Integer.parseInt(br.readLine());
                    y = Integer.parseInt(br.readLine());
                    System.out.println("DEAD COINT X: "+x);
                    System.out.println("DEAD COINT Y: "+y);
                    deadObjects.add(new DeadObject(x, y, Id.coin));
                    break;


                case "fireFlower":
                    x = Integer.parseInt(br.readLine());
                    y = Integer.parseInt(br.readLine());
                    deadObjects.add(new DeadObject(x, y, Id.fireFlower));
                    break;

                case "powerUp":
                    x = Integer.parseInt(br.readLine());
                    y = Integer.parseInt(br.readLine());
                    int hits = Integer.parseInt(br.readLine());
                    deadObjects.add(new DeadObject(x, y, Id.powerUp, hits));
                    break;


            }
        }
    }

    private void receiveFireBalls() throws Exception {
        while (true) {
            command = br.readLine();
            if (command.equals("OK"))
                break;

            int x = Integer.parseInt(br.readLine());
            int y = Integer.parseInt(br.readLine());

            fireBallX.add(x);
            fireBallY.add(y);

        }
    }

    private void receiveMushroom() throws Exception {
        while (true) {
            command = br.readLine();
            if (command.equals("OK"))
                break;

            int x = Integer.parseInt(br.readLine());
            int y = Integer.parseInt(br.readLine());

            mushroomX.add(x);
            mushroomY.add(y);

        }
    }

    private void initialize() {

        otherPlayerStatus = -1;
        ArrayList<ChangedKoopa> changedKoopas = new ArrayList<>();
        ArrayList<DeadObject> deadObjects = new ArrayList<>();
        ArrayList<Integer> fireBallX = new ArrayList<>(), fireBallY = new ArrayList<>(), mushroomX = new ArrayList<>(), mushroomY = new ArrayList<>();
    }

    @Override
    public void run() {
        try {


            initialize();
            socket = new Socket(host, port);
            input = socket.getInputStream();
            output = socket.getOutputStream();

            pr = new PrintWriter(new OutputStreamWriter(output));
            br = new BufferedReader(new InputStreamReader(input));

            identify();

            while (!command.equals("DONE")) {

                //  System.out.println("stats : "+otherPlayerStatus);
                //  System.out.println("x : "+otherPlayerX);
                //  System.out.println("y : "+otherPlayerY);

                command = br.readLine();
                //  System.out.println("in reader client command = "+command);

                switch (command) {
                    case "OK":
                        continue;

                    case "PLAYER":
                        receivePlayer();
                        break;

                    case "LIVEKOOPA":
                        receiveLiveKoopas();
                        break;

                    case "DEADTHINGS":
                        receiveDeadThings();
                        break;

                    case "FIRE":
                        receiveFireBalls();
                        break;


                    case "MUSHROOM":
                        receiveMushroom();
                        break;

                    default:
                        //       System.out.println(command);
                        break;


                }

                applyRemoteUpdate();


            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void identify() throws Exception {
        pr.println(Game.playerIndex);
        pr.flush();
    }

    private void updateLiveKoopas() {
        for (int i = 0; i < ReaderClient.changedKoopas.size(); i++) {
            for (int j = 0; j < handler.getEntity().size(); j++)
                if (handler.getEntity().get(j).getId() == Id.koopa && handler.getEntity().get(j).getTag() == ReaderClient.changedKoopas.get(i).getTag()) {
                    if (handler.getEntity().get(j).getKoopaState() == KoopaState.WALKING) {
                        handler.getEntity().get(j).setKoopaState(KoopaState.SHELL);
                        handler.getEntity().get(j).setVelX(0);
                        break;
                    }

                    if (handler.getEntity().get(j).getKoopaState() == KoopaState.SHELL) {
                        handler.getEntity().get(j).setKoopaState(KoopaState.SPINNING);
                        handler.getEntity().get(j).setVelX(ReaderClient.changedKoopas.get(i).getVelX());
                        break;
                    }
                }

        }
    }

    private void updateDeadObjects() {
        for (int i = 0; i < ReaderClient.deadObjects.size(); i++) {
            DeadObject deadObject = ReaderClient.deadObjects.get(i);
            System.out.println("DEAD OBJECT ID : "+deadObject.getId());

            switch (deadObject.getId()) {
                case goomba:
                    for (int j = 0; j < handler.getEntity().size(); j++)
                        if (handler.getEntity().get(j).getId() == Id.goomba && handler.getEntity().get(j).getTag() == deadObject.getTag()) {
                            handler.getEntity().remove(j);
                            break;
                        }
                    break;

                case hedgehog:
                    for (int j = 0; j < handler.getEntity().size(); j++)
                        if (handler.getEntity().get(j).getId() == Id.hedgehog && handler.getEntity().get(j).getTag() == deadObject.getTag()) {
                            handler.getEntity().remove(j);
                            break;
                        }
                    break;


                case plant:
                    for (int j = 0; j < handler.getEntity().size(); j++)
                        if (handler.getEntity().get(j).getId() == Id.plant && handler.getEntity().get(j).getTag() == deadObject.getTag()) {
                            handler.getEntity().remove(j);
                            break;
                        }
                    break;

                case koopa:
                    for (int j = 0; j < handler.getEntity().size(); j++)
                        if (handler.getEntity().get(j).getId() == Id.koopa && handler.getEntity().get(j).getTag() == deadObject.getTag()) {
                            handler.getEntity().remove(j);
                            break;
                        }
                    break;


                case brick:
                    for (int j = 0; j < handler.getTile().size(); j++)
                        if (handler.getTile().get(j).getId() == Id.brick && handler.getTile().get(j).getX()==deadObject.getX() &&  handler.getTile().get(j).getY()==deadObject.getY()) {
                            handler.getTile().remove(j);
                            break;
                        }
                    break;


                case coin:
                    System.out.println("REMOVING COIN");
                    for (int j = 0; j < handler.getTile().size(); j++)
                        if (handler.getTile().get(j).getId() == Id.coin && handler.getTile().get(j).getX()==deadObject.getX() &&  handler.getTile().get(j).getY()==deadObject.getY()) {
                            handler.getTile().remove(j);
                            break;
                        }
                    break;


                case fireFlower:
                    for (int j = 0; j < handler.getTile().size(); j++)
                        if (handler.getTile().get(j).getId() == Id.fireFlower && handler.getTile().get(j).getX()==deadObject.getX() &&  handler.getTile().get(j).getY()==deadObject.getY()) {
                            handler.getTile().remove(j);
                            break;
                        }
                    break;

                case powerUp:
                    for (int j = 0; j < handler.getTile().size(); j++)
                        if (handler.getTile().get(j).getId() == Id.powerUp && handler.getTile().get(j).getX()==deadObject.getX() &&  handler.getTile().get(j).getY()==deadObject.getY()) {
                            ((PowerUpBlock)handler.getTile().get(j)).setHitsTaken(deadObject.getHits());
                            break;
                        }
                    break;



            }
        }
    }

    private void updateMushrooms()
    {
        for (int i=0;i<ReaderClient.mushroomX.size();i++)
            handler.getEntity().add(new RedMushroom(ReaderClient.mushroomX.get(i),ReaderClient.mushroomY.get(i),64,64,Id.redMushroom,handler,Handler.mushroomTags++));
    }


    private void applyRemoteUpdate() {
        updateLiveKoopas();
        updateDeadObjects();
        updateMushrooms();


    }
}

