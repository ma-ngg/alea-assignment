package org.example.pokemonservice.unit.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.pokemonservice.service.PokemonFactory;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PokemonFactoryTest {

    private final String resourcePath = "src/test/resources/data/%s";

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void createPokemon() throws IOException {
        // given
        var pokemonData = mapper.readTree(new FileInputStream(resourcePath.formatted("pokemonData.json")).readAllBytes());

        // when
        var pokemon = PokemonFactory.create(pokemonData);

        // then
        assertEquals(1, pokemon.getId());
        assertEquals(12, pokemon.getHeight());
        assertEquals(300, pokemon.getWeight());
        assertEquals(175, pokemon.getBaseExperience());
    }

    @Test
    void createPokemonWithNullNode() throws IOException {
        // when
        var ex = assertThrows(IllegalArgumentException.class, () -> PokemonFactory.create(null));

        // then
        assertEquals("Node object must not be null", ex.getMessage());
    }
}