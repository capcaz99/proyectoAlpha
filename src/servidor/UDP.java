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
                int posiciones[] = {r.nextInt(3), r.nextInt(3)};
                InetAddress group = InetAddress.getByName("228.5.6.7"); // destination multicast group 
                s = new MulticastSocket(6789);
                s.joinGroup(group);
                
                if (RunThreads.ganador==-1){
                    m[2]=(byte) RunThreads.ganador;
                    RunThreads.ganador = -1;
                    DatagramPacket messageOut = new DatagramPacket(m, m.length, group, 6789);
                    s.send(messageOut);
                }
                else{
                    m[0] =(byte) posiciones[0];
                    m[1] =(byte) posiciones[1];
                   // byte[] m = posEnviar.getBytes();
                    DatagramPacket messageOut = new DatagramPacket(m, m.length, group, 6789);
                    s.send(messageOut);
                    String mensaje =new String(messageOut.getData());
                    System.out.println("Mensaje: "+ mensaje );
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


