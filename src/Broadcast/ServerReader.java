package Broadcast;
/**
 * A Class to read and save files from socket
 */

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;

public class ServerReader extends Thread {


    private Socket socket;
    private InputStream input;
    private BufferedReader br;
    private String command = ".",tag;
    private int num;


    public ServerReader(Socket socket) {
        try {
            this.socket = socket;
            input = socket.getInputStream();
            br = new BufferedReader(new InputStreamReader(input));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Run method
     */
    @Override
    public void run() {
        try {

            identify();
            MultiServer.data[num] = new ArrayList<>();

            while (!command.equals("DONE")) {

                command = br.readLine();
                MultiServer.data[num].add(command);

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void identify() throws Exception
    {
        tag = br.readLine();

        if(tag.equals("0"))
            num=0;

        else
            num=1;
    }

}


