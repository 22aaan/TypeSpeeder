package se.ju23.typespeeder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import se.ju23.typespeeder.Anvandare;


@Repository
public interface AnvandareRepository extends JpaRepository<Anvandare, Long> {
    Optional<Anvandare> findByAnvandarnamnAndLosenord(String anvandarnamn, String losenord);
    boolean existsByAnvandarnamnAndAnvandarIDNot(String anvandarnamn, Long anvandarID);
    boolean existsByAnvandarnamn(String anvandarnamn);
    Optional<Anvandare> findById(Long id);
    Optional<Anvandare> findByAnvandarnamn(String anvandarnamn);

}

