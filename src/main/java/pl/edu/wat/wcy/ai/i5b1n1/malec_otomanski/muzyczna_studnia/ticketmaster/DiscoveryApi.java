package pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.ticketmaster;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.ticketmaster.api.Version;
import com.ticketmaster.api.discovery.DiscoveryApiConfiguration;
import com.ticketmaster.api.discovery.operation.ByIdOperation;
import com.ticketmaster.api.discovery.operation.SearchEventsOperation;
import com.ticketmaster.discovery.model.Attraction;
import com.ticketmaster.discovery.model.Event;
import com.ticketmaster.discovery.model.Venue;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.ticketmaster.model.commons.Link;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.ticketmaster.model.commons.Page;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.ticketmaster.model.commons.TypedPage;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.ticketmaster.model.event.Events;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

public class DiscoveryApi {

    private final String apiKey;
    private final Gson gson;
    private final OkHttpClient client;
    private final DiscoveryApiConfiguration configuration;
    private final HashMap<Class<?>, String> pathByType;
    private final String apiKeyQueryParam;

    public DiscoveryApi(String apiKey) {
        this(apiKey, DiscoveryApiConfiguration.builder().build());
    }

    public DiscoveryApi(String apiKey, DiscoveryApiConfiguration configuration) {
        this.apiKey = apiKey;
        this.configuration = configuration;
        this.gson = new Gson();
        this.client = new OkHttpClient();
        this.pathByType = new HashMap();
        this.pathByType.put(Event.class, "events");
        this.pathByType.put(Attraction.class, "attractions");
        this.pathByType.put(Venue.class, "venues");
        this.apiKeyQueryParam = configuration.getApiKeyQueryParam();
    }

    public Optional<TypedPage<Events>> searchEvents(SearchEventsOperation operation) throws IOException {
        HttpUrl.Builder builder = this.urlBuilder((String) this.pathByType.get(Event.class));
        Iterator var3 = operation.getQueryParameters().entrySet().iterator();

        while (var3.hasNext()) {
            Map.Entry<String, String> e = (Map.Entry) var3.next();
            builder.addQueryParameter((String) e.getKey(), (String) e.getValue());
        }

        Request request = this.getRequest(builder.build());
        Type complexType = new TypeToken<Page<Events>>() {
        }.getType();
        okhttp3.Response response = this.client.newCall(request).execute();
        String responseJson = response.body().string();
        System.out.println("SEARCH EVENT " + responseJson);
        return Optional.ofNullable(new TypedPage<>(gson.fromJson(responseJson, complexType), complexType));
    }


    private Request getRequest(HttpUrl url) {
        HttpUrl.Builder urlBuilder = url.newBuilder().addQueryParameter(this.apiKeyQueryParam, this.apiKey);
        if (this.configuration.getDefaultLocale() != null && url.queryParameter("locale") == null) {
            urlBuilder.addQueryParameter("locale", this.configuration.getDefaultLocale());
        }

        return (new Request.Builder()).url(urlBuilder.build()).addHeader("User-Agent", Version.getUserAgent()).build();
    }

    HttpUrl.Builder urlBuilder(String path) {
        HttpUrl.Builder builder = this.baseUrlBuilder().addPathSegment(this.configuration.getApiPackage()).addPathSegment(this.configuration.getApiVersion()).addPathSegment(path);
        if (this.configuration.getPathModifier() != DiscoveryApiConfiguration.PathModifier.NONE) {
            builder.addPathSegment(this.configuration.getPathModifier().getModifier());
        }

        return builder;
    }

    private HttpUrl.Builder baseUrlBuilder() {
        HttpUrl.Builder builder = new HttpUrl.Builder();
        builder.scheme(this.configuration.getProtocol());
        builder.host(this.configuration.getDomainName());
        if (this.configuration.isPortSet()) {
            builder.port(this.configuration.getPort());
        }

        return builder;
    }

    public <T> Optional<TypedPage<T>> nextPage(TypedPage typedPage) throws IOException {
        if (typedPage == null ) return Optional.empty();
        return navigateTo(typedPage.getNextPageUrl(), typedPage.getType());
    }

    public <T> Optional<TypedPage<T>> prevPage(TypedPage typedPage) throws IOException {
        if (typedPage == null ) return Optional.empty();
        return navigateTo(typedPage.getPrevPageUrl(), typedPage.getType());
    }

    private <T> Optional<TypedPage<T>> navigateTo(Link nextPageUrl, Type type) throws IOException {
        if (nextPageUrl == null) return Optional.empty();
        HttpUrl baseUrl = baseUrlBuilder().build();
        HttpUrl nextUrl = baseUrl.resolve(nextPageUrl.getHref());
        Request request = getRequest(nextUrl);

        okhttp3.Response nextResponse = client.newCall(request).execute();
        String responseJson = nextResponse.body().string();

        return Optional.ofNullable(new TypedPage<>(gson.fromJson(responseJson, type), type));
    }

    public Optional<pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.ticketmaster.model.event.Event> getEvent(ByIdOperation operation) throws IOException {
        return getById(operation, Event.class, pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.ticketmaster.model.event.Event.class);
    }

    private <T, R> Optional<R> getById(ByIdOperation operation, Class<T> clazz, Class<R> retClazz) throws IOException {
        HttpUrl.Builder builder = urlBuilder(pathByType.get(clazz));

        builder.addPathSegment(operation.getId());
        for (Map.Entry<String, String> e : operation.getQueryParameters().entrySet()) {
            builder.addQueryParameter(e.getKey(), e.getValue());
        }

        Request request = getRequest(builder.build());
        okhttp3.Response response = client.newCall(request).execute();
        String responseJson = response.body().string();
        System.out.println("EVENT JSON " + responseJson);
        return Optional.ofNullable(gson.fromJson(responseJson, retClazz));
    }
}
