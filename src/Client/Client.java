/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;


/**
 *
 * @author Hassane and Nicolas
 * This class represents a client who connects to a server 
 */
public class Client {

    private InetAddress ip;
    private int port;
    private PrintWriter out;
    private BufferedReader in;
    private Socket socket;

    public Client(InetAddress a, int port) throws IOException {
        this.ip = a;
        this.port = port;

    }
// Establish a connection with the server
    public void connect() throws IOException {
        socket = new Socket(ip, port);
       
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream());

    }
// Function that write  send a message to the server 
    public void write(String message) throws IOException {

       
        out.println(message);
        out.flush();

    }

    public BufferedReader getInput() throws IOException {

        return in;

    }

    public void test() throws IOException {

        while (true) {
            System.out.println(in.readLine());

        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void stop(Socket socket) throws IOException {

        socket.close();
    }
}
