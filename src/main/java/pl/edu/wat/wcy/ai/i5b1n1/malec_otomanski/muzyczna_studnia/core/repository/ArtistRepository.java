package pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.model.Artist;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.model.User;

import java.util.Optional;
import java.util.Set;

public interface ArtistRepository extends JpaRepository<Artist, Long> {

    Set<Artist> findAllByUsers(User user);
    Optional<Artist> findByName(String name);
}
