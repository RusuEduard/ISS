package repoImpl;

import domain.Comanda;
import domain.Medicament;
import repo.RepositoryComenzi;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class RepoComenziDB implements RepositoryComenzi {

    private JDBCUtils dbUtils;

    public RepoComenziDB(Properties props){
        this.dbUtils = new JDBCUtils(props);
    }

    @Override
    public Iterable<Comanda> find_by_terminal(Long terminal) {
        List<Comanda> comenzi = new ArrayList<>();
        Connection con = dbUtils.getConnection();
        try(PreparedStatement statement = con.prepareStatement("select * from comanda where terminal = ?")){
            statement.setLong(1, terminal);
            try(ResultSet set = statement.executeQuery()){
                while(set.next()){
                    long id_comanda = set.getLong("id");
                    String status = set.getString("status");
                    String tip = set.getString("tip");
                    LocalDate data = set.getDate("data").toLocalDate();
                    long id_personal = set.getLong("id_personal");

                    Comanda comanda = new Comanda();
                    comanda.setTip(tip);
                    comanda.setStatus(status);
                    comanda.setData(data);
                    comanda.setId(id_comanda);
                    comanda.setTerminal(terminal);
                    comanda.setPersonal_id(id_personal);
                    try(PreparedStatement statement2 = con.prepareStatement("select mc.cantitate as cantitate, m.nume as nume from medicament_comanda mc inner join medicament m on m.id = mc.id_medicament where mc.id_comanda = ?")){
                        statement2.setLong(1, id_comanda);
                        try(ResultSet set2 = statement2.executeQuery()){
                            Map<Medicament, Long> med_cant = new HashMap<>();
                            while(set2.next()){
                                String nume = set2.getString("nume");
                                long cantitate = set2.getLong("cantitate");
                                Medicament m = new Medicament();
                                m.setNume(nume);
                                med_cant.put(m, cantitate);
                            }
                            comanda.setMedicamente(med_cant);
                        }
                    }
                    comenzi.add(comanda);
                }
                return comenzi;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateStatusComanda(String status, Long id) {
        Connection con = dbUtils.getConnection();
        try(PreparedStatement statement = con.prepareStatement("UPDATE comanda SET status = ? WHERE id = ?")) {
            statement.setString(1, status);
            statement.setLong(2, id);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Comanda findOne(Long aLong) {
        Connection con = dbUtils.getConnection();
        try(PreparedStatement statement = con.prepareStatement("select * from comanda where id = ?")){
            statement.setLong(1, aLong);
            try(ResultSet set = statement.executeQuery()){
                if(set.next()){
                    long terminal = set.getLong("terminal");
                    String status = set.getString("status");
                    String tip = set.getString("tip");
                    LocalDate data = set.getDate("data").toLocalDate();
                    long id_personal = set.getLong("id_personal");

                    Comanda comanda = new Comanda();
                    comanda.setTip(tip);
                    comanda.setStatus(status);
                    comanda.setData(data);
                    comanda.setId(aLong);
                    comanda.setTerminal(terminal);
                    comanda.setPersonal_id(id_personal);
                    try(PreparedStatement statement2 = con.prepareStatement("select mc.cantitate as cantitate, m.nume as nume from medicament_comanda mc inner join medicament m on m.id = mc.id_medicament where mc.id_comanda = ?")){
                        statement2.setLong(1, aLong);
                        try(ResultSet set2 = statement2.executeQuery()){
                            Map<Medicament, Long> med_cant = new HashMap<>();
                            while(set2.next()){
                                String nume = set2.getString("nume");
                                long cantitate = set2.getLong("cantitate");
                                Medicament m = new Medicament();
                                m.setNume(nume);
                                med_cant.put(m, cantitate);
                            }
                            comanda.setMedicamente(med_cant);
                            return comanda;
                        }
                    }
                }

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Iterable<Comanda> findAll() {
        List<Comanda> comenzi = new ArrayList<>();
        Connection con = dbUtils.getConnection();
        try(PreparedStatement statement = con.prepareStatement("select * from comanda")){
            try(ResultSet set = statement.executeQuery()){
                while(set.next()){
                    long id_comanda = set.getLong("id");
                    long terminal = set.getLong("terminal");
                    String status = set.getString("status");
                    String tip = set.getString("tip");
                    LocalDate data = set.getDate("data").toLocalDate();
                    long id_personal = set.getLong("id_personal");

                    Comanda comanda = new Comanda();
                    comanda.setTip(tip);
                    comanda.setStatus(status);
                    comanda.setData(data);
                    comanda.setId(id_comanda);
                    comanda.setTerminal(terminal);
                    comanda.setPersonal_id(id_personal);
                    try(PreparedStatement statement2 = con.prepareStatement("select mc.cantitate as cantitate, m.nume as nume, m.id as id from medicament_comanda mc inner join medicament m on m.id = mc.id_medicament where mc.id_comanda = ?")){
                        statement2.setLong(1, id_comanda);
                        try(ResultSet set2 = statement2.executeQuery()){
                            Map<Medicament, Long> med_cant = new HashMap<>();
                            while(set2.next()){
                                long id = set2.getLong("id");
                                String nume = set2.getString("nume");
                                long cantitate = set2.getLong("cantitate");
                                Medicament m = new Medicament();
                                m.setId(id);
                                m.setNume(nume);
                                med_cant.put(m, cantitate);
                            }
                            comanda.setMedicamente(med_cant);
                        }
                    }
                    comenzi.add(comanda);
                }
                return comenzi;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(Comanda entity) {
        Connection con = dbUtils.getConnection();
        try(PreparedStatement statement = con.prepareStatement("insert into comanda(data, status, tip, terminal, id_personal) values (?, ?, ?, ?, ?) returning id")){
            statement.setDate(1, Date.valueOf(entity.getData()));
            statement.setString(2, entity.getStatus());
            statement.setString(3, entity.getTip());
            statement.setLong(4, entity.getTerminal());
            statement.setLong(5, entity.getPersonal_id());
            try(ResultSet set = statement.executeQuery()) {
                if (set.next()) {
                    Long id = set.getLong("id");
                    try (PreparedStatement statement2 = con.prepareStatement("insert into medicament_comanda(id_medicament, id_comanda, cantitate) values (?, ?, ?);")) {
                        for (var el : entity.getMedicamente().entrySet()) {
                            statement2.setLong(1, el.getKey().getId());
                            statement2.setLong(2, id);
                            statement2.setLong(3, el.getValue());
                            statement2.executeUpdate();
                        }
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
