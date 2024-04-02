package se.ju23.typespeeder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface SpeldataRepository extends JpaRepository<Speldata, Long> {

    @Query("SELECT s FROM Speldata s WHERE s.isCorrect = true ORDER BY s.tid ASC")
    List<Speldata> findByIsCorrectTrueOrderByTidAsc();


    @Query("SELECT s FROM Speldata s ORDER BY s.rattaSvar DESC")
    List<Speldata> findByOrderByRattaSvarDesc();

    @Query("SELECT s FROM Speldata s WHERE s.isCorrect = true ORDER BY s.svarIOrdning DESC, s.tid ASC")
    List<Speldata> findSequentialCorrectAnswersRanking();
}
