package pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.ticketmaster.model.event.sale;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sale {

    private String startDateTime;
    private boolean startTBD;
    private String endDateTime;
}
