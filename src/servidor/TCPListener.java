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
            
            System.out.println(RunThreads.puntajes.get(data.trim()));
            
            if(!RunThreads.puntajes.containsKey(data.trim())){
                RunThreads.puntajes.put(data.trim(), 1); // si no existe, se agrega a la tabla hash
                System.out.println("es la primera vez");
                System.out.println("miauuuuuuuuu "+RunThreads.puntajes.containsKey(data.trim()));

            }
            else{
                
                if (RunThreads.puntajes.get(data.trim())<4){
                RunThreads.puntajes.put(data.trim(), RunThreads.puntajes.get(data.trim()) + 1);
                System.out.println("suma ");
                
                }
                else
                    unaVez.run();  // se envía una vez quién ganó esta partida
            }
            System.out.println("miauu:   "+RunThreads.puntajes.get(data.trim()));
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
