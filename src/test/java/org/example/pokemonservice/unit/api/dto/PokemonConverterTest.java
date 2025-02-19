package org.example.pokemonservice.unit.api.dto;

import org.example.pokemonservice.api.dto.PokemonConverter;
import org.example.pokemonservice.api.dto.PokemonDto;
import org.example.pokemonservice.unit.PokemonTestFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PokemonConverterTest {
    private final PokemonConverter converter = new PokemonConverter();

    @Test
    void convertPokemonListToPokemonDtoList() {
        // given
        var pokemon1 = PokemonTestFactory.createBulbasaur();
        var pokemon2 = PokemonTestFactory.createIvysaur();
        var pokemon1ExpectedDto = new PokemonDto(pokemon1.getName(), pokemon1.getHeight(), pokemon1.getWeight(), pokemon1.getBaseExperience());
        var pokemon2ExpectedDto = new PokemonDto(pokemon2.getName(), pokemon2.getHeight(), pokemon2.getWeight(), pokemon2.getBaseExperience());

        // when
        var result = converter.convertToDto(List.of(pokemon1, pokemon2));

        // then
        assertEquals(2, result.size());
        assertTrue(result.containsAll(List.of(pokemon1ExpectedDto, pokemon2ExpectedDto)));
    }
}