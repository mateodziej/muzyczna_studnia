package pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.model.Artist;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.service.ArtistService;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.service.impl.ArtistServiceImpl;

import java.security.Principal;
import java.util.Set;

@RestController
@RequestMapping("/api/artists")
public class ArtistController {

    private ArtistService artistService;

    @Autowired
    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping
    public Set<Artist> getArtistsByUser(Principal principal) {
        return artistService.getArtistsByUser(principal.getName());
    }

    @PostMapping("/{artist}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addArtistToUser(Principal principal, @PathVariable final String artist) {
        artistService.addArtistToUser(artist, principal.getName());
    }

    @DeleteMapping("/{artist}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeArtistFromUser(Principal principal, @PathVariable final String artist) {
        artistService.removeArtistFromUser(artist, principal.getName());
    }

}
