package domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

public class Personal{

    private Long id;

    private String nume;

    private String prenume;

    private String login_id;

    private Set<Comanda> comenzi;

    public Personal() {
    }

    public Personal(String nume, String prenume, String login_id) {
        this.nume = nume;
        this.prenume = prenume;
        this.login_id = login_id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getLogin_id() {
        return login_id;
    }

    public void setLogin_id(String login_id) {
        this.login_id = login_id;
    }

    public Set<Comanda> getComenzi() {
        return comenzi;
    }

    public void setComenzi(Set<Comanda> comenzi) {
        this.comenzi = comenzi;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
