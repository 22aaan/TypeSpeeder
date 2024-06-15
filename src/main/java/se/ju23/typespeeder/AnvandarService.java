package se.ju23.typespeeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnvandarService {

    @Autowired
    private AnvandareRepository anvandareRepository;

    public boolean isAnvandarnamnUnique(String anvandarnamn) {
        return !anvandareRepository.existsByAnvandarnamn(anvandarnamn);
    }

    public boolean registreraNyAnvandare(String anvandarnamn, String losenord, String spelnamn) {
        if (!isAnvandarnamnUnique(anvandarnamn)) {
            System.out.println("Användarnamnet är redan taget.");
            return false;
        }

        Anvandare nyAnvandare = new Anvandare();
        nyAnvandare.setAnvandarnamn(anvandarnamn);
        nyAnvandare.setLosenord(losenord);
        nyAnvandare.setSpelnamn(spelnamn);
        nyAnvandare.setPoang(0);

        anvandareRepository.save(nyAnvandare); // Spara den nya användaren i databasen
        System.out.println("Ny användare registrerad med användarnamn: " + anvandarnamn);

        return true;
    }
}