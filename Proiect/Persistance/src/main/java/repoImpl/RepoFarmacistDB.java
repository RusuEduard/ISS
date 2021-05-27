package repoImpl;

import domain.Farmacist;
import jdk.jfr.consumer.RecordedStackTrace;
import repo.RepositoryFarmacist;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class RepoFarmacistDB implements RepositoryFarmacist {
    private JDBCUtils dbUtils;

    public RepoFarmacistDB(Properties props) {
        this.dbUtils = new JDBCUtils(props);
    }

    @Override
    public Farmacist findOne(Long aLong) {
        Connection con = dbUtils.getConnection();
        Farmacist farmacist = null;
        try(PreparedStatement statement = con.prepareStatement("select * from farmacist where id=?")){
            statement.setLong(1, aLong);
            try(ResultSet set = statement.executeQuery()){
                if(set.next()){
                    farmacist = new Farmacist();
                    String nume = set.getString("nume");
                    String prenume = set.getString("prenume");
                    String login_id = set.getString("login_id");
                    farmacist.setId(aLong);
                    farmacist.setNume(nume);
                    farmacist.setPrenume(prenume);
                    farmacist.setLogin_id(login_id);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return farmacist;
    }

    @Override
    public Iterable<Farmacist> findAll() {
        return null;
    }

    @Override
    public void save(Farmacist entity) {

    }

    @Override
    public Farmacist find_by_login_id(String login_id) {
        Connection con = dbUtils.getConnection();
        Farmacist farmacist = null;
        try(PreparedStatement statement = con.prepareStatement("select * from farmacist where login_id=?")){
            statement.setString(1, login_id);
            try(ResultSet set = statement.executeQuery()){
                if(set.next()){
                    farmacist = new Farmacist();
                    String nume = set.getString("nume");
                    String prenume = set.getString("prenume");
                    Long id = set.getLong("id");
                    farmacist.setId(id);
                    farmacist.setNume(nume);
                    farmacist.setPrenume(prenume);
                    farmacist.setLogin_id(login_id);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return farmacist;
    }
}
