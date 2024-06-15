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
    @JoinColumn(name = "anvandarid", referencedColumnName = "AnvandarID")
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
    private Integer niva = 1;

    @Column(name = "flera_ratt")
    private Integer fleraRatt;


    public Long getSpelDataId() {
        return spelDataID;
    }

    public void setSpelDataId(Long spelDataId) {
        this.spelDataID = spelDataId;
    }


    public Anvandare getAnvandare() {
        return anvandare;
    }

    public void setAnvandare(Anvandare anvandare) {
        this.anvandare = anvandare;
    }

    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    public Integer getRattaSvar() {
        return rattaSvar;
    }

    public void setRattaSvar(Integer rattaSvar) {
        this.rattaSvar = rattaSvar;
    }

    public String getSvarIOrdning() {
        return svarIOrdning;
    }

    public void setSvarIOrdning(String svarIOrdning) {
        this.svarIOrdning = svarIOrdning;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public Integer getNiva() {
        return niva;
    }

    public void setNiva(Integer niva) {
        this.niva = niva;
    }

    public Integer getFleraRatt() {
        return fleraRatt;
    }

    public void setFleraRatt(Integer fleraRatt) {
        this.fleraRatt = fleraRatt;
    }
}