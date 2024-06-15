package se.ju23.typespeeder;

public class Spelresultat {
    private String spelnamn;
    private Integer resultat;

    public Spelresultat(String spelnamn, Integer resultat) {
        this.spelnamn = spelnamn;
        this.resultat = resultat;
    }

    // Getters och setters
    public String getSpelnamn() { return spelnamn; }
    public void setSpelnamn(String spelnamn) { this.spelnamn = spelnamn; }

    public Integer getResultat() { return resultat; }
    public void setResultat(Integer resultat) { this.resultat = resultat; }
}