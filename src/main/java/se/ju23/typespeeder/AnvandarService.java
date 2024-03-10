package se.ju23.typespeeder;

public class AnvandarService {

    // Metod för att kontrollera om ett användarnamn redan finns
    public boolean isAnvandarnamnUnique(String anvandarnamn) {
        // Här skulle du implementera en sökning i din databas för att se om användarnamnet redan finns
        return true; // Anta att den är unik för detta exempel
    }

    // Metod för att registrera en ny användare
    public boolean registreraNyAnvandare(String anvandarnamn, String losenord, String spelnamn) {
        if (!isAnvandarnamnUnique(anvandarnamn)) {
            System.out.println("Användarnamnet är redan taget.");
            return false;
        }

        // Skapa en ny användare och fyll i detaljerna
        Anvandare nyAnvandare = new Anvandare();
        nyAnvandare.setAnvandarnamn(anvandarnamn);
        nyAnvandare.setLosenord(losenord); // Notera: du bör hasha lösenordet innan lagring i en riktig applikation
        nyAnvandare.setSpelnamn(spelnamn);

        // Här skulle du spara den nya användaren i din databas
        System.out.println("Ny användare registrerad med användarnamn: " + anvandarnamn);

        return true;
    }
}
