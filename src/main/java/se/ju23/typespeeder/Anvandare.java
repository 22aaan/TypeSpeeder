package se.ju23.typespeeder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "anvandare") // Se till att detta namn matchar din databastabell exakt
public class Anvandare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AnvandarID") // Se till att namnet matchar databaskolumnens namn exakt
    private Long anvandarID;

    @Column(name = "Anvandarnamn", nullable = false, unique = true)
    private String anvandarnamn;

    @Column(name = "Losenord", nullable = false)
    private String losenord;

    @Column(name = "Spelnamn", nullable = false)
    private String spelnamn;

    @Column(name = "poang")
    private Integer poang = 0;

    @Column(name = "level")
    private Integer level = 1; // Nytt fält för användarens nivå


    public Anvandare() {
        this.poang = 0;
        this.level = 1;
    }

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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getPoang() {
        return poang;
    }

    public void setPoang(int poang) {
        this.poang = poang;
    }

    public void addPoang(int additionalPoints) {
        this.poang += additionalPoints;
        updateLevel();
    }

    private void updateLevel() {
        if (this.poang == null) {
            this.poang = 0; // Ensure poang is not null.
        }
        if (this.level == null) {
            this.level = 1; // Ensure level is not null.
        }

        int newLevel = 1 + this.poang / 6;
        if (newLevel > this.level) {
            this.level = newLevel;
            System.out.println("Grattis! Du har nått level " + this.level);
        }
    }
    public void removePoints(int pointsToRemove) {
        if (this.poang == null) {
            this.poang = 0;
        }
        this.poang = Math.max(this.poang - pointsToRemove, 0); // Säkerställ att poäng inte går under 0
    }



    @Override
    public String toString() {
        return "Anvandare{" +
                "anvandarID=" + anvandarID +
                ", anvandarnamn='" + anvandarnamn + '\'' +
                ", losenord='" + losenord + '\'' +
                ", spelnamn='" + spelnamn + '\'' +
                ", poang=" + poang +
                ", level=" + level +
                '}';
    }
}