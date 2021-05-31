package controllers;

import domain.Comanda;
import domain.Medicament;
import services.IObserver;
import services.IServices;
import services.ServiceException;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class FarmacistController  extends UnicastRemoteObject implements IObserver {
    private IServices server;
    private Iface controller;

    public FarmacistController() throws RemoteException {
    }

    public void setIface(Iface ctrl){
        this.controller = ctrl;
    }

    public void setServer(IServices server) {
        this.server = server;
    }

    public void login(String login_id) throws ServiceException {
        server.login_farmacist(login_id, this);
    }

    public Iterable<Comanda> getComenziInAsteptare() throws ServiceException {
        List<Comanda> cmds = StreamSupport.stream(server.getComenzi().spliterator(), false).collect(Collectors.toList());
        List<Comanda> comenzi = StreamSupport.stream(server.getComenzi().spliterator(), false)
                .filter(x -> x.getStatus().equals("In asteptare"))
                .collect(Collectors.toList());
        return comenzi;
    }

    public void refuzaComanda(Comanda comanda) throws ServiceException {
        server.updateStatusComanda("Refuzata!", comanda.getId());
    }

    public void onoreazaComanda(Comanda comanda) throws ServiceException {
        Map<Medicament, Long> medscant = server.getMedsCant();
        List<Medicament> meds = StreamSupport.stream(server.getMeds().spliterator(), false).collect(Collectors.toList());
        for(Medicament med : meds){
            if(comanda.getMedicamente().get(med) != null && medscant.get(med) < comanda.getMedicamente().get(med)){
                throw new ServiceException("Nu sunt suficiente medicamente!");
            }
        }
        for(Medicament med : meds){
            if(comanda.getMedicamente().get(med) != null){
                Long val = medscant.get(med) - comanda.getMedicamente().get(med);
                server.updateMedCantitate(med.getId(), val);
            }
        }
        server.updateStatusComanda("Onorata!", comanda.getId());
    }

    public Map<Medicament, Long> getMedsCant() throws ServiceException{
        return server.getMedsCant();
    }

    @Override
    public void statusUpdated() throws RemoteException{
        controller.initModel();
    }

    public void updateStoc() throws ServiceException {
        server.updateStoc();
    }
}
