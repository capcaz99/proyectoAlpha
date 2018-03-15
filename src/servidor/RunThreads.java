/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ARENDONCL
 */
public class RunThreads {
public static Hashtable<String, Integer> puntajes = new Hashtable<String, Integer>();

        public static void main(String[] args) {

            UDP hilo1 = new UDP();
            TCPListener hilo2 = new TCPListener();

            hilo1.start(); // hilo para enviar monstruos
            hilo2.runtcp();  // aquí el hilo está empezando dentro del método runtcp   **hilo para recibir respuesta de jugadores

        }
    }
