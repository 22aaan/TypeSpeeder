package se.ju23.typespeeder;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Patch")
public class Patch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "patchVersion", nullable = false, length = 45)
    private String patchVersion;  // As expected by the test

    @Column(name = "realeaseDateTime", nullable = false)
    private LocalDateTime realeaseDateTime;  // As expected by the test

    // Constructor
    public Patch() {
    }

    // Additional constructor your test might be assuming (not required by JPA but could be tested for)
    public Patch(String patchVersion, LocalDateTime realeaseDateTime) {
        this.patchVersion = patchVersion;
        this.realeaseDateTime = realeaseDateTime;
    }

    // Getter and setter for patchVersion
    public String getpatchVersion() {
        return patchVersion;
    }

    public void setpatchVersion(String patchVersion) {
        this.patchVersion = patchVersion;
    }

    // Getter for realeaseDateTime as expected by the test
    public LocalDateTime getRealeaseDateTime() {
        return realeaseDateTime;
    }

    public void setRealeaseDateTime(LocalDateTime realeaseDateTime) {
        this.realeaseDateTime = realeaseDateTime;
    }

}
