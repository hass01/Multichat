/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server_Socket;


import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import socket_network.AbstractMultichatServer;
import socket_network.MultichatServer;

/**
 *
 * @author Hassane and Nicolas 
 * Class that implements a server working with a serversocket and sockets when the connection is estblished 
 */
public class Server extends AbstractMultichatServer implements MultichatServer {

 
    public ServerSocket server;
    private Socket s;
    private int counter = 0;
    private BufferedWriter out;
    private Map liste;
    private Map liste2;

    public Map getListe2() {
        return liste2;
    }

    public Map getListe() {
        return liste;
    }

   

    public ServerSocket getServer() {
        return server;
    }

    public Socket getS() {
        return s;
    }

    public Server(InetAddress ip, int port) throws IOException {
        super(ip,port);    
        server = new ServerSocket();
        liste = new HashMap<Socket, BufferedWriter>();
        liste2 = new HashMap<Socket, String>();
    }

    @Override
    public void start() throws IOException {

        String a = "";

        server.bind(new InetSocketAddress(getIp(), getPort()), 5);
        System.out.println(server.getInetAddress());

        while (true) {
            s = server.accept();
            
            counter++;
            // liste to associate evry buffered reader to a socket
            liste.put(s, new BufferedWriter(new OutputStreamWriter(s.getOutputStream())));
            //list for nicknames
            liste2.put(s, "User" + Integer.toString(counter));
            // Broadcast of the version of the server to evry client who establish a connection
            out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
            out.write("Version serveur 3.5 !\n");
            out.flush();
            
            System.out.println("Client numero :" + counter);
            // We start a thread that manage the client
            new Thread(new Client_handling(s, counter, this)).start();
            
        }

   //server.close();
        // s.close();
    }
// Functiont o send a message to evry client
    public synchronized void send_msg(Socket s, String a) throws IOException {
       
        Iterator<Socket> i = liste.keySet().iterator();
        while (i.hasNext()) {
     
            Socket s1 = i.next();
             
            if (s1.equals(s)) {}
                
                BufferedWriter buff = (BufferedWriter) liste.get(s1);
                
             
            
             
               
                buff.write(a);

                buff.flush();
               
 

        }
}
    // Function that change the nick name of a user 
    public synchronized boolean set_nick(Socket s, String name) {
        Iterator<Socket> i = liste2.keySet().iterator();
        while (i.hasNext()) {
          
            Socket s1 = i.next();
            if (s1.equals(s)) {
              
                liste2.replace(s1,liste2.get(s1) ,name);
             //   liste2.put(s1, name);

                return true;
            }

        }
        return false;
    }
}
