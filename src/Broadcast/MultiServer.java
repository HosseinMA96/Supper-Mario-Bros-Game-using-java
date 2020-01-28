/**
 * A Class which acts as the servers, reads and manipulates files from sockets and sends them to the computers
 */

package Broadcast;
import javafx.scene.control.ProgressBar;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class MultiServer {

    /**
     * Main method, always runs the server
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        ServerSocket serverSocket = new ServerSocket(30000);
        initializeServer();


        while (true) {


            Socket socketReader1 = serverSocket.accept();
            ServerReader serverReader1 = new ServerReader(socketReader1, true, base);

            System.out.println("first reader accepted");

            Socket socketReader2 = serverSocket.accept();
            System.out.println("second reader accepted");
            ServerReader serverReader2 = new ServerReader(socketReader2, false, base);


            serverReader1.start();
            serverReader2.start();

            System.out.println("both reader started");


            serverReader1.join();
            serverReader2.join();


            mergeFile(serverReader1, serverReader2);


            Socket socketWriter1 = serverSocket.accept();
            ServerWriter serverWriter1 = new ServerWriter(socketWriter1, base, toBeDeletedInFirst, firstDeleted);


            Socket socketWriter2 = serverSocket.accept();
            ServerWriter serverWriter2 = new ServerWriter(socketWriter2, base, toBeDeletedInFirst, firstDeleted);


            System.out.println("Both readers joined");
            System.out.println("Both writers started");


            serverWriter2.start();
            serverWriter1.start();


            serverWriter1.join();
            serverWriter2.join();

            initializeServer();


            System.out.println("Both writers joined");


        }


    }

}
