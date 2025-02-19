package org.example.pokemonservice.api.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.pokemonservice.domain.Pokemon;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PokemonConverter {

    private final ObjectMapper objectMapper;

    public PokemonConverter() {
        this.objectMapper = new ObjectMapper();
    }

    public List<PokemonDto> convertToDto(List<Pokemon> pokemons) {
        return pokemons.stream()
                .map(pokemon -> objectMapper.convertValue(pokemon, PokemonDto.class))
                .collect(Collectors.toList());
    }
}
