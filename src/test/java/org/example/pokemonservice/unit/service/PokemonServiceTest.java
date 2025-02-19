package org.example.pokemonservice.unit.service;

import org.example.pokemonservice.api.dto.PokemonRequestParam;
import org.example.pokemonservice.domain.Pokemon;
import org.example.pokemonservice.integration.service.PokemonIntegrationService;
import org.example.pokemonservice.service.PokemonService;
import org.example.pokemonservice.unit.PokemonTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PokemonServiceTest {

    private final PokemonIntegrationService integrationService = mock(PokemonIntegrationService.class);
    private final PokemonService service = new PokemonService(integrationService);

    private final List<String> pokemonUrls = List.of(
            "https://pokeapi.co/api/v2/pokemon/1/",
            "https://pokeapi.co/api/v2/pokemon/2/",
            "https://pokeapi.co/api/v2/pokemon/3/",
            "https://pokeapi.co/api/v2/pokemon/4/",
            "https://pokeapi.co/api/v2/pokemon/5/",
            "https://pokeapi.co/api/v2/pokemon/6/",
            "https://pokeapi.co/api/v2/pokemon/7/"
            );

    private final Pokemon bulbasaur = PokemonTestFactory.createBulbasaur();
    private final Pokemon ivysaur = PokemonTestFactory.createIvysaur();
    private final Pokemon venusaur = PokemonTestFactory.createVenusaur();
    private final Pokemon squirtle = PokemonTestFactory.createSquirtle();
    private final Pokemon weedle = PokemonTestFactory.createWeedle();
    private final Pokemon pidgey = PokemonTestFactory.createPidgey();
    private final Pokemon pidgeotto = PokemonTestFactory.createPidgeotto();

    @BeforeEach
    public void init() {
        when(integrationService.getPokemonUrls()).thenReturn(pokemonUrls);
        when(integrationService.getPokemon(pokemonUrls.get(0))).thenReturn(Optional.of(bulbasaur));
        when(integrationService.getPokemon(pokemonUrls.get(1))).thenReturn(Optional.of(ivysaur));
        when(integrationService.getPokemon(pokemonUrls.get(2))).thenReturn(Optional.of(venusaur));
        when(integrationService.getPokemon(pokemonUrls.get(3))).thenReturn(Optional.of(squirtle));
        when(integrationService.getPokemon(pokemonUrls.get(4))).thenReturn(Optional.of(weedle));
        when(integrationService.getPokemon(pokemonUrls.get(5))).thenReturn(Optional.of(pidgey));
        when(integrationService.getPokemon(pokemonUrls.get(6))).thenReturn(Optional.of(pidgeotto));
        service.postConstruct();
    }

    @Test
    void getTopFiveHighestPokemons() {
        // when
        var pokemons = service.getTopFivePokemons(PokemonRequestParam.HEIGHT);

        // then
        assertThat(pokemons.size()).isEqualTo(5);
        assertEquals(pidgeotto, pokemons.get(0));
        assertEquals(venusaur, pokemons.get(1));
        assertEquals(bulbasaur, pokemons.get(2));
        assertEquals(pidgey, pokemons.get(3));
        assertEquals(weedle, pokemons.get(4));
    }

    @Test
    void getTopFiveHeaviestPokemons() {
        // when
        var pokemons = service.getTopFivePokemons(PokemonRequestParam.WEIGHT);

        // then
        assertThat(pokemons.size()).isEqualTo(5);
        assertEquals(weedle, pokemons.get(0));
        assertEquals(venusaur, pokemons.get(1));
        assertEquals(ivysaur, pokemons.get(2));
        assertEquals(pidgeotto, pokemons.get(3));
        assertEquals(pidgey, pokemons.get(4));
    }

    @Test
    void getTopFiveMostExperiencedPokemons() {
        // when
        var pokemons = service.getTopFivePokemons(PokemonRequestParam.EXPERIENCE);

        // then
        assertThat(pokemons.size()).isEqualTo(5);
        assertEquals(weedle, pokemons.get(0));
        assertEquals(bulbasaur, pokemons.get(1));
        assertEquals(pidgeotto, pokemons.get(2));
        assertEquals(pidgey, pokemons.get(3));
        assertEquals(venusaur, pokemons.get(4));
    }
}