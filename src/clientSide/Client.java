/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientSide;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author gabri
 */
public class Client {
    
    public static ArrayList<String> users = new ArrayList<>();
    private static HashMap<Integer,String> userMap = new HashMap<>();
    public static ArrayList<String> options = new ArrayList<>();
    public static ArrayList<String> displayItems = new ArrayList<>();
    public static HashMap<Integer, String> map = new HashMap<>();
    
    
    public static void setUsers(ArrayList<String> list){
        users = list;
    }
    
    public static ArrayList<String> getUsers(){
        return users;
    }
    
    public static void main(String[] args) throws InterruptedException {
        try {
            Socket socket = new Socket("127.0.0.1", 8080);
            System.out.println("Connected to server at 127.0.0.1 on port 8080");
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            Scanner sc = new Scanner(System.in);
            Scanner serverScanner = new Scanner(in).useDelimiter("\n");
            
            
            System.out.println("Please insert your name:\n");
            String name = sc.nextLine();
            String clientName = name;
            
            Thread readThread = new Thread(new ClientReader(socket,out,clientName));
            readThread.start();
            readThread.join(1000);

            out.write("INSERT_NAME\n".getBytes());  
            name += "\n";
            
            out.write(name.getBytes());
            out.write("JOIN_SERVER\n".getBytes());
            out.flush();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
