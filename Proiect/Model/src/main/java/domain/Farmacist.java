package domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


public class Farmacist{

    private Long id;

    private String nume;

    private String prenume;

    private String login_id;

    public Farmacist(String nume, String prenume, String login_id) {
        this.nume = nume;
        this.prenume = prenume;
        this.login_id = login_id;
    }

    public Farmacist() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
