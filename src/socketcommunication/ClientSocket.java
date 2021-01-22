package socketcommunication;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author abotaff
 */
public class ClientSocket extends Application {

    // Properties
    String serverIP, clientPort;
    
    TextField ipTF = new TextField();

    @Override
    public void start(Stage primaryStage) {
   
        ipTF.setPromptText("Enter Server IP Address");
        Button btn = new Button();
        btn.setText("Connect To Server");
        btn.setOnAction(e -> createSocket());

        VBox root = new VBox();
        root.getChildren().addAll(ipTF, btn);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Socket Communication");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Create a Socket
    public void createSocket() {
        
        // get server IP from user
        serverIP = ipTF.getText();

        try {
            Socket clientSocket = new Socket(serverIP, 6666);

            DataOutputStream dout = new DataOutputStream(clientSocket.getOutputStream());

            dout.writeUTF("Hello Server");
            dout.flush();
            dout.close();
            clientSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
