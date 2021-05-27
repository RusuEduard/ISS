package services;

import domain.Comanda;
import domain.Farmacist;
import domain.Medicament;
import domain.Personal;

public interface IServices {
    void login_farmacist(String login_id, IObserver client) throws ServiceException;
    void logout_farmacist(Farmacist user, IObserver client) throws ServiceException;
    long login_medic(String login_id, IObserver client) throws ServiceException;
    void logout_medic(Personal user, IObserver client) throws  ServiceException;
    Iterable<Medicament> getMeds() throws ServiceException;
    void saveComanda(Comanda comanda);
    Iterable<Comanda> getComenzi() throws ServiceException;
}
