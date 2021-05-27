package repo;

import domain.Farmacist;

public interface RepositoryFarmacist extends Repository<Farmacist, Long> {

    Farmacist find_by_login_id(String login_id);

}
