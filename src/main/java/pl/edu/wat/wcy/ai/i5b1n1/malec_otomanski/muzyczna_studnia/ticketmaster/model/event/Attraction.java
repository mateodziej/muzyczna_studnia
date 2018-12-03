package pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.ticketmaster.model.event;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.ticketmaster.model.commons.Image;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.ticketmaster.model.commons.Links;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attraction {

    private String name;
    private String type;
    private String id;
    private boolean test;
    private String url;
    private String locale;
    List<Image> images;
    private List<Classification> classifications;

    @SerializedName("_links")
    Links links;
}
