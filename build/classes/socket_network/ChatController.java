/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket_network;

import Client.Client;
import java.io.IOException;
import java.net.URL;
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
import javafx.stage.Stage;

/**
 * FXML Controller class
 * The class is the controller of the View ChatView
 * @author Hassane and Nicolas
 */
public class ChatController implements Initializable {

    @FXML
    private Button Send;
    @FXML
    private ListView<String> chat;
    private ObservableList<String> obs = FXCollections.observableArrayList();
    @FXML
    private TextArea message;
    private Client client;
    @FXML
    Button Quit;
   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
        
    }

    @FXML
    public void handleActionSend(Event e) throws IOException {
        String mess = message.getText() + "\n";
        if (mess.length() != 0) {
            client.write(mess);

        }

    }
// Function that manage the events on the Button quit 
    @FXML
    public void handleActionQuit(Event e) throws IOException {
        client.write("/exit");
        Stage stage = (Stage) Quit.getScene().getWindow();
        stage.close();
        Platform.exit();
        System.exit(0);
    }
// We set the client who is using the chat
    public void set_Client(Client client) throws IOException {

        this.client = client;
        client.connect();
      

        new Thread(new ClientHandleRceived(client, this)).start();

    }
// Function that update the view
    public void addToView(String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                obs.add(message);
                chat.setItems(obs);

                
            }
        });

    }

    public void setClient(Client client) {
        this.client = client;
    }
}
