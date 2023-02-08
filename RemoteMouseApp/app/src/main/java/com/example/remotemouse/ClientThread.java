package com.example.remotemouse;

import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class ClientThread extends Thread implements Serializable {
    private String serverIP;
    private int serverPort;
    private boolean connected;
    String msg = "";
    String oldMsg = "";
    ClientThread(String serverIP, int serverPort, boolean connected)
    {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.connected = connected;
    }

    public void stopThread(){
        connected = false;
    }

    public void update(String msg)
    {
        this.msg = msg;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket(serverIP, serverPort);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            DataInputStream input = new DataInputStream(socket.getInputStream());
            connected = true;
            while (connected)
            {
                try {
                    if(!msg.equals(oldMsg)) {
                        out.write((msg).getBytes());
                        out.flush();
                        oldMsg = msg;
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            out.write("<TheEnd>".getBytes());
            out.flush();
            socket.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
