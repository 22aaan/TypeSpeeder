package se.ju23.typespeeder;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SpeldataRepository extends JpaRepository<Speldata, Long> {
    List<Speldata> findAllByOrderByTidAsc(Pageable pageable);
    List<Speldata> findAllByOrderByRattaSvarDesc(Pageable pageable);
    List<Speldata> findAllByOrderByFleraRattDesc(Pageable pageable);
    Optional<Speldata> findTopByAnvandareAndIsCorrectOrderBySpelDataIDDesc(Anvandare anvandare, boolean isCorrect);

    @Query("SELECT new se.ju23.typespeeder.Spelresultat(s.anvandare.anvandarnamn, MAX(s.fleraRatt)) " +
            "FROM Speldata s GROUP BY s.anvandare.anvandarnamn ORDER BY MAX(s.fleraRatt) DESC")
    Page<Spelresultat> findHighestFleraRattForEachUser(Pageable pageable);

    @Query("SELECT s FROM Speldata s ORDER BY s.fleraRatt DESC, s.tid ASC")
    List<Speldata> findRankings(Pageable pageable);


}