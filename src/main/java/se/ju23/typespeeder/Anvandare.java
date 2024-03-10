package se.ju23.typespeeder;

import jakarta.persistence.*;

@Entity
public class Anvandare {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "AnvandarID", nullable = false)
    private int anvandarId;
    @Basic
    @Column(name = "Anvandarnamn", nullable = true, length = 45)
    private String anvandarnamn;
    @Basic
    @Column(name = "Losenord", nullable = true, length = 45)
    private String losenord;
    @Basic
    @Column(name = "Spelnamn", nullable = true, length = 45)
    private String spelnamn;
    @Basic
    @Column(name = "Level", nullable = true)
    private Integer level;
    @Basic
    @Column(name = "Poang", nullable = true)
    private Integer poang;

    public int getAnvandarId() {
        return anvandarId;
    }

    public void setAnvandarId(int anvandarId) {
        this.anvandarId = anvandarId;
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

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getPoang() {
        return poang;
    }

    public void setPoang(Integer poang) {
        this.poang = poang;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Anvandare anvandare = (Anvandare) o;

        if (anvandarId != anvandare.anvandarId) return false;
        if (anvandarnamn != null ? !anvandarnamn.equals(anvandare.anvandarnamn) : anvandare.anvandarnamn != null)
            return false;
        if (losenord != null ? !losenord.equals(anvandare.losenord) : anvandare.losenord != null) return false;
        if (spelnamn != null ? !spelnamn.equals(anvandare.spelnamn) : anvandare.spelnamn != null) return false;
        if (level != null ? !level.equals(anvandare.level) : anvandare.level != null) return false;
        if (poang != null ? !poang.equals(anvandare.poang) : anvandare.poang != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = anvandarId;
        result = 31 * result + (anvandarnamn != null ? anvandarnamn.hashCode() : 0);
        result = 31 * result + (losenord != null ? losenord.hashCode() : 0);
        result = 31 * result + (spelnamn != null ? spelnamn.hashCode() : 0);
        result = 31 * result + (level != null ? level.hashCode() : 0);
        result = 31 * result + (poang != null ? poang.hashCode() : 0);
        return result;
    }
}
