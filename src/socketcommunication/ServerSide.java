/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketcommunication;

import java.io.*;
import java.net.*;
import java.util.Enumeration;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ServerSide extends Application {
    
    // Properties
    Label ipLabel = new Label("..");
    
      @Override
    public void start(Stage primaryStage) {
   
        
        Button btn = new Button();
        btn.setText("Listen");
        btn.setOnAction(e -> listen());

        VBox root = new VBox();
        root.getChildren().addAll( ipLabel,btn);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Socket Communication");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    
    
    public void listen(){
        
        getMyIP();
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
    
    public void getMyIP(){
        
        
          String ip;
    try {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface iface = interfaces.nextElement();
            // filters out 127.0.0.1 and inactive interfaces
            if (iface.isLoopback() || !iface.isUp())
                continue;

            Enumeration<InetAddress> addresses = iface.getInetAddresses();
            while(addresses.hasMoreElements()) {
                InetAddress addr = addresses.nextElement();
                ip = addr.getHostAddress();
                System.out.println(iface.getDisplayName() + " " + ip);
                ipLabel.setText(ip);
            }
            
        }
    } catch (SocketException e) {
        throw new RuntimeException(e);
    }
    }
    
    public static void main(String[] args) {
          launch(args);
    }
}
