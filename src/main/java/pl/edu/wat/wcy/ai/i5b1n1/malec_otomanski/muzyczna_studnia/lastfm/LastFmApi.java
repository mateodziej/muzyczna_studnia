package pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.lastfm;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.lastfm.model.Artist;
import pl.edu.wat.wcy.ai.i5b1n1.malec_otomanski.muzyczna_studnia.lastfm.model.ArtistChartResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LastFmApi {

    private final String url;
    private final Gson gson;
    private final OkHttpClient client;

    private LastFmApi(String url, Gson gson, OkHttpClient client) {
        this.url = url;
        this.gson = gson;
        this.client = client;
    }

    public List<Artist> getWeeklyArtistsByUser(String lastFmUsername) throws IOException {
        Request request = buildRequest(lastFmUsername);
        Response response = client.newCall(request).execute();
        String responseJson = response.body().string();
        ArtistChartResponse artistChartResponse = gson.fromJson(responseJson, ArtistChartResponse.class);
        if(artistChartResponse == null) return new ArrayList<>();
        return artistChartResponse.getArtists();
    }

    private Request buildRequest(String username) {
        String urlWithUsername = String.format(url, username);
        return new Request.Builder()
                .url(urlWithUsername)
                .build();
    }


    // - API BUILDER
    public static LastFmApi.LastFmApiConfigurationBuilder builder() {
        return new LastFmApi.LastFmApiConfigurationBuilder();
    }

    public static class LastFmApiConfigurationBuilder {
        private static final String DEFAULT_ADDRESS = "http://ws.audioscrobbler.com/2.0/";
        private static final String DEFAULT_METHOD = "user.getweeklyartistchart";
        private static final String DEFAULT_FORMAT = "json";

        private String address;
        private String method;
        private String format;
        private String apiKey;
        private Gson gson;
        private OkHttpClient client;

        public LastFmApiConfigurationBuilder() {
            this.address = DEFAULT_ADDRESS;
            this.method = DEFAULT_METHOD;
            this.format = DEFAULT_FORMAT;
            this.gson = new Gson();
            this.client = new OkHttpClient();
            this.apiKey = "";
        }

        public LastFmApi.LastFmApiConfigurationBuilder address(final String address) {
            this.address = address;
            return this;
        }

        public LastFmApi.LastFmApiConfigurationBuilder method(final String method) {
            this.method = method;
            return this;
        }

        public LastFmApi.LastFmApiConfigurationBuilder format(final String format) {
            this.format = format;
            return this;
        }

        public LastFmApi.LastFmApiConfigurationBuilder apiKey(final String apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        public LastFmApi.LastFmApiConfigurationBuilder gson(final Gson gson) {
            this.gson = gson;
            return this;
        }

        public LastFmApi.LastFmApiConfigurationBuilder client(final OkHttpClient client) {
            this.client = client;
            return this;
        }

        public LastFmApi build() {
            StringBuilder sb = new StringBuilder();
            sb.append(address)
                    .append("?method=")
                    .append(method)
                    .append("&user=%s")
                    .append("&api_key=")
                    .append(apiKey)
                    .append("&format=")
                    .append(format);

            return new LastFmApi(
                    sb.toString(),
                    gson,
                    client
            );
        }
    }
}
