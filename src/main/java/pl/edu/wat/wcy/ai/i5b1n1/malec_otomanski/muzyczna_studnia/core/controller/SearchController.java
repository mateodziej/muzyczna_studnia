package pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.service.EventSearchService;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.service.impl.EventSearchServiceImpl;

import java.security.Principal;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    private EventSearchService eventSearchService;

    @Autowired
    public SearchController(EventSearchService eventSearchService) {
        this.eventSearchService = eventSearchService;
    }

    @GetMapping
    public void searchEvents(Principal principal) {
        eventSearchService.search(principal.getName());
    }

    @GetMapping("/new")
    public void searchNewEvents(Principal principal) {
        eventSearchService.wipeAndSearch(principal.getName());
    }


}
