/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket_network;


import Client.Client;
import Client.MultiCastClient;
import Logger.MonLogger;
import Server_Channel.ServerV2;
import Server_Socket.Server;
import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;
import java.io.IOException;
import java.net.InetAddress;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Hassane and Nicolas
 */
public class Socket_network extends Application {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    private static InetAddress ip;
    private static int port = 6060;
    private static boolean Lanch_serv = false;
    private static boolean Nio = false;
    private static boolean multiCast = false;
    private static MonLogger mylog = null;

    public static void main(String[] args) throws Exception {

        ip = InetAddress.getByName("127.0.0.1");
       
        LongOpt L[] = new LongOpt[7];
        L[0] = new LongOpt("Address", LongOpt.REQUIRED_ARGUMENT, null, 'a');
        L[1] = new LongOpt("debug", LongOpt.NO_ARGUMENT, null, 'd');
        L[2] = new LongOpt("multicast", LongOpt.NO_ARGUMENT, null, 'm');
        L[3] = new LongOpt("help", LongOpt.NO_ARGUMENT, null, 'h');
        L[4] = new LongOpt("nio", LongOpt.NO_ARGUMENT, null, 'n');
        L[5] = new LongOpt("port", LongOpt.REQUIRED_ARGUMENT, null, 'p');
        L[6] = new LongOpt("server", LongOpt.NO_ARGUMENT, null, 's');

        Getopt g = new Getopt("Multichat", args, "a:dmhnp:s", L);
        int value;
        while ((value = g.getopt()) != -1) {
            switch (value) {
                case 'a':
                    ip = InetAddress.getByName(g.getOptarg());
                    break;
                case 'h':
                    System.out.println(" Usage : java - jar target / multichat -0.0.1 - SNAPSHOT . jar [ OPTION ]...");
                    System.out.println("-a , -- address = ADDR sp�cifier l'adresse IP");
                    System.out.println("-d , -- debug affiche les messages d'erreur");
                    System.out.println("-h , -- help afficher l'aide et quitter");
                    System.out.println("-m , -- multicast d � marrer le client en mode multicast");
                    System.out.println("-n , -- nio configurer le serveur en mode NIO");
                    System.out.println("-p , -- port = PORT sp�cifier le port");
                    System.out.println("-s , -- server start the server\n");

                    break;
                case 'n':
                    Lanch_serv = true;
                    Nio = true;
                    break;
                case 'm':
                    multiCast = true;
                    break;
                case 'p':
                    port = Integer.parseInt(g.getOptarg());
                    break;
                case 's':
                    System.out.println("Lancement du server");
                    Lanch_serv = true;
                    break;
                case 'd':
                    System.out.println("Mode Debug");
                    mylog = new MonLogger(1);
                    break;
                default:
                    System.out.println("Veuillez mettre les bon arguments ");
            }
        }
        if (mylog == null) {
            mylog = new MonLogger(1);
        }

        launchProgram();

    }
// Function that luanch the right part of the program
    public static void launchProgram() throws IOException {
        if (Lanch_serv) {
            if (Nio) {
                ServerV2 serv = new ServerV2(ip, port);
                serv.start();

            } else {
                Server serv = new Server(ip, port);
                serv.start();

            }

        } else {
            launch();
        }
    }
 //Function for javafx windows
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
        FXMLLoader Loader = new FXMLLoader();
        if (multiCast) {
            Loader.setLocation(getClass().getResource("/View/MultiCastChat.fxml"));
            Parent root = (Parent) Loader.load();
            MultiCastChatController controller = (MultiCastChatController) Loader.getController();
            controller.set_client(new MultiCastClient(InetAddress.getByName("224.0.0.3"), 6789));
            Scene scene = new Scene(root);
            primaryStage.setTitle("Multichat");

            primaryStage.setScene(scene);
            //stage.setScene(scene);
            primaryStage.show();
        } else {
            Loader.setLocation(getClass().getResource("/View/ChatView.fxml"));

            Parent root = (Parent) Loader.load();
            ChatController controller = (ChatController) Loader.getController();
            controller.set_Client(new Client(ip, port));
            Scene scene = new Scene(root);
            primaryStage.setTitle("Multichat");

            primaryStage.setScene(scene);
            //stage.setScene(scene);
            primaryStage.show();

        }

    }
}
