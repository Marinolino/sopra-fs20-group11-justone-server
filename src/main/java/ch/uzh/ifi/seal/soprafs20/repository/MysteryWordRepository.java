package ch.uzh.ifi.seal.soprafs20.repository;

import ch.uzh.ifi.seal.soprafs20.entity.Game.MysteryWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository("mysteryWordRepository")
public interface MysteryWordRepository extends JpaRepository<MysteryWord, Long> {
    Optional<MysteryWord> findById(Long id);
}
