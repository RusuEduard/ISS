package repo;

import domain.Comanda;

public interface RepositoryComenzi extends Repository<Comanda, Long> {

    Iterable<Comanda> find_by_terminal(Long terminal);

    void updateStatusComanda(String status, Long id);
}
