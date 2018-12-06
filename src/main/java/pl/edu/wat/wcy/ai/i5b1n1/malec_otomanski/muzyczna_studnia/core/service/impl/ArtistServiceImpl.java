package pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.service.impl;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.model.Artist;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.model.User;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.repository.ArtistRepository;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.repository.UserRepository;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.service.ArtistService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Service
public class ArtistServiceImpl implements ArtistService {

    final static Logger logger = Logger.getLogger(ArtistServiceImpl.class);

    private UserRepository userRepository;
    private ArtistRepository artistRepository;

    @Autowired
    public ArtistServiceImpl(UserRepository userRepository, ArtistRepository artistRepository) {
        this.userRepository = userRepository;
        this.artistRepository = artistRepository;
    }

    @Transactional
    public Set<Artist> getArtistsByUser(final String username){
        logger.debug("username : " + username);
        Set<Artist> artists = new HashSet<>();
        Optional<User> dbUser = userRepository.findByUsername(username);
        if (dbUser.isPresent()){
            logger.debug("user is present");
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
            logger.debug("user is present");
            User user = dbUser.get();
            user.getArtists().add(dbArtist.get());
            userRepository.save(user);
            logger.debug("addArtistToUser -> username " + username + " artist " + artistName);
        }
    }

    @Transactional
    public void removeArtistFromUser(final String artistName, final String username){
        Optional<Artist> dbArtist = artistRepository.findByName(artistName);
        if (!dbArtist.isPresent()) return;

        Optional<User> dbUser = userRepository.findByUsername(username);
        if(dbUser.isPresent()){
            logger.debug("user is present");
            User user = dbUser.get();
            user.getArtists().remove(dbArtist.get());
            userRepository.save(user);
            logger.debug("removeArtistFromUser -> username " + username + " artist " + artistName);
        }
    }
}
