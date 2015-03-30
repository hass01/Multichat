/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket_network;


import Client.Client;
import Logger.MonLogger;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;


/**
 *
 * @author Hassane and Nicolas 
 * Class that manages every message received by the client connecteed with on the  chat 
 */
public class ClientHandleRceived implements Runnable {

    private Client client;
    private ChatController chatController;

    public ClientHandleRceived(Client client, ChatController controller) {
        this.client = client;
        this.chatController = controller;
    }

    @Override
    public void run() {
        BufferedReader in = null;
        try {
            char[] buffer = new char[5000];
            String msg = "";
            in = new BufferedReader(new InputStreamReader(client.getSocket().getInputStream()));
            while (true) {

                int charsRead = 0;

                if ((charsRead = in.read(buffer)) != -1) {
                    msg = new String(buffer).substring(0, charsRead);
                    System.out.println(msg);
                    chatController.addToView(msg);

                }

            }
        } catch (IOException ex) {
            MonLogger.logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}
