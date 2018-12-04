package pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @NotEmpty
    @Column(unique = true)
    private String username;

    @Builder.Default
    @NotNull
    private String lastFmUsername = "";

    private String email;

    @Builder.Default
    @NotNull
    private String city = "Warszawa";

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH
    },
            fetch=FetchType.EAGER,
            targetEntity = Artist.class)
    @JoinTable(
            name = "user_artist",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "artist_id")}
    )
    @JsonIgnore
    private Set<Artist> artists = new HashSet<>();

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH
    },
            fetch=FetchType.EAGER,
            targetEntity = Tag.class)
    @JoinTable(
            name = "user_tag",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")}
    )
    @JsonIgnore
    private Set<Tag> tags = new HashSet<>();
}
