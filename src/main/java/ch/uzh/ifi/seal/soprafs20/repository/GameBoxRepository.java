package ch.uzh.ifi.seal.soprafs20.repository;

import ch.uzh.ifi.seal.soprafs20.entity.Game.GameBox;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("gameBoxRepository")
public interface GameBoxRepository extends JpaRepository<GameBox, Long> {
    @EntityGraph(attributePaths = {"game"})
    Optional<GameBox> findById(Long id);
}
