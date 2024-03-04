package perso.discordbots.caupanharm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import perso.discordbots.caupanharm.models.Apis;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RequestBuilder {
    private static final Logger logger = LoggerFactory.getLogger(RequestBuilder.class);

    public static HttpResponse<String> get(Apis api, String uri, String api_key) {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .method("GET", HttpRequest.BodyPublishers.noBody());


        switch (api) {
            case RIOT -> builder.header("Accept-Charset", "application/x-www-form-urlencoded; charset=UTF-8")
                                .header("X-Riot-Token", api_key);
            case HENRIKDEV -> builder.header("Accept-Charset", "application/json; charset=UTF-8");
        }


        HttpRequest request = builder.build();

        logger.info(String.valueOf(request.uri()));
        logger.info(String.valueOf(request.headers()));

        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

}