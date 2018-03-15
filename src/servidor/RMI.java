/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.rmi.RemoteException;

/**
 *
 * @author CABURTOP
 */
public class RMI extends Thread implements Registro{

    @Override
    public void run() {
    }
    
    
    @Override
    public void registrar(String ip) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
}
