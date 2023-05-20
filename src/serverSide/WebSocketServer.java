/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverSide;

import serverSide.ClientConnection;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class WebSocketServer {
  

    public static ConcurrentLinkedQueue<String> currentUsers = new ConcurrentLinkedQueue<>();
    public static ConcurrentHashMap<String,Socket> connectedClients = new ConcurrentHashMap<>();


    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8080);
        try {
            System.out.println("Server has started on 127.0.0.1:8080.\r\nWaiting for a connection…");
            while (true) {
                Socket client = server.accept();
                System.out.println("A client connected.");
                ClientConnection clientConnection = new ClientConnection(client);
                Thread newClient = new Thread(new ClientConnection(client));
                newClient.start();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    
    /**
     * These methods avoid race conditions on the currentUser List.
     */
    public static void addUser(String name){
        synchronized(currentUsers){
            currentUsers.add(name);
            System.out.println(name);
        }
    }
    public static ArrayList<String> getCurrentUsers(){
        synchronized(currentUsers){
            return new ArrayList<String>(currentUsers);
        }
    }
    
}
