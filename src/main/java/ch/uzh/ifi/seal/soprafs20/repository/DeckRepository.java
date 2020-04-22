package ch.uzh.ifi.seal.soprafs20.repository;

import ch.uzh.ifi.seal.soprafs20.entity.Game.Deck;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("deckRepository")
public interface DeckRepository extends JpaRepository<Deck, Long> {
    @EntityGraph(attributePaths = {"game"})
    Optional<Deck> findById(Long id);
}
