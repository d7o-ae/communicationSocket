package socketcommunication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 *
 * @author abotaff
 */
public class ClientSocket extends Application {

    // *** Properties ****
    String remoteIP, message; // to be deleted

    TextField ipTF = new TextField();
    TextField messageTF = new TextField();
    Button sendBtn, listenBtn;

    @Override
    public void start(Stage primaryStage) {

        // textFields
        ipTF.setPromptText("Enter IP Address");
        ipTF.setTooltip(new Tooltip("IP Address"));
        ipTF.setMaxSize(140, 40);
        messageTF.setPromptText("Enter your message");
        messageTF.setTooltip(new Tooltip("Your message here"));
        messageTF.setMaxSize(140, 40);

        // buttons
        sendBtn = new Button("Send Message");
        sendBtn.setOnAction(e -> sendMessage());
        listenBtn = new Button("Listen");
        listenBtn.setOnAction(e -> listen());

        // style scene and add components
        VBox root = new VBox(20);
        HBox buttonsHBox = new HBox(20);
        buttonsHBox.setAlignment(Pos.CENTER);
        root.setAlignment(Pos.CENTER);

        buttonsHBox.getChildren().addAll(listenBtn, sendBtn);
        root.getChildren().addAll(ipTF, messageTF, buttonsHBox);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Socket Communication");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    
    
    
    
    // *** Methods ***
    // send Message
    public void sendMessage() {

        // get server IP from user
        remoteIP = ipTF.getText();
        message = messageTF.getText();

        try {
            Socket clientSocket = new Socket(remoteIP, 6666);

            DataOutputStream dout = new DataOutputStream(clientSocket.getOutputStream());

            dout.writeUTF(message);
            dout.flush();
            dout.close();
            clientSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // listen to IP's Message
    public void listen() {

        try {
            ServerSocket ss = new ServerSocket(6666);
            Socket s = ss.accept();//establishes connection   
            DataInputStream dis = new DataInputStream(s.getInputStream());
            String str = (String) dis.readUTF();
            System.out.println("message= " + str);
            ss.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    // clear fields
    public void clear() {
        messageTF.clear();
        ipTF.clear();
    }

}
