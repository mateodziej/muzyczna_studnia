package pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.lastfm.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArtistChartResponse {

    @SerializedName("weeklyartistchart")
    private ArtistChart weeklyArtistChart;

    public List<Artist> getArtists(){
        if(weeklyArtistChart != null){
            if(weeklyArtistChart.getArtists() != null) return weeklyArtistChart.getArtists();
        }

        return new ArrayList<>();
    }
}
