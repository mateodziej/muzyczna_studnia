package pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.model.Tag;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.service.TagService;

import java.security.Principal;
import java.util.Set;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    private TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public Set<Tag> getTagsByUser(Principal principal) {
        return tagService.getTagsByUser(principal.getName());
    }

    @PostMapping("/{tag}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addTagToUser(Principal principal, @PathVariable final String tag) {
        tagService.addTagToUser(tag, principal.getName());
    }

    @DeleteMapping("/{tag}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeTagFromUser(Principal principal, @PathVariable final String tag) {
        tagService.removeTagFromUser(tag, principal.getName());
    }

}
