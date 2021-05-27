package controllers;

import domain.Comanda;
import domain.Medicament;
import services.IObserver;
import services.IServices;
import services.ServiceException;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class PersonalController extends UnicastRemoteObject implements IObserver {
    private IServices server;
    public PersonalController() throws RemoteException {
    }

    public void setServer(IServices server){
        this.server = server;
    }

    public long login_personal(String id) throws ServiceException{
        return server.login_medic(id, this);
    }

    public Iterable<Medicament> get_meds() throws ServiceException {
        return server.getMeds();
    }

    public void saveComanda(Comanda comanda){
        server.saveComanda(comanda);
    }

    public Iterable<Comanda> getComenzi() throws ServiceException {
        return server.getComenzi();
    }
}
