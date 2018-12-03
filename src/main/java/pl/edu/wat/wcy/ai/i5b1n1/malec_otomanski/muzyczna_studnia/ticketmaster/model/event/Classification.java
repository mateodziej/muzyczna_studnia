package pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.ticketmaster.model.event;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.ticketmaster.model.commons.IdName;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Classification {

    private boolean primary;
    private IdName idName;
    private IdName genre;
    private IdName subGenre;
    private boolean family;

}
