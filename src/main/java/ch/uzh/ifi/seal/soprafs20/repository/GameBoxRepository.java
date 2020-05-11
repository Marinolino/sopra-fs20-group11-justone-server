package ch.uzh.ifi.seal.soprafs20.repository;

import ch.uzh.ifi.seal.soprafs20.entity.game.GameBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("gameBoxRepository")
public interface GameBoxRepository extends JpaRepository<GameBox, Long> {
    Optional<GameBox> findById(Long id);
}
