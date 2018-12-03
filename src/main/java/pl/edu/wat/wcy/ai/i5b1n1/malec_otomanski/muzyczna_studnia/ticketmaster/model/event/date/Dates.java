package pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.ticketmaster.model.event.date;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.ticketmaster.model.event.Status;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dates {

    private Date start;
    private String timezone;
    private Status status;
    private boolean spanMultipleDays;
}
