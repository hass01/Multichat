/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server_Socket;

import Logger.MonLogger;
import Server_Socket.Server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hassane and Nicolas 
 * 
 * This class manages evry client which is connected to the server with a socket
 */
public class Client_handling implements Runnable {

    private Socket s;
    private int numero_client;
    private Server myserver;
    private String nickname;

    public Client_handling(Socket socket, int num, Server server) {
        s = socket;
        numero_client = num;
        myserver = server;
        nickname = (String) server.getListe2().get(s);
    }

    @Override
    public void run() {
        String a = "";
        char[] buffer = new char[5000];
        int charsRead = 0;
        boolean end=true;

        do {
            try {
               
                BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                if((charsRead = in.read(buffer)) != -1 ) {

                    a = new String(buffer).substring(0, charsRead).trim();
                    System.out.println("Message reçus par le server : "+a);
                    if(a.equals("/exit")){end=false;}
                      
                    if (a.contains("/nick ")) {
                       
                        a = a.replace("/nick", "");
                        // change nickname
                        boolean result = myserver.set_nick(s, a);

                    } else {
                            if(!a.equals("/exit"))
       {                // broadcast to other clients 
                        myserver.send_msg(s,nickname+" : "+ a);}
                    }
                }
            } catch (IOException ex) {
               
                MonLogger.logger.log(Level.SEVERE, ex.getMessage(), ex);
            }
         

        } while (end);
       try {  myserver.getListe().remove(s);
            myserver.getListe2().remove(s);
               
                    s.close();
                } catch (IOException ex1) {
                    Logger.getLogger(Client_handling.class.getName()).log(Level.SEVERE, null, ex1);
                }
        
            
    }
}
