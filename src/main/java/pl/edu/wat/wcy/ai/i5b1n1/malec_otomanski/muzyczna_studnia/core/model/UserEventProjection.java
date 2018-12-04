package pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEventProjection {

    private StoredEvent storedEvent;
    private long nr;

    public UserEventProjection(StoredEvent storedEvent, long nr) {
        this.storedEvent = storedEvent;
        this.nr = nr;
    }
}
