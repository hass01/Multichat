/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket_network;

import java.net.InetAddress;

public abstract class AbstractMultichatServer {

    private InetAddress ip;
    private int port;

    public AbstractMultichatServer(InetAddress ip, int port) {
        this.ip = ip;
        this.port = port;

    }

    public InetAddress getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }
}
