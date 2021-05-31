package services;

import domain.Comanda;
import domain.Farmacist;
import domain.Medicament;
import domain.Personal;

import java.util.Map;

public interface IServices {
    void login_farmacist(String login_id, IObserver client) throws ServiceException;
    void logout_farmacist(Farmacist user, IObserver client) throws ServiceException;
    long login_medic(String login_id, IObserver client) throws ServiceException;
    void logout_medic(Personal user, IObserver client) throws  ServiceException;
    Iterable<Medicament> getMeds() throws ServiceException;
    void saveComanda(Comanda comanda);
    Iterable<Comanda> getComenzi() throws ServiceException;
    void updateStatusComanda(String status, long id) throws ServiceException;
    Map<Medicament, Long> getMedsCant() throws ServiceException;
    void updateStoc() throws ServiceException;
    void updateMedCantitate(Long id, Long val) throws ServiceException;
}
