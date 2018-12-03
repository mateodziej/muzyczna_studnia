package pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.ticketmaster.model.event;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
public class Events {

    @Setter
    private List<Event> events;

    public List<Event> getEvents(){
        return Optional.ofNullable(events).orElse(new ArrayList<>());
    }
}
