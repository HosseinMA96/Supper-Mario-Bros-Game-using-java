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
    private String command=".";



    /**
     * Constructor for this class
     *
     * @param socket
     * @param first
     * @param location
     */
    public ServerReader(Socket socket, boolean first, File location) {
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
        while(!command.equals("FINISHED"))
        {

        }
    }

}


