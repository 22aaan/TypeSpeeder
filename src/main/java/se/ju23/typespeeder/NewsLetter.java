package se.ju23.typespeeder;

import jakarta.persistence.*;

import java.time.LocalDateTime;

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

    @Column(name = "publishDateTime", nullable = false, length = 45)
    private LocalDateTime publishDateTime;

    @Column(name = "Content", nullable = false, length = 255) // assuming a max length of 255
    private String content;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


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

    public LocalDateTime getPublishDateTime() {
        return publishDateTime;
    }

    public void setPublishDateTime(LocalDateTime publishDateTime) {
        this.publishDateTime = publishDateTime;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NewsLetter)) return false;

        NewsLetter that = (NewsLetter) o;

        if (!getUppdateringsId().equals(that.getUppdateringsId())) return false;
        if (!getTitel().equals(that.getTitel())) return false;
        if (!getBeskrivning().equals(that.getBeskrivning())) return false;
        if (!getPublishDateTime().equals(that.getPublishDateTime())) return false;
        return getContent().equals(that.getContent());
    }

    @Override
    public int hashCode() {
        int result = getUppdateringsId().hashCode();
        result = 31 * result + getTitel().hashCode();
        result = 31 * result + getBeskrivning().hashCode();
        result = 31 * result + getPublishDateTime().hashCode();
        result = 31 * result + getContent().hashCode();
        return result;
    }

}
