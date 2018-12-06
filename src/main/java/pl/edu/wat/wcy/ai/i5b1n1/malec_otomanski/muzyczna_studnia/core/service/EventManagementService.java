package pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.model.StoredEvent;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.model.UserEvent;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.model.UserEventProjection;

import java.util.List;

public interface EventManagementService {

    void addEvent(final StoredEvent event);

    void updateUsersEventStatus(final String username,
                                final String ticketmasterId,
                                final UserEvent.Status status);

    Page<UserEvent> getEventsByUser(final String username,
                                    final UserEvent.Type type,
                                    final UserEvent.Status status,
                                    Pageable pageable);

    List<UserEventProjection> getPopularEvents(final UserEvent.Type type, final UserEvent.Status status);
}
