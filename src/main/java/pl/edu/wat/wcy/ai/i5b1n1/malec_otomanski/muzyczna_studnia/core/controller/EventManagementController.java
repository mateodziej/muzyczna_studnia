package pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.model.UserEvent;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.service.EventManagementService;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.util.EventStatusConventer;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.util.EventTypeConventer;

import java.security.Principal;

@RestController
@RequestMapping("/api/events")
public class EventManagementController {

    private EventManagementService eventManagementService;

    @Autowired
    public EventManagementController(EventManagementService eventManagementService) {
        this.eventManagementService = eventManagementService;
    }

    @GetMapping("/{type}/{status}")
    @Transactional
    public Page<UserEvent> getEvents(Principal principal,
                                    @PathVariable UserEvent.Type type,
                                    @PathVariable UserEvent.Status status,
                                    Pageable pageRequest) {
        return eventManagementService.getEventsByUser(principal.getName(), type, status, pageRequest);
    }

    @PutMapping("/{status}/{ticketmasterId}")
    @Transactional
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
