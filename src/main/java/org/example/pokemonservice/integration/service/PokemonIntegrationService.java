package org.example.pokemonservice.integration.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.example.pokemonservice.domain.Pokemon;
import org.example.pokemonservice.service.PokemonFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

@Service
public class PokemonIntegrationService {

    private final RestClient restClient;
    private final Logger logger = LoggerFactory.getLogger(PokemonIntegrationService.class);

    @Value("${integration.pokemons.service.url}")
    private String getPokemonsUrl;

    public PokemonIntegrationService(RestClient restClient) {
        this.restClient = restClient;
    }

    public List<String> getPokemonUrls() {
        var pokemonUrls = new ArrayList<String>();
        try {
            var responseJson = restClient.get().uri(getPokemonsUrl).retrieve().body(JsonNode.class);
            pokemonUrls.addAll(extractAllPokemonUrlFromResponse(responseJson));

            while (responseJson != null && responseJson.has("next") && !responseJson.get("next").isNull()) {
                var nextUrl = responseJson.get("next").asText();
                if (nextUrl.startsWith(getPokemonsUrl)) {
                    responseJson = restClient.get().uri(nextUrl).retrieve().body(JsonNode.class);
                    pokemonUrls.addAll(extractAllPokemonUrlFromResponse(responseJson));
                }
            }
        } catch (Exception e) {
            logger.error("Unexpected error occurred while connecting to get /pokemon: {}", e.getMessage());
        }

        return pokemonUrls;
    }

    public Optional<Pokemon> getPokemon(String url) {
        try {
            var responseJson = restClient.get().uri(url).retrieve().body(JsonNode.class);
            return of(PokemonFactory.create(responseJson));
        } catch (Exception e) {
            logger.error("Unexpected error occurred while accessing url {}: {}", url, e.getMessage());
            return empty();
        }
    }

    private List<String> extractAllPokemonUrlFromResponse(JsonNode response) {
        if (response == null || response.isNull()) {
            return new ArrayList<>();
        }

        var pokemonUrls = new ArrayList<String>();
        for (JsonNode pokemon : response.get("results")) {
            pokemonUrls.add(pokemon.get("url").asText());
        }
        return pokemonUrls;
    }
}
