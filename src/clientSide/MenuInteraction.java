/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientSide;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import static serverSide.WebSocketServer.connectedClients;
import static serverSide.WebSocketServer.currentUsers;

/**
 *
 * @author gabri
 */
public class MenuInteraction implements Runnable {

    public String name;
    private OutputStream out;
    Scanner sc = new Scanner(System.in);

    public MenuInteraction(OutputStream out, String name) {
        this.out = out;
        this.name = name;
    }
    
    public synchronized void setName(String name){
        this.name = name;
    }

    @Override
    public void run() {
        try{
            System.out.println("Main Menu\n");
            System.out.println("Please Select an Option: [numbers only]");
            System.out.println("1_ Check Available People");
            System.out.println("2_ Chat History");
            System.out.println("3_ Exit");

            int decision = sc.nextInt();
            if(decision == 1){
               out.write("DECISION_1\n".getBytes());
               
               MenuItem1 item1 =  new MenuItem1(out,name);
               item1.display();
            }
            
        }catch(Exception e){
                
            String msg = e.getMessage() + "\n";
            try {
                out.write(msg.getBytes());
            } catch (IOException ex) {
                Logger.getLogger(MenuInteraction.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
