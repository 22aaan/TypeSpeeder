package se.ju23.typespeeder;

import jakarta.persistence.*;

@Entity
@Table(name = "uppdatering", schema = "typespeeder", catalog = "")
public class Uppdatering {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "UppdateringsID", nullable = false)
    private int uppdateringsId;
    @Basic
    @Column(name = "Titel", nullable = false, length = 45)
    private String titel;
    @Basic
    @Column(name = "Beskrivning", nullable = false, length = 45)
    private String beskrivning;
    @Basic
    @Column(name = "Publiceringsdatum", nullable = false, length = 45)
    private String publiceringsdatum;

    public int getUppdateringsId() {
        return uppdateringsId;
    }

    public void setUppdateringsId(int uppdateringsId) {
        this.uppdateringsId = uppdateringsId;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getBeskrivning() {
        return beskrivning;
    }

    public void setBeskrivning(String beskrivning) {
        this.beskrivning = beskrivning;
    }

    public String getPubliceringsdatum() {
        return publiceringsdatum;
    }

    public void setPubliceringsdatum(String publiceringsdatum) {
        this.publiceringsdatum = publiceringsdatum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Uppdatering that = (Uppdatering) o;

        if (uppdateringsId != that.uppdateringsId) return false;
        if (titel != null ? !titel.equals(that.titel) : that.titel != null) return false;
        if (beskrivning != null ? !beskrivning.equals(that.beskrivning) : that.beskrivning != null) return false;
        if (publiceringsdatum != null ? !publiceringsdatum.equals(that.publiceringsdatum) : that.publiceringsdatum != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uppdateringsId;
        result = 31 * result + (titel != null ? titel.hashCode() : 0);
        result = 31 * result + (beskrivning != null ? beskrivning.hashCode() : 0);
        result = 31 * result + (publiceringsdatum != null ? publiceringsdatum.hashCode() : 0);
        return result;
    }
}
