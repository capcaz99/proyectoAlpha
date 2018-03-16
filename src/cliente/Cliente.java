/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.net.SocketException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import servidor.Registro;

/**
 *
 * @author CABURTOP
 */
public class Cliente {

    private static int posicionMonstruo;
    private static int posicionMonstruo1;
    private static int posicionRecibida;
    private static int posicionRecibida1;
    private static Registro comp;

    public static void main(String args[]) {
        
            
        
        try {

            String pos;
            int numclientes = 50;
            listenUD[] hilos = new listenUD[numclientes];
            
            String name = "Registro";
            Registry registry = LocateRegistry.getRegistry("localhost"); // server's ip address
            comp = (Registro) registry.lookup(name);
            
            //crear hilos 
            
                for (int i = 0; i < numclientes; i++) {
                     listenUD hilo = new listenUD();
                     hilos[i] = hilo;
                }

                for (int i = 0; i < numclientes; i++) {
                    hilos[i].start();
                }
            
           
            
            
                
        } catch (RemoteException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotBoundException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }

    static class listenUD extends Thread {

        @Override
        public void run() {
            MulticastSocket s = null;
            int id = 0;
            double tiempo0, timpof;
            
            try {
                id = comp.registrar();
                InetAddress group = InetAddress.getByName("228.5.6.7"); // destination multicast group 
                
                s = new MulticastSocket(6789);
                
                
                byte[] buffer = new byte[1000];
                int pos0, pos1, mipos1, mipos2;
                Random r = new Random();
                tiempo0 = System.currentTimeMillis();
                s.joinGroup(group);
                boolean bandera = true;
                while (bandera) {
                   
                    
                    DatagramPacket messageIn
                            = new DatagramPacket(buffer, buffer.length);
                    s.receive(messageIn);
                    ;
                    posicionRecibida = messageIn.getData()[0];
                    posicionRecibida1 = messageIn.getData()[1];
                    
                    posicionMonstruo = posicionRecibida;
                    posicionMonstruo1 = posicionRecibida1;
                    
                    mipos1 = r.nextInt(3);
                    mipos2 = r.nextInt(3);

                    int ganador = messageIn.getData()[2];
                    if (ganador != -1) {
                        if (ganador == id) {
                            timpof = System.currentTimeMillis();
                            System.out.println(timpof - tiempo0);
                            bandera = false;
                            
                        }
                    } else {

                        pos0 = posicionRecibida;
                        pos1 = posicionRecibida1;
                        
                        if (pos0 == mipos1 && pos1 == mipos2) {
                            Socket soc;
                            try {
                                int serverPort = 7896;

                                soc = new Socket("localhost", serverPort);
                                
                                DataOutputStream out
                                        = new DataOutputStream(soc.getOutputStream());

                                out.writeInt(id);

                            } catch (IOException ex) {
                                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
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

        }
    }
}
