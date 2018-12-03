package pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserEvent {

    public enum Type{
        MUSIC("KZFzniwnSyZfZ7v7nJ"),
        OTHER;

        private final String ticketmasterSegmentId;

        Type(){
            this("");
        }
        Type(String ticketmasterSegmentId){
            this.ticketmasterSegmentId = ticketmasterSegmentId;
        }

        public String id(){
            return ticketmasterSegmentId;
        }
    }

    public enum Status{
        WALL,
        LIKED,
        DISLIKED
    }

    @EmbeddedId
    private UserEventId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("userId")
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("storedEventId")
    private StoredEvent storedEvent;

    @NotNull
    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private Type type;

    @NotNull
    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private Status status;

    private String keyword;

    public UserEvent(User user, StoredEvent storedEvent, Type type, Status status, String keyword) {
        this.id = new UserEventId(user.getId(), storedEvent.getId());
        this.user = user;
        this.storedEvent = storedEvent;
        this.status = status;
        this.type = type;
        this.keyword = keyword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEvent)) return false;

        UserEvent userEvent = (UserEvent) o;

        if (id != null ? !id.equals(userEvent.id) : userEvent.id != null) return false;
        if (user != null ? !user.equals(userEvent.user) : userEvent.user != null) return false;
        return storedEvent != null ? storedEvent.equals(userEvent.storedEvent) : userEvent.storedEvent == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (storedEvent != null ? storedEvent.hashCode() : 0);
        return result;
    }
}
