package se.ju23.typespeeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnvandarService {

    @Autowired
    private AnvandareRepository anvandareRepository;

    // Metod för att kontrollera om ett användarnamn redan finns
    public boolean isAnvandarnamnUnique(String anvandarnamn) {
        return !anvandareRepository.existsByAnvandarnamn(anvandarnamn);
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
        nyAnvandare.setPoang(0); // Initial poäng

        anvandareRepository.save(nyAnvandare); // Spara den nya användaren i databasen
        System.out.println("Ny användare registrerad med användarnamn: " + anvandarnamn);

        return true;
    }
}