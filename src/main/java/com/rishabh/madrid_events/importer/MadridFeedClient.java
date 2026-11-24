package com.rishabh.madrid_events.importer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import tools.jackson.core.JacksonException;
import tools.jackson.core.json.JsonReadFeature;
import tools.jackson.databind.json.JsonMapper;

import java.time.Duration;
import java.util.List;

@Component
public class MadridFeedClient {

    private static final Logger log = LoggerFactory.getLogger(MadridFeedClient.class);

    private final RestClient restClient;
    private final JsonMapper jsonMapper;
    private final String feedUrl;

    public MadridFeedClient(@Value("${madrid.feed.url}") String feedUrl) {
        this.feedUrl = feedUrl;

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofSeconds(10));
        factory.setReadTimeout(Duration.ofSeconds(60));
        this.restClient = RestClient.builder()
                .requestFactory(factory)
                .build();

        this.jsonMapper = JsonMapper.builder()
                .enable(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS)
                .build();
    }

    public List<MadridEvent> fetchEvents() {
        long start = System.currentTimeMillis();
        try {
            byte[] body = restClient.get()
                    .uri(feedUrl)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(byte[].class);

            if (body == null) {
                throw new MadridFeedException("Madrid feed returned an empty response", null);
            }

            MadridFeedResponse response = jsonMapper.readValue(body, MadridFeedResponse.class);
            List<MadridEvent> events = (response.events() == null) ? List.of() : response.events();

            log.info("Fetched {} events from Madrid feed in {} ms",
                    events.size(), System.currentTimeMillis() - start);
            return events;

        } catch (RestClientException | JacksonException e) {
            throw new MadridFeedException("Could not read the Madrid events feed", e);
        }
    }
}