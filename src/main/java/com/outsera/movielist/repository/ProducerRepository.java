package com.outsera.movielist.repository;

import com.outsera.movielist.model.Producer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProducerRepository extends JpaRepository<Producer, Long> {

    Optional<Producer> findByName(String name);

    @Query("SELECT p FROM Producer p WHERE SIZE(p.winnerList) > 1")
    List<Producer> findProducersWithMoreThanOneWinner();

    @Query("SELECT p FROM Producer p JOIN p.winnerList w GROUP BY p.id ORDER BY MAX(w.ageOutWinner) DESC")
    List<Producer> findTop2ByMaxAgeOutWinner(Pageable pageable);

    @Query("SELECT p FROM Producer p JOIN p.winnerList w  WHERE w.ageOutWinner <> 0 GROUP BY p.id ORDER BY MIN(w.ageOutWinner) ASC")
    List<Producer> findTop2ByMinAgeOutWinner(Pageable pageable);
}
