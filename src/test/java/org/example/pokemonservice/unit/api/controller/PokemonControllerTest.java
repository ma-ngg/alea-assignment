package org.example.pokemonservice.unit.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.pokemonservice.api.controller.PokemonController;
import org.example.pokemonservice.api.dto.PokemonConverter;
import org.example.pokemonservice.api.dto.PokemonDto;
import org.example.pokemonservice.api.dto.PokemonRequestParam;
import org.example.pokemonservice.service.PokemonService;
import org.example.pokemonservice.unit.PokemonTestFactory;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PokemonControllerTest {
    private final PokemonService pokemonService = mock(PokemonService.class);
    private final PokemonConverter pokemonConverter = mock(PokemonConverter.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final PokemonController controller = new PokemonController(pokemonService, pokemonConverter);

    @Test
    void getAllPokemons() {
        // given
        var pokemon1 = PokemonTestFactory.createBulbasaur();
        var pokemon2 = PokemonTestFactory.createIvysaur();
        var pokemon3 = PokemonTestFactory.createPidgeotto();
        var pokemon4 = PokemonTestFactory.createSquirtle();
        var pokemon5 = PokemonTestFactory.createVenusaur();

        var pokemon1Dto = objectMapper.convertValue(pokemon1, PokemonDto.class);
        var pokemon2Dto = objectMapper.convertValue(pokemon2, PokemonDto.class);
        var pokemon3Dto = objectMapper.convertValue(pokemon3, PokemonDto.class);
        var pokemon4Dto = objectMapper.convertValue(pokemon4, PokemonDto.class);
        var pokemon5Dto = objectMapper.convertValue(pokemon5, PokemonDto.class);

        when(pokemonService.getTopFivePokemons(PokemonRequestParam.HEIGHT))
                .thenReturn(List.of(pokemon1, pokemon2, pokemon3, pokemon4, pokemon5));
        when(pokemonConverter.convertToDto(List.of(pokemon1, pokemon2, pokemon3, pokemon4, pokemon5)))
                .thenReturn(List.of(pokemon1Dto, pokemon2Dto, pokemon3Dto, pokemon4Dto, pokemon5Dto));

        // when
        var res = controller.getPokemons(PokemonRequestParam.HEIGHT);

        // then
        assertEquals(HttpStatusCode.valueOf(200), res.getStatusCode());
        assertNotNull(res.getBody());
        assertTrue(res.getBody().containsAll(List.of(pokemon1Dto, pokemon2Dto, pokemon3Dto, pokemon4Dto, pokemon5Dto)));
    }
}