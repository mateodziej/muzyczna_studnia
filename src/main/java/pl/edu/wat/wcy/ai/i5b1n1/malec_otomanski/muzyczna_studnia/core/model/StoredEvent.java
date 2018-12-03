package pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoredEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String ticketmasterId;

    private String name;

    private String localDate;

    private String localTime;

    private boolean dateTBA;

    private boolean timeTBA;

    private boolean dateTBD;

    private String statusCode;

    private String venueName;

    private String venueCity;

    private String venueAddress;

    private String currency;

    private float priceMin;

    private float priceMax;

    private String promoterName;

    private String imageUrl;

    private String saleStartDate;

    private String saleEndDate;

    private String genreName;

    private String subGenreName;
}
