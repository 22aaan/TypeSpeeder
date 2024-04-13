package se.ju23.typespeeder;

import jakarta.persistence.*;

@Entity // Denna annotation behövs för att definiera klassen som en JPA-entitet
@Table(name = "patch") // Om tabellen heter 'patch' i din databas
public class Patch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // Om din databas använder BIGINT för ID

    @Column(name = "titel", nullable = false, length = 45)
    private String titel;

    @Column(name = "beskrivning", nullable = false, length = 45)
    private String beskrivning;
    public Patch(String titel, String beskrivning) {
        this.titel = titel;
        this.beskrivning = beskrivning;
    }

    // Getters
    public String getTitel() {
        return titel;
    }

    public String getBeskrivning() {
        return beskrivning;
    }

    // Setters
    public void setTitel(String titel) {
        this.titel = titel;
    }

    public void setBeskrivning(String beskrivning) {
        this.beskrivning = beskrivning;
    }

    @Override
    public String toString() {
        return "Patch [titel=" + titel + ", beskrivning=" + beskrivning + "]";
    }
}
