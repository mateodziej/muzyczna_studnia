package pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.model.StoredEvent;

import java.util.Optional;

public interface EventRepository extends JpaRepository<StoredEvent, Long> {

    Optional<StoredEvent> findByTicketmasterId(String ticketmasterId);
}
