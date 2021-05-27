package domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

public class Comanda implements Serializable {
    private Long id;
    private LocalDate data;
    private String status;
    private String tip;
    private Long terminal;
    private Long personal_id;
    private Map<Medicament, Long> medicamente;

    public Comanda(LocalDate data, String status, String tip, Long terminal) {
        this.data = data;
        this.status = status;
        this.tip = tip;
        this.terminal = terminal;
    }

    public Comanda() {
    }

    public Long getPersonal_id() {
        return personal_id;
    }

    public void setPersonal_id(Long personal_id) {
        this.personal_id = personal_id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public Long getTerminal() {
        return terminal;
    }

    public void setTerminal(Long terminal) {
        this.terminal = terminal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<Medicament, Long> getMedicamente() {
        return medicamente;
    }

    public void setMedicamente(Map<Medicament, Long> medicamente) {
        this.medicamente = medicamente;
    }
}
