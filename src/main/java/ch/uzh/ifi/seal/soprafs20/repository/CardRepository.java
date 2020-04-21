package ch.uzh.ifi.seal.soprafs20.repository;

import ch.uzh.ifi.seal.soprafs20.entity.Game.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("cardRepository")
public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<Card> findById(Long id);
}
