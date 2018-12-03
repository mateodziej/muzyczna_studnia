package pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.ticketmaster.model.event;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.ticketmaster.model.commons.IdName;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.ticketmaster.model.commons.Image;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.ticketmaster.model.event.date.Dates;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.ticketmaster.model.event.price.Price;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.ticketmaster.model.event.sale.Sales;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    private String name;
    private String type;
    private String id;
    private boolean test;
    private String url;
    private String locale;
    private List<Image> images;
    private Sales sales;
    private Dates dates;
    private List<Classification> classifications;
    private IdName promoter;
    private List<IdName> promoters;
    private List<Price> priceRanges;
    private SeatMap seatmap;

    @SerializedName("_embedded")
    private Embedded embedded;

    @JsonIgnore
    public @NotNull String getStartDateTime() {
        return dates.getStart() != null ? dates.getStart().getDateTime() : null;
    }
}
