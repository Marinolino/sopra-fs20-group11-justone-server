package ch.uzh.ifi.seal.soprafs20.repository;

import ch.uzh.ifi.seal.soprafs20.entity.Game.Clue;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("clueRepository")
public interface ClueRepository extends JpaRepository<Clue, Long> {
    @EntityGraph(attributePaths = {"game"})
    Optional<Clue> findById(Long id);
}
