package impl;

import domain.Comanda;
import domain.Farmacist;
import domain.Medicament;
import domain.Personal;
import repo.RepositoryComenzi;
import repo.RepositoryFarmacist;
import repo.RepositoryMedicament;
import repo.RepositoryPersonal;
import services.IObserver;
import services.IServices;
import services.ServiceException;

import java.rmi.RemoteException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceImpl implements IServices {
    private RepositoryMedicament medsRepo;
    private RepositoryPersonal persRepo;
    private RepositoryFarmacist farmRepo;
    private RepositoryComenzi comenziRepo;
    private Map<String, IObserver> loggedUsers;

    public ServiceImpl(RepositoryPersonal persRepo, RepositoryFarmacist farmRepo,RepositoryMedicament medsRepo, RepositoryComenzi comenziRepo) {
        this.medsRepo = medsRepo;
        this.persRepo = persRepo;
        this.farmRepo = farmRepo;
        this.comenziRepo = comenziRepo;
        loggedUsers = new ConcurrentHashMap<>();
    }

    @Override
    public void login_farmacist(String login_id, IObserver client) throws ServiceException {
        if(loggedUsers.get(login_id)!=null)
            throw new ServiceException("User already logged id");
        Farmacist user = farmRepo.find_by_login_id(login_id);
        if(user == null){
            throw new ServiceException("Authentication failed");
        }
        loggedUsers.put(user.getLogin_id(), client);
    }

    @Override
    public void logout_farmacist(Farmacist user, IObserver client) throws ServiceException {
        IObserver local_client = loggedUsers.remove(user.getLogin_id());
        if(local_client == null)
            throw new ServiceException("User not logged in");
    }

    @Override
    public long login_medic(String login_id, IObserver client) throws ServiceException {
        if(loggedUsers.get(login_id)!=null)
            throw new ServiceException("User already logged id");
        Personal user = persRepo.find_by_login_id(login_id);
        if(user == null){
            throw new ServiceException("Authentication failed");
        }
        loggedUsers.put(user.getLogin_id(), client);
        return user.getId();
    }

    @Override
    public void logout_medic(Personal user, IObserver client) throws ServiceException {
        IObserver local_client = loggedUsers.remove(user.getLogin_id());
        if(local_client == null)
            throw new ServiceException("User not logged in");
    }

    @Override
    public Iterable<Medicament> getMeds() throws ServiceException {
        return medsRepo.findAll();
    }

    @Override
    public void saveComanda(Comanda comanda) {
        comenziRepo.save(comanda);
        update();
    }

    private void update() {
        loggedUsers.forEach((key, value) -> {
            try {
                value.statusUpdated();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public Iterable<Comanda> getComenzi() throws ServiceException {
        return comenziRepo.findAll();
    }

    @Override
    public void updateStatusComanda(String status, long id) throws ServiceException {
        comenziRepo.updateStatusComanda(status, id);
        update();
    }

    @Override
    public Map<Medicament, Long> getMedsCant() throws ServiceException {
        return medsRepo.get_medicamente_cantitate();
    }

    @Override
    public void updateStoc() throws ServiceException {
        medsRepo.updateCantitati();
    }

    @Override
    public void updateMedCantitate(Long id, Long val) throws ServiceException {
        medsRepo.updateCantitate(id, val);
    }

}
