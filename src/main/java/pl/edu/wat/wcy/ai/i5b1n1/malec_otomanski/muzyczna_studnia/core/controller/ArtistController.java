package pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.model.Artist;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.service.ArtistService;

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
    public void addArtistToUser(Principal principal, @PathVariable final String artist) {
        System.out.println("addArtistToUser controller principal " + principal.getName());
        artistService.addArtistToUser(artist, principal.getName());
    }

    @DeleteMapping("/{artist}")
    public void removeArtistFromUser(Principal principal, @PathVariable final String artist) {
        artistService.removeArtistFromUser(artist, principal.getName());
    }

}
