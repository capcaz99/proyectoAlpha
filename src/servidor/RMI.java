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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author CABURTOP
 */
public class RMI extends Thread implements Registro{
    
    private Hashtable<String, Integer> registro; 
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
        this.registro = new Hashtable<String,Integer>();
    }
     
    @Override
    public void registrar(String ip) throws RemoteException {
        if(registro.get(ip) == null){
            registro.put(ip, 0);
            System.out.println("--------------------------------------Registrado: "+ip);
        }
    }

    @Override
    public void sumarPuntos(String ip) throws RemoteException {
        int cantidad = registro.get(ip);
        if(cantidad <4){
        registro.replace(ip, cantidad+1);
        System.out.println("--------------------------------------Añadido a: "+ip);
        }else{
            System.out.println("-------------------Ya hay un ganador-------------");
        }
    }
    
}
