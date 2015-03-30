/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket_network;

import Client.MultiCastClient;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author Hassane and Nicolas 
 * Controller of the View MultiCastChat
 */
public class MultiCastChatController implements Initializable {

    private MultiCastClient client;

    @FXML
    private Button send;
    @FXML
    private TextArea msg;
    @FXML
    private ListView<String> chat;
    private ObservableList<String> obsChat = FXCollections.observableArrayList();
    @FXML
    private ListView<String> Users;
    private ObservableList<String> obsUsers = FXCollections.observableArrayList();
    @FXML
    private Button Refresh;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    public void HandleAction(Event e) throws IOException, InterruptedException {
        if (e.getSource() == send) {

            client.SendMsg(msg.getText());
        }

        if (e.getSource() == Refresh) {

            client.Check_Others();
            putUsers(client.getHosts());
        }

    }

    public void set_client(MultiCastClient client) throws IOException, InterruptedException {
        this.client = client;

        client.connect();
        Thread Listening = new Thread(new MultiCastthread(client, this));
        Listening.start();

    }
// Function that was supposed to manage the view of the  list of users using a multicasting chat
    public void putUsers(HashSet<String> Hosts) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                obsUsers.clear();
                Iterator<String> i = Hosts.iterator();
                while (i.hasNext()) {
                    obsUsers.add(i.next());
                    Users.setItems(obsUsers);

                }

            }
        });
    }
// function that update the view of the chat when a message is sent 
    public void chat(String msg) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                obsChat.add(msg);
                chat.setItems(obsChat);

            }
        });

    }

}
