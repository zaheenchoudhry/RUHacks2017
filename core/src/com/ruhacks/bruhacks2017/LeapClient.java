package com.ruhacks.bruhacks2017;

/**
 * Created by Ali on 2017-03-18.
 */

import java.net.*;
import java.io.*;
import java.util.Arrays;

public class LeapClient extends Thread {

    private static String serverName = "127.0.0.1";
    private static int port = 6666;
    private static Socket client;
    static float[] position = new float[2];
    private GameScreen gc;

    LeapClient(GameScreen gameScreen) throws UnknownHostException, IOException {
        client = new Socket(serverName, port);
        gc = gameScreen;
    }
    @Override
    public void run() {
        InputStream inFromServer = null;
        try {
            while (true) {
                inFromServer = client.getInputStream();
                DataInputStream in = new DataInputStream(inFromServer);
                String[] vals = (in.readUTF()).split(",");
                gc.setXY(Float.parseFloat(vals[0]), Float.parseFloat(vals[1]));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setGameScreen(GameScreen gameScreen) {
        gc = gameScreen;
    }

}