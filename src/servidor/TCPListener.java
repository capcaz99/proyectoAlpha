/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;

/**
 *
 * @author ARENDONCL
 */
public class TCPListener {

    public void runtcp() {
        try {
            int serverPort = 7896;
            ServerSocket listenSocket = new ServerSocket(serverPort);
            while (true) {
                System.out.println("Waiting for messages...");
                Socket clientSocket = listenSocket.accept();  // Listens for a connection to be made to this socket and accepts it. The method blocks until a connection is made. 
                Connection c = new Connection(clientSocket);
                c.start();
            }
        } catch (IOException e) {
            System.out.println("Listen :" + e.getMessage());
        }
    }
}

class Connection extends Thread {
    
    
    Hashtable<String, Integer> puntajes = new Hashtable<String, Integer>();
    int puntaje = 0;
    String data;
    DataInputStream in;
    DataOutputStream out;

    Socket clientSocket;

    public Connection(Socket aClientSocket) {
        try {
            clientSocket = aClientSocket;
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Connection:" + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {			                 // an echo server
            data = in.readUTF();
            UDPchiquito unaVez = new UDPchiquito();
            System.out.println("Message received from: " + clientSocket.getRemoteSocketAddress());
            out.writeUTF(data); // las llaves van a ser las direcciones IP
            

            if(!puntajes.containsKey(data)){
                puntajes.put(data, 1); // si no existe, se agrega a la tabla hash
            }
            else
                if (puntajes.get(data)<4)
                puntajes.put(data, puntajes.get(data) + 1);
                else
                    unaVez.run();  // se envía una vez quién ganó esta partida
            System.out.println("miauu:   "+puntajes.get(data));
        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO:" + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

}
