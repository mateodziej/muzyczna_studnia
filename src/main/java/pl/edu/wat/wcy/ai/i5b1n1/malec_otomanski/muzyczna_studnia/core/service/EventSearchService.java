package pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.service;

import com.ticketmaster.api.discovery.operation.SearchEventsOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.model.StoredEvent;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.model.User;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.model.UserEvent;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.repository.*;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.core.util.EventMapper;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.lastfm.LastFmApi;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.ticketmaster.DiscoveryApi;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.ticketmaster.model.commons.TypedPage;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.ticketmaster.model.event.Event;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.ticketmaster.model.event.Events;


import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EventSearchService {

    private LastFmApi lastFmApi;
    private DiscoveryApi discoveryApi;
    private ArtistRepository artistRepository;
    private UserRepository userRepository;
    private TagRepository tagRepository;
    private EventRepository eventRepository;
    private UserEventRepository userEventRepository;

    @Autowired
    public EventSearchService(LastFmApi lastFmApi,
                              DiscoveryApi discoveryApi,
                              ArtistRepository artistRepository,
                              UserRepository userRepository,
                              TagRepository tagRepository,
                              EventRepository eventRepository,
                              UserEventRepository userEventRepository) {
        this.lastFmApi = lastFmApi;
        this.discoveryApi = discoveryApi;
        this.artistRepository = artistRepository;
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
        this.eventRepository = eventRepository;
        this.userEventRepository = userEventRepository;
    }

    @Transactional
    @Async
    public void search(final String username){
        searchMusic(username);
        searchOther(username);
    }

    @Transactional
    @Async
    public void wipeAndSearch(final String username){
        wipeWall(username);
        searchMusic(username);
        searchOther(username);
    }

    private void wipeWall(String username) {
        userRepository.findByUsername(username).ifPresent(user ->
                userEventRepository.deleteAllByUserAndStatus(user, UserEvent.Status.WALL));

    }

    private void searchMusic(final String username){
        userRepository.findByUsername(username).ifPresent(user -> {
            Set<String> artistNames = artistRepository.findAllByUsers(user)
                    .stream()
                    .map(artist -> artist.getName())
                    .collect(Collectors.toSet());
            List<pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.lastfm.model.Artist> lastFmArtists = new ArrayList<>();
            if(!user.getLastFmUsername().isEmpty()){
                try {
                    lastFmArtists = lastFmApi.getWeeklyArtistsByUser(user.getLastFmUsername());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            artistNames.addAll(lastFmArtists.stream()
                    .limit(10L)
                    .map(artist -> artist.getName())
                    .collect(Collectors.toSet()));
            searchEvents(user, artistNames, UserEvent.Type.MUSIC);
        });
    }

    private void searchOther(String username) {
        userRepository.findByUsername(username).ifPresent(user -> {
            Set<String> tags = tagRepository.findAllByUsers(user)
                    .stream()
                    .map(tag -> tag.getName())
                    .collect(Collectors.toSet());
            searchEvents(user, tags, UserEvent.Type.OTHER);
        });
    }

    private void searchEvents(User user, Set<String> keywords, UserEvent.Type type) {
        keywords.iterator().forEachRemaining(keyword ->{
            Optional<TypedPage<Events>> foundEvents = makeSearch(keyword, user.getCity(), type);
            saveEvents(foundEvents, user, keyword, type);
        });
    }

    private Optional<TypedPage<Events>> makeSearch(String keyword, @NotNull String city, UserEvent.Type type) {
        SearchEventsOperation op = new SearchEventsOperation()
                .locale("pl")
                .keyword(keyword)
                .city(city);
        if(type == UserEvent.Type.MUSIC)  op.withParam("segmentId", UserEvent.Type.MUSIC.id());
        try {
            Optional<TypedPage<Events>> page = discoveryApi.searchEvents(op);
            Thread.sleep(200);
            return page;
        } catch (IOException e) {
            return Optional.empty();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private void saveEvents(Optional<TypedPage<Events>> foundEvents, User user, String keyword, UserEvent.Type type) {
        foundEvents.ifPresent(eventsTypedPage -> {
            Optional.ofNullable(eventsTypedPage.getPageEmbedded())
                    .ifPresent(emb ->
                            emb.getEvents()
                            .forEach(event -> {
                                StoredEvent se = saveIfNotExists(event);
                                addEventToUser(se, user, type, keyword);
                            })
                    );
        });
    }

    private void addEventToUser(StoredEvent se, User user, UserEvent.Type type, String keyword) {
        if (userEventRepository.findByUserAndStoredEvent(user, se)
                .isPresent()) return;
        userEventRepository.save(new UserEvent(user, se, type, UserEvent.Status.WALL, keyword));
    }

    private StoredEvent saveIfNotExists(Event event) {
        Optional<StoredEvent> storedEvent = eventRepository.findByTicketmasterId(event.getId());
        if(storedEvent.isPresent()) return storedEvent.get();
        return eventRepository.save(EventMapper.map(event));
    }

}
