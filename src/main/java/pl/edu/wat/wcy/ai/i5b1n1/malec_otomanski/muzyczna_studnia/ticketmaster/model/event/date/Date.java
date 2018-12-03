package pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.ticketmaster.model.event.date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Date {

    String localDate;
    String localTime;
    String dateTime;
    boolean dateTBD;
    boolean dateTBA;
    boolean timeTBA;
    boolean noSpecificTime;
}
