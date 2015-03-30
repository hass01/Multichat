/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server_Channel;

import Logger.MonLogger;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import socket_network.AbstractMultichatServer;
import socket_network.MultichatServer;

/**
 *
 * @author Hassane and nicolas 
 * Serversocket channel implementation 
 */
public class ServerV2 extends AbstractMultichatServer implements MultichatServer {

    Selector selector;

    public ServerV2(InetAddress ip, int port) {
        super(ip, port);

    }

    @Override
    public void start() throws IOException {
        selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(getIp(), getPort()));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
           // System.out.println("Server listening  ");
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> i = keys.iterator();

            while (i.hasNext()) {
                SelectionKey key = (SelectionKey) i.next();
                i.remove();
                // Si un client demande une connexion 
                if (key.isAcceptable()) {
                    SocketChannel s_client = serverSocketChannel.accept();
                    System.out.println("Un client vient de se connecter");
                    ByteBuffer buffer = ByteBuffer.allocate(81952);
                    Charset charset = Charset.defaultCharset();
                    buffer = charset.encode(" Server version 3.6 \n");
                    s_client.write(buffer);
                    // On configure le socket en non bloquant 
                    s_client.configureBlocking(false);
                    s_client.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                }
                // To read
                if (key.isReadable()) {
                    SocketChannel s_client = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(81952);
                    try {

                        s_client.read(buffer);
                        Charset charset = Charset.defaultCharset();
                        buffer.flip();
                        CharBuffer cbuf = charset.decode(buffer);
                        System.out.println(cbuf);
                        
                           if(cbuf.toString().trim().equals("/exit"))
                        {
                            System.out.print("okkk");
                      s_client.close();
                        }
                        else
                        
                        
                      {buffer.compact();
                        Boradcast(cbuf.toString());}

                    } catch (Exception a) {
                        MonLogger.logger.log(Level.SEVERE, a.getMessage(), a);
                        System.out.println("Erreur lecture !");
                    }

                }

            }
      // SocketChannel client= serverSocketChannel.accept();

        }
    }
// Function that broadcast evry message sent by a client to all the hosts
    public void Boradcast(String message) throws IOException {
        int counter=0;
        ByteBuffer Buffer = ByteBuffer.wrap(message.getBytes());
        System.out.println(selector.keys().size());
        for (SelectionKey key :selector.keys() ) {
            
            if ( key.channel() instanceof SocketChannel) {
            System.out.println("counter:"+counter);
                SocketChannel s = (SocketChannel) key.channel();
               System.out.println("Le buffer est :" +Buffer);
                s.write(Buffer);
               Buffer.rewind();
            
                
            }

        }

               
    }
}
