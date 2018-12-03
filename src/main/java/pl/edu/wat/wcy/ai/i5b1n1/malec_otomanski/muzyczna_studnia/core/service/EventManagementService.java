package pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.model.StoredEvent;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.model.User;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.model.UserEvent;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.repository.EventRepository;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.repository.UserEventRepository;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.repository.UserRepository;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class EventManagementService {

    private UserEventRepository userEventRepository;
    private EventRepository eventRepository;
    private UserRepository userRepository;

    @Autowired
    public EventManagementService(UserEventRepository userEventRepository,
                                  EventRepository eventRepository,
                                  UserRepository userRepository) {
        this.userEventRepository = userEventRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void addEvent(StoredEvent event) {
        if (eventRepository.findByTicketmasterId(event.getTicketmasterId())
                .isPresent()) return;
        eventRepository.save(event);
    }

    @Transactional
    public void updateUsersEventStatus(String username,
                                       String ticketmasterId,
                                       UserEvent.Status status) {
        userRepository.findByUsername(username)
                .ifPresent(user -> {
                    eventRepository.findByTicketmasterId(ticketmasterId)
                            .ifPresent(storedEvent -> {
                                userEventRepository.findByUserAndStoredEvent(user, storedEvent)
                                        .ifPresent(userEvent -> {
                                            userEvent.setStatus(status);
                                            userEventRepository.save(userEvent);
                                        });
                            });
                });

    }

    @Transactional
    public Page<UserEvent> getEventsByUser(String username,
                                           UserEvent.Type type,
                                           UserEvent.Status status,
                                           Pageable pageable) {
        Page<UserEvent> eventsPage = new PageImpl<>(new ArrayList<>(), pageable, 0);
        Optional<User> dbUser = userRepository.findByUsername(username);
        if (!dbUser.isPresent()) return eventsPage;
        return userEventRepository.findAllByUserAndTypeAndStatusOrderById(
                dbUser.get(),
                type,
                status,
                pageable);
    }
}
