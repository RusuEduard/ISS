package repo;

import domain.Medicament;

import java.util.Map;

public interface RepositoryMedicament extends Repository<Medicament, Long> {

    Medicament find_by_nume(String nume);

    Map<Medicament, Integer> get_medicamente_cantitate();

}
