/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;



public class UDPchiquito extends Thread{
    
     public void run() {
      MulticastSocket s = null;
        int i = 0;
       
            try {
                InetAddress group = InetAddress.getByName("228.5.6.7"); // destination multicast group 
                s = new MulticastSocket(6789);
                s.joinGroup(group);
                byte[] m = Inet4Address.getLocalHost().getHostAddress().getBytes();
                DatagramPacket messageOut
                        = new DatagramPacket(m, m.length, group, 6789);
                s.send(messageOut);
                s.leaveGroup(group);
                
                
            } catch (SocketException e) {
                System.out.println("Socket: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("IO: " + e.getMessage());
            } finally {
                if (s != null) {
                    s.close();
                }
            }
          try {
              Thread.sleep(2000);
          } catch (InterruptedException ex) {
              Logger.getLogger(UDP.class.getName()).log(Level.SEVERE, null, ex);
          }
        }
    }
    

