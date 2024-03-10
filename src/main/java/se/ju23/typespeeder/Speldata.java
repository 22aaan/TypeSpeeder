package se.ju23.typespeeder;

import jakarta.persistence.*;

@Entity
public class Speldata {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "SpelDataID", nullable = false)
    private int spelDataId;
    @Basic
    @Column(name = "AnvandarID", nullable = false)
    private int anvandarId;
    @Basic
    @Column(name = "Niva", nullable = false, length = 45)
    private String niva;

    public int getSpelDataId() {
        return spelDataId;
    }

    public void setSpelDataId(int spelDataId) {
        this.spelDataId = spelDataId;
    }

    public int getAnvandarId() {
        return anvandarId;
    }

    public void setAnvandarId(int anvandarId) {
        this.anvandarId = anvandarId;
    }

    public String getNiva() {
        return niva;
    }

    public void setNiva(String niva) {
        this.niva = niva;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Speldata speldata = (Speldata) o;

        if (spelDataId != speldata.spelDataId) return false;
        if (anvandarId != speldata.anvandarId) return false;
        if (niva != null ? !niva.equals(speldata.niva) : speldata.niva != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = spelDataId;
        result = 31 * result + anvandarId;
        result = 31 * result + (niva != null ? niva.hashCode() : 0);
        return result;
    }
}
