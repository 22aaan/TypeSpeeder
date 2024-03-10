package se.ju23.typespeeder;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AnvandareRepository extends JpaRepository<Anvandare, Long> {
    boolean existsByAnvandarnamn(String anvandarnamn);
    Optional<Anvandare> findByAnvandarnamn(String anvandarnamn); // Lägg till denna rad
}
