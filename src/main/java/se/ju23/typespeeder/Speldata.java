package se.ju23.typespeeder;

import jakarta.persistence.*;

@Entity
@Table(name = "speldata")
public class Speldata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "spel_dataid") // Namnet måste matcha exakt som i databasen
    private Long spelDataID;

    @ManyToOne
    @JoinColumn(name = "anvandarid", referencedColumnName = "AnvandarID") // Båda kolumnnamnen måste matcha exakt som i databasen
    private Anvandare anvandare;

    @Column(name = "tid")
    private Long tid;

    @Column(name = "ratta_svar")
    private Integer rattaSvar;

    @Column(name = "svariordning")
    private String svarIOrdning;

    @Column(name = "is_correct")
    private Boolean isCorrect;

    @Column(name = "niva", nullable = false)
    private Integer niva = 1; // Standardvärde satt till 1 för att undvika null-värden

    @Column(name = "flera_ratt") // Detta fält lagrar antalet rätt i rad
    private Integer fleraRatt;

    // Standard getter och setter för spelDataID
    public Long getSpelDataId() {
        return spelDataID;
    }

    public void setSpelDataId(Long spelDataId) {
        this.spelDataID = spelDataId;
    }

    // Standard getter och setter för anvandare
    public Anvandare getAnvandare() {
        return anvandare;
    }

    public void setAnvandare(Anvandare anvandare) {
        this.anvandare = anvandare;
    }

    // Getter och setter för tid
    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    // Getter och setter för rattaSvar
    public Integer getRattaSvar() {
        return rattaSvar;
    }

    public void setRattaSvar(Integer rattaSvar) {
        this.rattaSvar = rattaSvar;
    }

    // Getter och setter för svarIOrdning
    public String getSvarIOrdning() {
        return svarIOrdning;
    }

    public void setSvarIOrdning(String svarIOrdning) {
        this.svarIOrdning = svarIOrdning;
    }

    // Getter och setter för isCorrect
    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    // Getter och setter för niva
    public Integer getNiva() {
        return niva;
    }

    public void setNiva(Integer niva) {
        this.niva = niva;
    }

    // Getter och setter för fleraRatt
    public Integer getFleraRatt() {
        return fleraRatt;
    }

    public void setFleraRatt(Integer fleraRatt) {
        this.fleraRatt = fleraRatt;
    }
}
