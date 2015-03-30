/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket_network;

import Client.MultiCastClient;
import Logger.MonLogger;
import java.io.IOException;
import java.net.DatagramPacket;
import java.util.logging.Level;


/**
 *
 * @author Hassane and nicolas
 * Class which handle the receiving of the messages from other clients and update the view
 */
public class MultiCastthread implements Runnable {

    private final MultiCastClient client;
    private final MultiCastChatController c;

    public MultiCastthread(MultiCastClient client, MultiCastChatController m) {
        this.client = client;
        this.c = m;
    }

    @Override
    public void run() {
        String chaine;
        int nb;

        while (true) {
            byte[] buff = new byte[1000];
            try {
                DatagramPacket pkt = new DatagramPacket(buff, buff.length);
                client.getSocket().receive(pkt);

                chaine = new String(buff);

                if (chaine.contains("/Check")) {
                   /* System.out.println("Check");
                    System.out.println("Client nickname :" + client.getNickname());
                    client.SendMsg("/" + client.getNickname());*/

                } else if (!chaine.contains("/User")) {
                      // update the view  
                    c.chat(chaine);

                }

            } catch (IOException ex) {
                MonLogger.logger.log(Level.SEVERE, ex.getMessage(), ex);
            }

        }

    }
}
