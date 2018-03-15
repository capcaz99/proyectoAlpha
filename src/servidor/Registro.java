/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author CABURTOP
 */
public interface Registro extends Remote {
    public void registrar (String ip) throws RemoteException;
    public void sumarPuntos (String ip) throws RemoteException;
}
