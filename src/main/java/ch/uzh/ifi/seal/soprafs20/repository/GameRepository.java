package ch.uzh.ifi.seal.soprafs20.repository;


import ch.uzh.ifi.seal.soprafs20.entity.Game;
import com.sun.xml.bind.v2.model.core.ID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("GameRepository")
public interface GameRepository extends JpaRepository<Game, Long> {
}
