package org.example.pokemonservice.integration;

import org.example.pokemonservice.api.dto.PokemonDto;
import org.example.pokemonservice.api.dto.PokemonRequestParam;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PokemonControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getTopFiveHeaviestPokemons() {
        // given
        var criteria = PokemonRequestParam.WEIGHT;
        var url = "http://localhost:" + port + "/pokemons/top-five?criteria=" + criteria;

        // when
        var result = restTemplate.exchange(
                URI.create(url),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PokemonDto>>(){});

        // then
        assertTrue(result.getStatusCode().is2xxSuccessful());
        var pokemons = result.getBody();
        assertNotNull(pokemons);
        assertEquals(5, pokemons.size());
        assertTrue(pokemons.get(0).getWeight() > pokemons.get(4).getWeight());
    }

    @Test
    void getTopFiveHighestPokemons() {
        // given
        var criteria = PokemonRequestParam.HEIGHT;
        var url = "http://localhost:" + port + "/pokemons/top-five?criteria=" + criteria;

        // when
        var result = restTemplate.exchange(
                URI.create(url),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PokemonDto>>(){});

        // then
        assertTrue(result.getStatusCode().is2xxSuccessful());
        var pokemons = result.getBody();
        assertNotNull(pokemons);
        assertEquals(5, pokemons.size());
        assertTrue(pokemons.get(0).getHeight() > pokemons.get(4).getHeight());
    }

    @Test
    void getTopFiveMostExperiencedPokemons() {
        // given
        var criteria = PokemonRequestParam.EXPERIENCE;
        var url = "http://localhost:" + port + "/pokemons/top-five?criteria=" + criteria;

        // when
        var result = restTemplate.exchange(
                URI.create(url),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PokemonDto>>(){});

        // then
        assertTrue(result.getStatusCode().is2xxSuccessful());
        var pokemons = result.getBody();
        assertNotNull(pokemons);
        assertEquals(5, pokemons.size());
        assertTrue(pokemons.get(0).getBaseExperience() > pokemons.get(4).getBaseExperience());
    }
}
