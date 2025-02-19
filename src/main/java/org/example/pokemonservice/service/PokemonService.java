package org.example.pokemonservice.service;

import jakarta.annotation.PostConstruct;
import org.example.pokemonservice.api.dto.PokemonRequestParam;
import org.example.pokemonservice.domain.Pokemon;
import org.example.pokemonservice.integration.service.PokemonIntegrationService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

@Service
public class PokemonService {

    private final HashSet<Pokemon> pokemons = new HashSet<>();

    private final int TOP_POKEMONS_LIMIT = 5;

    private final PokemonIntegrationService integrationService;

    public PokemonService(PokemonIntegrationService integrationService) {
        this.integrationService = integrationService;
    }

    @PostConstruct
    public void postConstruct() {
        // to keep pokemons data updated, we can use event-driven design (if list of pokemons was updated, reload) or
        // crate a crob job that will update pokemons list behind the sence every X minutes
        loadPokemons();
    }

    public List<Pokemon> getTopFivePokemons(PokemonRequestParam criteria) {
        var queue = createPriorityQueueBasedOnCriteria(criteria);
        queue.addAll(pokemons);
        return queue.stream().limit(TOP_POKEMONS_LIMIT).toList();
    }

    private TreeSet<Pokemon> createPriorityQueueBasedOnCriteria(PokemonRequestParam criteria) {
        return switch (criteria) {
            case HEIGHT -> new TreeSet<>((p1, p2) -> Integer.compare(p2.getHeight(), p1.getHeight()));
            case WEIGHT -> new TreeSet<>((p1, p2) -> Integer.compare(p2.getWeight(), p1.getWeight()));
            case EXPERIENCE -> new TreeSet<>((p1, p2) -> Integer.compare(p2.getBaseExperience(), p1.getBaseExperience()));
        };
    }

    private void loadPokemons() {
        if (pokemons.isEmpty()) {
            List<String> pokemonUrls = integrationService.getPokemonUrls();
            for (String pokemonUrl : pokemonUrls) {
                integrationService.getPokemon(pokemonUrl)
                        .map(pokemons::add);
            }
        }
    }
}
