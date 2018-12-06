package pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.model.UserEvent;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.model.UserEventProjection;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.service.EventManagementService;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.service.impl.EventManagementServiceImpl;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.util.EventStatusConventer;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.util.EventTypeConventer;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventManagementController {

    private EventManagementService eventManagementService;

    @Autowired
    public EventManagementController(EventManagementService eventManagementService) {
        this.eventManagementService = eventManagementService;
    }

    @GetMapping("/{type}/{status}")
    public Page<UserEvent> getEvents(Principal principal,
                                    @PathVariable UserEvent.Type type,
                                    @PathVariable UserEvent.Status status,
                                    Pageable pageRequest) {
        return eventManagementService.getEventsByUser(principal.getName(), type, status, pageRequest);
    }

    @GetMapping("/popular/{type}/{status}")
    public List<UserEventProjection> getPopularEvents(@PathVariable UserEvent.Type type,
                                                      @PathVariable UserEvent.Status status) {
        return eventManagementService.getPopularEvents(type, status);
    }

    @PutMapping("/{status}/{ticketmasterId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateEventStatus(Principal principal,
                                  @PathVariable UserEvent.Status status,
                                  @PathVariable String ticketmasterId) {
        eventManagementService.updateUsersEventStatus(principal.getName(), ticketmasterId, status);
    }

    @InitBinder
    public void initBinder(final WebDataBinder webdataBinder) {
        webdataBinder.registerCustomEditor(UserEvent.Type.class, new EventTypeConventer());
        webdataBinder.registerCustomEditor(UserEvent.Status.class, new EventStatusConventer());
    }
}
