package repo;

import domain.Medicament;

import java.util.Map;

public interface RepositoryMedicament extends Repository<Medicament, Long> {

    Medicament find_by_nume(String nume);

    Map<Medicament, Long> get_medicamente_cantitate();

    void updateCantitati();

    void updateCantitate(Long id, Long val);

}
