package se.ju23.typespeeder;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "newsletter", schema = "typespeeder")
public class NewsLetter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UppdateringsID", nullable = false)
    private Integer uppdateringsId;

    @Column(name = "Titel", nullable = false, length = 45)
    private String titel;

    @Column(name = "Beskrivning", nullable = false, length = 255) // Assuming a max length of 255
    private String beskrivning;

    @Column(name = "Publiceringsdatum", nullable = false)
    private LocalDateTime publiceringsdatum;

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

    public LocalDateTime getPubliceringsdatum() {
        return publiceringsdatum;
    }

    public void setPubliceringsdatum(LocalDateTime publiceringsdatum) {
        this.publiceringsdatum = publiceringsdatum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NewsLetter)) return false;
        NewsLetter that = (NewsLetter) o;
        return Objects.equals(getUppdateringsId(), that.getUppdateringsId()) &&
                Objects.equals(getTitel(), that.getTitel()) &&
                Objects.equals(getBeskrivning(), that.getBeskrivning()) &&
                Objects.equals(getPubliceringsdatum(), that.getPubliceringsdatum());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUppdateringsId(), getTitel(), getBeskrivning(), getPubliceringsdatum());
    }
}
