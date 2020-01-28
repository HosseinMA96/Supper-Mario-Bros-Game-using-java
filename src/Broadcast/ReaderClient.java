package Broadcast;

import Mario.Handler;

import java.io.*;
import java.net.Socket;

public class ReaderClient extends Thread {
    private int port;
    private String host,command=".";
    private Handler handler;
    private InputStream input;
    private OutputStream output;
    private PrintWriter pr;
    private BufferedReader br;
    private Socket socket;


    public ReaderClient(int port, String host, Handler handler) {
        this.port = port;
        this.host = host;
        this.handler = handler;
    }

    @Override
    public void run()
    {
        try {
            socket = new Socket(host, port);
            input=socket.getInputStream();
            output=socket.getOutputStream();

            pr = new PrintWriter(new OutputStreamWriter(output));
            br = new BufferedReader(new InputStreamReader(input));

            identify();

            while (!command.equals("FINISHED")) {
                command=br.readLine();

                if(command.equals("OK"))
                    continue;

                asdasd

            }
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    private void identify()
    {

    }
}

