package socketcommunication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author d7o_ae
 */
public class ClientSocket extends Application {

    // *** Properties ****
    String remoteIP, message; // to be deleted

    TextField ipTF = new TextField();
    TextField messageTF = new TextField();
    TextArea chat = new TextArea();
    Label myIP = new Label();
    Button sendBtn, listenBtn;

    @Override
    public void start(Stage primaryStage) {

        // textFields
        ipTF.setPromptText("Enter IP Address");
        ipTF.setTooltip(new Tooltip("IP Address"));
        ipTF.setMaxSize(140, 40);
        messageTF.setPromptText("Enter your message");
        messageTF.setTooltip(new Tooltip("Your message here"));
        messageTF.setPrefSize(215, 40);
        chat.setEditable(false);
        chat.setPromptText("Your chat messages will appear here ..");

        // Labels
        myIP.setText((" My IP: " + getMyIP()));
        myIP.setTextFill(Color.RED);

        // buttons
        sendBtn = new Button("Send Message");
        sendBtn.setOnAction(e -> sendMessage());
        listenBtn = new Button("Listen");
        listenBtn.setOnAction(e -> listen());

        // style scene and add components
        HBox serverBottomHBox = new HBox(20);
        serverBottomHBox.getChildren().addAll(ipTF, listenBtn);

        VBox serverVBox = new VBox(20);
        serverVBox.setPadding(new Insets(10, 5, 5, 5));
        serverVBox.setAlignment(Pos.BASELINE_LEFT);
        serverVBox.setStyle("-fx-border-color: grey");
        serverVBox.getChildren().addAll(myIP, serverBottomHBox);

        HBox buttonsHBox = new HBox(5);
        buttonsHBox.setAlignment(Pos.BASELINE_LEFT);
        buttonsHBox.getChildren().addAll(messageTF, sendBtn);

        VBox chatAndButtonsVBox = new VBox(20);
        chatAndButtonsVBox.getChildren().addAll(chat, buttonsHBox);
        chatAndButtonsVBox.setStyle("-fx-border-color: grey");
        chatAndButtonsVBox.setAlignment(Pos.CENTER);
        chatAndButtonsVBox.setPadding(new Insets(10, 5, 5, 5));

        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20, 10, 20, 10));
        root.setStyle("-fx-border-color: grey");
        root.getChildren().addAll(serverVBox, chatAndButtonsVBox);

        Scene scene = new Scene(root, 350, 350);

        primaryStage.setTitle("Socket Communication");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
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
            chat.setText(chat.getText() + getMyIP() + " :\n" + messageTF.getText() + "\n");
            chat.setScrollTop(Double.MAX_VALUE);

            clear();
        } catch (IOException ex) {
            Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, null, ex);
            Alerts.showAlert(ex.getMessage());
        }
    }

    // listen to IP's Message
    public void listen() {
        // get server IP from user
        remoteIP = ipTF.getText();

        try {
            
            
            ServerSocket ss = new ServerSocket(6666);
            Socket s = ss.accept();//establishes connection  

            DataInputStream dis = new DataInputStream(s.getInputStream());

            String str = (String) dis.readUTF();
            System.out.println("message= " + str);
            chat.setText(chat.getText() + remoteIP + " :\n" + str + "\n");
            chat.setScrollTop(Double.MAX_VALUE);
            ss.close();
        } catch (Exception e) {
            System.out.println(e);
            Alerts.showAlert(e.getMessage());
        }

    }

    // clear fields
    public void clear() {
        messageTF.clear();
        //ipTF.clear();
    }

    // get my IP
    public String getMyIP() {
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getLocalHost();

        } catch (UnknownHostException ex) {
            Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        return inetAddress.getHostAddress();



    }

}
