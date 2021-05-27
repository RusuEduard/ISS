package repo;

import domain.Personal;

public interface RepositoryPersonal extends Repository<Personal, Long>{

    Personal find_by_login_id(String login_id);

}
