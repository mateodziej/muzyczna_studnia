package pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.model.*;

import java.util.List;
import java.util.Optional;

public interface UserEventRepository extends JpaRepository<UserEvent, UserEventId> {

    Optional<UserEvent> findByUserAndStoredEvent(User user, StoredEvent storedEvent);

    Page<UserEvent> findAllByUserAndTypeAndStatusOrderById(User user,
                                                           UserEvent.Type type,
                                                           UserEvent.Status status,
                                                           Pageable pageable);

    void deleteAllByUserAndStatus(User user, UserEvent.Status status);

    @Query(value = "SELECT new pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.model.UserEventProjection(u.storedEvent, count(1) AS nr) FROM UserEvent u WHERE u.type = ?1 AND u.status = ?2 GROUP BY u.storedEvent ORDER BY nr DESC")
    List<UserEventProjection> getMostPopularOf(UserEvent.Type type, UserEvent.Status status);

}
