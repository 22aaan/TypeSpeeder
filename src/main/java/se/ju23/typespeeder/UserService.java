package se.ju23.typespeeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private AnvandareRepository anvandareRepository;

    private Anvandare currentUser;
    public boolean registerUser(String anvandarnamn, String losenord, String spelnamn) {
        if (anvandareRepository.existsByAnvandarnamn(anvandarnamn)) {
            return false;
        }

        String nyAnvandareStoraBokstavar = anvandarnamn.toUpperCase(Locale.ROOT);
        Anvandare nyAnvandare = new Anvandare();
        nyAnvandare.setAnvandarnamn(nyAnvandareStoraBokstavar);
        nyAnvandare.setLosenord(losenord);
        nyAnvandare.setSpelnamn(spelnamn);
        nyAnvandare.setPoang(0);

        anvandareRepository.save(nyAnvandare);
        return true;
    }

    public Optional<Anvandare> loginUser(String anvandarnamn, String losenord) {
        Optional<Anvandare> userOptional = anvandareRepository.findByAnvandarnamn(anvandarnamn);
        if (userOptional.isPresent()) {
            Anvandare user = userOptional.get();
            if (user.getAnvandarnamn().equals(anvandarnamn) && user.getLosenord().equals(losenord)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
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

    public void saveUser(Anvandare anvandare) {
        anvandareRepository.save(anvandare);
    }
    public void setCurrentUserByUsername(String username) {
        Optional<Anvandare> userOpt = anvandareRepository.findByAnvandarnamn(username);
        userOpt.ifPresent(user -> this.currentUser = user);
    }

    public Anvandare getCurrentUser() {
        return this.currentUser;
    }
}