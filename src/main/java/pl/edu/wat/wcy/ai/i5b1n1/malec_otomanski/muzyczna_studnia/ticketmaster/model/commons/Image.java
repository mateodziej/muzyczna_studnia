package pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.ticketmaster.model.commons;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Image {

    private String ratio;
    private String url;
    private int width;
    private int height;
    private boolean fallback;
}
