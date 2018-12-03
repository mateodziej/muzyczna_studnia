package pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEventId implements Serializable{

    @Column(name = "user_id")
    private long userId;

    @Column(name = "stored_event_id")
    private long storedEventId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEventId)) return false;

        UserEventId that = (UserEventId) o;

        if (userId != that.userId) return false;
        return storedEventId == that.storedEventId;
    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + (int) (storedEventId ^ (storedEventId >>> 32));
        return result;
    }
}
