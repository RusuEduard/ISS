package repoImpl;

import domain.Personal;
import repo.RepositoryPersonal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RepoPersonalDB implements RepositoryPersonal {

    private JDBCUtils dbUtils;

    public RepoPersonalDB(Properties props) {
        this.dbUtils = new JDBCUtils(props);
    }

    @Override
    public Personal findOne(Long aLong) {
        Connection con = dbUtils.getConnection();
        Personal personal = null;
        try(PreparedStatement statement = con.prepareStatement("select * from personal where id = ?")){
            statement.setLong(1, aLong);
            try(ResultSet set = statement.executeQuery()){
                if(set.next()){
                    personal = new Personal();
                    String nume = set.getString("nume");
                    String prenume = set.getString("prenume");
                    String login_id = set.getString("login_id");
                    personal.setId(aLong);
                    personal.setNume(nume);
                    personal.setPrenume(prenume);
                    personal.setLogin_id(login_id);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return personal;
    }

    @Override
    public Iterable<Personal> findAll() {
        Connection con = dbUtils.getConnection();
        List<Personal> personal = new ArrayList<>();
        try(PreparedStatement statement = con.prepareStatement("select * from personal")){
            try(ResultSet set = statement.executeQuery()){
                while(set.next()){
                    Personal pers = new Personal();
                    String nume = set.getString("nume");
                    String prenume = set.getString("prenume");
                    String login_id = set.getString("login_id");
                    pers.setNume(nume);
                    pers.setPrenume(prenume);
                    pers.setLogin_id(login_id);
                    personal.add(pers);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return personal;
    }

    @Override
    public void save(Personal entity) {
        Connection con = dbUtils.getConnection();
        try(PreparedStatement statement = con.prepareStatement("insert into personal(nume, prenume, login_id) values (?, ?, ?)")){
            statement.setString(1, entity.getNume());
            statement.setString(2, entity.getPrenume());
            statement.setString(3, entity.getLogin_id());
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Personal find_by_login_id(String login_id) {
        Connection con = dbUtils.getConnection();
        Personal personal = null;
        try(PreparedStatement statement = con.prepareStatement("select * from personal where login_id = ?")){
            statement.setString(1, login_id);
            try(ResultSet set = statement.executeQuery()){
                if(set.next()){
                    personal = new Personal();
                    String nume = set.getString("nume");
                    String prenume = set.getString("prenume");
                    Long id = set.getLong("id");
                    personal.setId(id);
                    personal.setNume(nume);
                    personal.setPrenume(prenume);
                    personal.setLogin_id(login_id);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return personal;
    }
}
