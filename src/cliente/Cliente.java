/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;

/**
 *
 * @author CABURTOP
 */
public class Cliente {
    public static void main(String args[]){ 
  	 
	MulticastSocket s =null;
   	 try {
                InetAddress group = InetAddress.getByName("228.5.6.7"); // destination multicast group 
	    	s = new MulticastSocket(6789);
	   	s.joinGroup(group); 

	    	byte[] buffer = new byte[1000];
 	   	//for(int i=0; i< 3; i++) {
                    System.out.println("Waiting for messages");
                    DatagramPacket messageIn = 
			new DatagramPacket(buffer, buffer.length);
 		    s.receive(messageIn);
                    String posicionRecibida = new String(messageIn.getData());
                    char[] splited = posicionRecibida.toCharArray();
                    int pos0 = Character.getNumericValue(splited[0]);
                    int pos1 = Character.getNumericValue(splited[1]);
                    
 		    System.out.println("Posiciones: x=" +pos0+ " y="+pos1);
  	     	//}
	    	s.leaveGroup(group);		
 	    }
         catch (SocketException e){
             System.out.println("Socket: " + e.getMessage());
	 }
         catch (IOException e){
             System.out.println("IO: " + e.getMessage());
         }
	 finally {
            if(s != null) s.close();
        }
    }
}
