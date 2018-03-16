/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CABURTOP
 */
public class RMI extends Thread implements Registro{
    
    private Hashtable<Integer, Integer> registro; 
    @Override
    public void run() {
        try {
            LocateRegistry.createRegistry(1099);
                String name = "Registro";
                RMI engine = new RMI();
                Registro stub = (Registro) UnicastRemoteObject.exportObject(engine, 0);
                Registry registry = LocateRegistry.getRegistry();
                registry.rebind(name, stub);
                System.out.println("Servidor desplegado");
         } catch (RemoteException ex) {
            Logger.getLogger(RMI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public RMI() throws RemoteException {
        super();
        this.registro = new Hashtable<Integer,Integer>();
    }
     
    @Override
    public int registrar() throws RemoteException {
        RunThreads.numeroJugadores++;
        int res = RunThreads.numeroJugadores;
        if(registro.get(res) == null){
            registro.put(res, 0);
            System.out.println("--------------------------------------Registrado: "+res);
        }
        return res;
    }

    @Override
    public void sumarPuntos(int ip) throws RemoteException {
        int cantidad = registro.get(ip);
        System.out.println("Puntaje de "+ ip+ " es igual " + cantidad);
        if(cantidad <4){
            registro.replace(ip, cantidad+1);
            System.out.println("--------------------------------------Añadido a: "+ip);
        }else{
            System.out.println("-------------------Ya hay un ganador-------------");
            RunThreads.ganador = ip;
            int i;
            for (i=1;i<=RunThreads.numeroJugadores;i++){
                registro.replace(i,0);
                System.out.println("Jugador: "+ i +" Puntaje: " +registro.get(i));
            }
            System.out.println("Nuevo: "+ registro.get(ip));
        }
    }
    
}
