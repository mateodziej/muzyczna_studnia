package pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.service;

import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.model.Artist;

import java.util.Set;

public interface ArtistService {

    Set<Artist> getArtistsByUser(final String username);

    void addArtistToUser(final String artistName, final String username);

    void removeArtistFromUser(final String artistName, final String username);
}
