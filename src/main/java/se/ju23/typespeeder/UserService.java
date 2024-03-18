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
            return false; // Användarnamnet är redan taget
        }

        Anvandare nyAnvandare = new Anvandare();
        nyAnvandare.setAnvandarnamn(anvandarnamn);
        nyAnvandare.setLosenord(losenord);
        nyAnvandare.setSpelnamn(spelnamn);

        anvandareRepository.save(nyAnvandare);
        return true;
    }

    public Optional<Anvandare> loginUser(String username, String password) {
        return anvandareRepository.findByAnvandarnamnAndLosenord(username, password);
    }

    public boolean updateUser(Long anvandarID, String newAnvandarnamn, String newLosenord, String newSpelnamn) {
        Optional<Anvandare> anvandareOptional = anvandareRepository.findById(anvandarID);
        if (anvandareOptional.isPresent()) {
            Anvandare anvandare = anvandareOptional.get();
            if (newAnvandarnamn != null && !newAnvandarnamn.isEmpty()) {
                anvandare.setAnvandarnamn(newAnvandarnamn);
            }
            if (newLosenord != null && !newLosenord.isEmpty()) {
                anvandare.setLosenord(newLosenord);
            }
            if (newSpelnamn != null && !newSpelnamn.isEmpty()) {
                anvandare.setSpelnamn(newSpelnamn);
            }
            anvandareRepository.save(anvandare);
            return true;
        }
        return false;
    }

    public Optional<Anvandare> findByAnvandarnamn(String anvandarnamn) {
        return anvandareRepository.findByAnvandarnamn(anvandarnamn);
    }

}
