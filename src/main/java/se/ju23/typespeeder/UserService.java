package se.ju23.typespeeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private AnvandareRepository anvandareRepository;

    public boolean registerUser(String anvandarnamn, String losenord, String spelnamn) {
        if (anvandareRepository.existsByAnvandarnamn(anvandarnamn)) {
            return false;
        }

        Anvandare nyAnvandare = new Anvandare();
        nyAnvandare.setAnvandarnamn(anvandarnamn);
        nyAnvandare.setLosenord(losenord); // Observera: i en säker applikation bör du hasha lösenordet
        nyAnvandare.setSpelnamn(spelnamn);

        anvandareRepository.save(nyAnvandare);
        return true;
    }

    // Lägg till en metod för att logga in
    public boolean loginUser(String anvandarnamn, String losenord) {
        Optional<Anvandare> anvandare = anvandareRepository.findByAnvandarnamn(anvandarnamn);

        if (anvandare.isPresent()) {
            // Här bör du jämföra det hashade lösenordet istället för klartext
            return anvandare.get().getLosenord().equals(losenord);
        }

        return false;
    }
}
