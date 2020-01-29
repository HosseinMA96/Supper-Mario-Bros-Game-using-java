package Broadcast;

import GameEntity.Enemy.ChangedKoopa;
import GameEntity.Enemy.Koopa;
import GameEntity.Enemy.KoopaState;
import Mario.DeadObject;
import Mario.Game;
import Mario.Handler;
import Mario.Id;

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
    public static int otherPlayerX, otherPlayerY, otherPlayerStatus, otherPlayerFrame;
    public static ArrayList<ChangedKoopa> changedKoopas = new ArrayList<>();
    public static ArrayList<DeadObject> deadObjects = new ArrayList<>();
    public static ArrayList<Integer> fireBallX = new ArrayList<>(), fireBallY = new ArrayList<>(), mushroomX = new ArrayList<>(), mushroomY = new ArrayList<>();


    public ReaderClient(int port, String host) {
        this.port = port;
        this.host = host;

    }

    private void receivePlayer() throws Exception {
        command = br.readLine();

        if (command.equals("-1")) {
            otherPlayerStatus = -1;
            return;
        } else {
            command = br.readLine();
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

            int tg = Integer.parseInt(br.readLine());
            int x, y;

            switch (command) {

                case "goomba":
                    deadObjects.add(new DeadObject(tg, Id.goomba));
                    break;

                case "hedgehog":
                    deadObjects.add(new DeadObject(tg, Id.hedgehog));
                    break;

                case "koopa":
                    deadObjects.add(new DeadObject(tg, Id.koopa));
                    break;

                case "plant":
                    deadObjects.add(new DeadObject(tg, Id.plant));
                    break;


                case "brick":
                    x = Integer.parseInt(br.readLine());
                    y = Integer.parseInt(br.readLine());
                    deadObjects.add(new DeadObject(x, y, Id.brick));
                    break;

                case "coin":
                    x = Integer.parseInt(br.readLine());
                    y = Integer.parseInt(br.readLine());
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

        otherPlayerStatus=-1;
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

                command = br.readLine();
                System.out.println("in reader client command = "+command);

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
                        System.out.println(command);
                        break;


                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void identify() throws Exception {
        pr.println(Game.playerIndex);
        pr.flush();
    }
}

