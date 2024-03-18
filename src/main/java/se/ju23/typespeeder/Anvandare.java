package se.ju23.typespeeder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;


@Entity
@Table(name = "anvandare")
public class Anvandare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long anvandarID;

    @Column(name = "Anvandarnamn", nullable = false, unique = true)
    private String anvandarnamn;

    @Column(name = "Losenord", nullable = false)
    private String losenord;

    @Column(name = "Spelnamn", nullable = false)
    private String spelnamn;

    // ... övriga fält och metoder ...

    public Long getAnvandarID() {
        return anvandarID;
    }

    public void setAnvandarID(Long anvandarID) {
        this.anvandarID = anvandarID;
    }

    public String getAnvandarnamn() {
        return anvandarnamn;
    }

    public void setAnvandarnamn(String anvandarnamn) {
        this.anvandarnamn = anvandarnamn;
    }

    public String getLosenord() {
        return losenord;
    }

    public void setLosenord(String losenord) {
        this.losenord = losenord;
    }

    public String getSpelnamn() {
        return spelnamn;
    }

    public void setSpelnamn(String spelnamn) {
        this.spelnamn = spelnamn;
    }

    // ... getters och setters för övriga fält ...
}
