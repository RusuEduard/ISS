package repoImpl;

import domain.Medicament;
import repo.RepositoryMedicament;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class RepoMedsDB implements RepositoryMedicament {
    private JDBCUtils dbUtils;

    public RepoMedsDB(Properties props) {
        this.dbUtils = new JDBCUtils(props);
    }

    @Override
    public Medicament findOne(Long aLong) {
        return null;
    }

    @Override
    public Iterable<Medicament> findAll() {
        List<Medicament> meds = new ArrayList<>();
        Connection con = dbUtils.getConnection();
        try(PreparedStatement statement = con.prepareStatement("select * from medicament")) {
            try(ResultSet set = statement.executeQuery()){
                while(set.next()){
                    String nume = set.getString("nume");
                    Long id = set.getLong("id");
                    Medicament med = new Medicament();
                    med.setNume(nume);
                    med.setId(id);
                    meds.add(med);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return meds;
    }

    @Override
    public void save(Medicament entity) {

    }


    @Override
    public Medicament find_by_nume(String nume) {
        return null;
    }

    @Override
    public Map<Medicament, Integer> get_medicamente_cantitate() {
        return null;
    }
}
