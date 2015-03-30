/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.HashSet;
import java.util.Iterator;


/**
 *
 * @author Hassane
 */
public class MultiCastClient {

    private MulticastSocket socket;
    private InetAddress adr;
    private int port;
    private String nickname = "";

    private HashSet<String> hosts;

    public HashSet<String> getHosts() {
        return hosts;
    }

    public MultiCastClient(InetAddress group, int port) throws IOException {

        this.adr = group;
        this.port = port;

        hosts = new HashSet<String>();

    }

    public void connect() throws IOException, InterruptedException {
        socket = new MulticastSocket(port);
        socket.joinGroup(adr);
    // Lookinf for others clients
        //Check_Others();

 //  Thread Sending_data = new Thread(new Back_ground_data(this));
        // Sending_data.start();
//   Listening.sleep(2000);
        //  ReceivMsg();
        // System.out.println("Number : " +socket.getNetworkInterface().getIndex());  
    }
// Send message to others clients 
    public void SendMsg(String msg) throws IOException {

        DatagramPacket pkt = new DatagramPacket(msg.getBytes(), msg.length(), adr, port);
        socket.send(pkt);
    }

    public String ReceivMsg() throws IOException {

        byte[] buff = new byte[1000];

        DatagramPacket pkt = new DatagramPacket(buff, buff.length);
        socket.receive(pkt);
        return new String(buff);

    }

    public InetAddress getAdr() {
        return adr;
    }

    public int getPort() {
        return port;
    }

    public String getNickname() {
        return nickname;
    }
// Function that one supposed to send a message to knwo if there is other hots of the multicasting chat
    public void Check_Others() throws IOException, InterruptedException {
        Thread th1 = new Thread(new ListenOthers(this));
        th1.start();
        SendMsg("/Check");
        Thread.sleep(3000);

        if (nickname.isEmpty()) {
            if (hosts.isEmpty()) {
                nickname = "User1";
                hosts.add(nickname);
            } else {
                define_nickname();
            }
        }

    }

    public MulticastSocket getSocket() {
        return socket;
    }

    public void define_nickname() {
        String tmp;
        int tmp2 = 0;
        Iterator<String> i = hosts.iterator();
        while (i.hasNext()) {

            tmp = i.next();
            if (tmp.contains("/")) {
                tmp = tmp.replaceAll("/User", "").trim();
            } else {
                tmp = tmp.replaceAll("User", "").trim();
            }

            System.out.println("Value:" + tmp);
            if (tmp2 < (int) Integer.parseInt(tmp)) {
                tmp2 = Integer.parseInt(tmp);
            }

        }
        tmp2 = tmp2 + 1;
        nickname = "User" + tmp2;
        hosts.add(nickname);

    }

}
