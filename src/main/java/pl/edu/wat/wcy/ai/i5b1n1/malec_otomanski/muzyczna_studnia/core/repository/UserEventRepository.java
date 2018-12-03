package pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.model.StoredEvent;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.model.User;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.model.UserEvent;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.model.UserEventId;

import java.util.Optional;

public interface UserEventRepository extends JpaRepository<UserEvent, UserEventId> {

    Optional<UserEvent> findByUserAndStoredEvent(User user, StoredEvent storedEvent);

    Page<UserEvent> findAllByUserAndTypeAndStatusOrderById(User user,
                                                           UserEvent.Type type,
                                                           UserEvent.Status status,
                                                           Pageable pageable);

    void deleteAllByUserAndStatus(User user, UserEvent.Status status);
}
