package se.ju23.typespeeder;

import jakarta.persistence.*;

@Entity
@Table(name = "news_letter", schema = "typespeeder", catalog = "")
public class NewsLetter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UppdateringsID", nullable = false)
    private Integer uppdateringsId;


    @Column(name = "Titel", nullable = false, length = 45)
    private String titel;

    @Column(name = "Beskrivning", nullable = false, length = 45)
    private String beskrivning;

    @Column(name = "Publiceringsdatum", nullable = false, length = 45)
    private String publiceringsdatum;

    // Getters och setters

    public Integer getUppdateringsId() {
        return uppdateringsId;
    }

    public void setUppdateringsId(Integer uppdateringsId) {
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

    // Övriga metoder som equals() och hashCode() bör också uppdateras för att hantera Long.


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Uppdatering that = (Uppdatering) o;

        if (uppdateringsId != that.getUppdateringsId()) return false;
        if (titel != null ? !titel.equals(that.getTitel()) : that.getTitel() != null) return false;
        if (beskrivning != null ? !beskrivning.equals(that.getBeskrivning()) : that.getBeskrivning() != null) return false;
        if (publiceringsdatum != null ? !publiceringsdatum.equals(that.getPubliceringsdatum()) : that.getPubliceringsdatum() != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = uppdateringsId != null ? uppdateringsId.hashCode() : 0;
        result = 31 * result + (titel != null ? titel.hashCode() : 0);
        result = 31 * result + (beskrivning != null ? beskrivning.hashCode() : 0);
        result = 31 * result + (publiceringsdatum != null ? publiceringsdatum.hashCode() : 0);
        return result;
    }

}

