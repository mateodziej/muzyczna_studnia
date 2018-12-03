package pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.model.Artist;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.model.User;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.repository.ArtistRepository;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.repository.UserRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Service
public class ArtistService {
    private UserRepository userRepository;
    private ArtistRepository artistRepository;

    @Autowired
    public ArtistService(UserRepository userRepository, ArtistRepository artistRepository) {
        this.userRepository = userRepository;
        this.artistRepository = artistRepository;
    }

    @Transactional
    public Set<Artist> getArtistsByUser(final String username){
        Set<Artist> artists = new HashSet<>();
        Optional<User> dbUser = userRepository.findByUsername(username);
        if (dbUser.isPresent()){
            artists = artistRepository.findAllByUsers(dbUser.get());
        }
        return artists;
    }

    @Transactional
    public void addArtistToUser(final String artistName, final String username){
        Optional<Artist> dbArtist = artistRepository.findByName(artistName);
        if (!dbArtist.isPresent()) dbArtist = Optional.of(Artist.builder()
                .name(artistName)
                .build());

        Optional<User> dbUser = userRepository.findByUsername(username);
        if(dbUser.isPresent()){
            User user = dbUser.get();
            user.getArtists().add(dbArtist.get());
            userRepository.save(user);
            System.out.println("addArtistToUser -> username " + username + " artist " + artistName);
        }
    }

    @Transactional
    public void removeArtistFromUser(@PathVariable final String artistName, @PathVariable final String username){
        Optional<Artist> dbArtist = artistRepository.findByName(artistName);
        if (!dbArtist.isPresent()) return;

        Optional<User> dbUser = userRepository.findByUsername(username);
        if(dbUser.isPresent()){
            User user = dbUser.get();
            user.getArtists().remove(dbArtist.get());
            userRepository.save(user);
        }
    }
}
