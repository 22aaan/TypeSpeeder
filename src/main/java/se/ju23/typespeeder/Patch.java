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
    private String patchVersion;

    @Column(name = "realeaseDateTime", nullable = false)
    private LocalDateTime realeaseDateTime;

    public Patch() {
    }

    public Patch(String patchVersion, LocalDateTime realeaseDateTime) {
        this.patchVersion = patchVersion;
        this.realeaseDateTime = realeaseDateTime;
    }

    public String getpatchVersion() {
        return patchVersion;
    }

    public void setpatchVersion(String patchVersion) {
        this.patchVersion = patchVersion;
    }

    public LocalDateTime getRealeaseDateTime() {
        return realeaseDateTime;
    }

    public void setRealeaseDateTime(LocalDateTime realeaseDateTime) {
        this.realeaseDateTime = realeaseDateTime;
    }

}
