/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Logger.MonLogger;
import java.io.IOException;
import java.net.DatagramPacket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hassane and Nicolas 
 * 
 * Class which was supposed to listen for others cliens in the case of a multicast to detect other clients
 * Not enought time to implement it 
 */
public class ListenOthers implements Runnable {

    private MultiCastClient client;

    public ListenOthers(MultiCastClient client) {
        this.client = client;
    }

    @Override
    public void run() {

        String chaine;
        int nb;
        byte[] buff = new byte[1000];
        while (true) {
            try {
                DatagramPacket pkt = new DatagramPacket(buff, buff.length);
                client.getSocket().receive(pkt);

                chaine = new String(buff);

                
                if (chaine.contains("/User")) {
                    System.out.println("Some one here ?");
                    chaine = chaine.trim().replace("/", "");
                    client.getHosts().add(chaine);

                } else {
                    System.out.println("No one here ");
                }

            } catch (IOException ex) {
                MonLogger.logger.log(Level.SEVERE, ex.getMessage(), ex);
            }

        }

    }
}
