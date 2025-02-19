package org.example.pokemonservice.api.controller;

import org.example.pokemonservice.api.dto.PokemonConverter;
import org.example.pokemonservice.api.dto.PokemonDto;
import org.example.pokemonservice.api.dto.PokemonRequestParam;
import org.example.pokemonservice.service.PokemonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pokemons")
public class PokemonController {

    private final PokemonService pokemonService;
    private final PokemonConverter pokemonConverter;

    public PokemonController(PokemonService pokemonService, PokemonConverter pokemonConverter) {
        this.pokemonService = pokemonService;
        this.pokemonConverter = pokemonConverter;
    }

    @GetMapping("/top-five")
    public ResponseEntity<List<PokemonDto>> getPokemons(@RequestParam PokemonRequestParam criteria) {
        var pokemons = pokemonService.getTopFivePokemons(criteria);
        return ResponseEntity.ok(pokemonConverter.convertToDto(pokemons));
    }
}
