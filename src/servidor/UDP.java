/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CABURTOP
 */
public class UDP extends Thread {

    @Override
    public void run() {
        
        MulticastSocket s = null;
        int i = 0;
        
        while (i < 10) { //Cambiar mientras no haya ganador
            try {
                byte [] m = new byte [3];
                Random r = new Random();
                int r1=r.nextInt(3);
                int r2=r.nextInt(3);
                int posiciones[] = {r1, r2};
                //System.out.println("pos1: "+r1+ " pos2: "+r2);
                InetAddress group = InetAddress.getByName("228.5.6.7"); // destination multicast group 
                s = new MulticastSocket(6789);
                s.joinGroup(group);
                
                if (RunThreads.ganador!=-1){
                    m[0] =(byte) 0;
                    m[1] =(byte) 0;
                    m[2]=(byte) RunThreads.ganador;
                    //System.out.println("Ganador : "+ m[2]);
                    DatagramPacket messageOut = new DatagramPacket(m, m.length, group, 6789);
                    s.send(messageOut);
                    RunThreads.ganador = -1;
//                    System.out.println("Data win 0: "+messageOut.getData()[0]);
//                    System.out.println("Data win 1 "+messageOut.getData()[1]);
//                    System.out.println("Data win 2 "+messageOut.getData()[2]);
                }
                else{
                    m[0] =(byte) posiciones[0];
                    m[1] =(byte) posiciones[1];
                    m[2]=(byte) RunThreads.ganador;
                   // byte[] m = posEnviar.getBytes();
                    DatagramPacket messageOut = new DatagramPacket(m, m.length, group, 6789);
                    s.send(messageOut);
//                    System.out.println("Data no 0 "+messageOut.getData()[0]);
//                    System.out.println("Data no 1 "+messageOut.getData()[1]);
//                    System.out.println("Data no 2 "+messageOut.getData()[2]);
                }
                
                
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
}


