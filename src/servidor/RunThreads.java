/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ARENDONCL
 */
public class RunThreads {
    
    public static String ganador = "";


        public static void main(String[] args) {

           
            try {
                RMI hilo3 = new RMI();
                 UDP hilo1 = new UDP();
                TCPListener hilo2 = new TCPListener();
                
                System.out.println("Empezar 1");
                hilo1.start(); // hilo para enviar monstruos
                System.out.println("Empezar 3");
                hilo3.start();
               System.out.println("Empezar 2");
                hilo2.runtcp();  // aquí el hilo está empezando dentro del método runtcp   **hilo para recibir respuesta de jugadores
                
            } catch (RemoteException ex) {
                Logger.getLogger(RunThreads.class.getName()).log(Level.SEVERE, null, ex);
            }

           
        }
    }
