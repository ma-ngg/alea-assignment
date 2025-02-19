package org.example.pokemonservice.unit.integration.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.pokemonservice.integration.service.PokemonIntegrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.ExpectedCount.times;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withException;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

class PokemonIntegrationServiceTest {

    private final String resourcePath = "src/test/resources/data/%s";

    private final RestClient.Builder restClient = RestClient.builder();
    private final ObjectMapper mapper = new ObjectMapper();
    private final MockRestServiceServer restMockServer = MockRestServiceServer.bindTo(restClient).build();
    private final PokemonIntegrationService service = new PokemonIntegrationService(restClient.build());

    @BeforeEach
    public void init() {
        ReflectionTestUtils.setField(service, "getPokemonsUrl", "https://pokeapi.co/api/v2/pokemon");
    }

    @Test
    void getPokemonsUrlsSuccessfully() throws IOException {
        //given
        var pokemonResponse1 = mapper.readTree(new FileInputStream(resourcePath.formatted("getPokemons1.json")).readAllBytes());
        var pokemonResponse2 = mapper.readTree(new FileInputStream(resourcePath.formatted("getPokemons2.json")).readAllBytes());
        var pokemonUrlsExpected = extractPokemonUrls(pokemonResponse1, pokemonResponse2);
        restMockServer
                .expect(times(2), requestTo("https://pokeapi.co/api/v2/pokemon"))
                .andExpect(method(GET))
                .andRespond(withSuccess(mapper.writeValueAsString(pokemonResponse1), APPLICATION_JSON));
        restMockServer
                .expect(times(2), requestTo("https://pokeapi.co/api/v2/pokemon?offset=20&limit=20"))
                .andExpect(method(GET))
                .andRespond(withSuccess(mapper.writeValueAsString(pokemonResponse2), APPLICATION_JSON));

        //when
        var pokemonUrls = service.getPokemonUrls();

        //then
        assertEquals(5, pokemonUrls.size());
        assertTrue(pokemonUrls.containsAll(pokemonUrlsExpected));
    }

    @Test
    void getPokemonUrlsWithExceptionDuringIntegration() {
        // given
        restMockServer
                .expect(times(2), requestTo("https://pokeapi.co/api/v2/pokemon"))
                .andExpect(method(GET))
                .andRespond(withException(new RemoteException("Something went wrong")));

        // when
        var pokemonUrls = service.getPokemonUrls();

        // then
        assertTrue(pokemonUrls.isEmpty());
    }

    @Test
    void getPokemonSuccessfully() throws IOException {
        // given
        var pokemonData = mapper.readTree(new FileInputStream(resourcePath.formatted("pokemonData.json")).readAllBytes());
        var pokemonUrl= "https://pokeapi.co/api/v2/pokemon/1";
        restMockServer
                .expect(times(1), requestTo(pokemonUrl))
                .andExpect(method(GET))
                .andRespond(withSuccess(pokemonData.toString(), APPLICATION_JSON));
        // when
        var pokemon = service.getPokemon(pokemonUrl);

        // then
        assertTrue(pokemon.isPresent());
        assertEquals(1, pokemon.get().getId());
        assertEquals(12, pokemon.get().getHeight());
        assertEquals(300, pokemon.get().getWeight());
        assertEquals(175, pokemon.get().getBaseExperience());
    }

    @Test
    void getPokemonWithExceptionDuringIntegration() {
        // given
        var pokemonUrl= "https://pokeapi.co/api/v2/pokemon/1";
        restMockServer
                .expect(times(1), requestTo(pokemonUrl))
                .andExpect(method(GET))
                .andRespond(withException(new RemoteException("Something went wrong")));
        // when
        var pokemon = service.getPokemon(pokemonUrl);

        // then
        assertTrue(pokemon.isEmpty());
    }

    private List<String> extractPokemonUrls(JsonNode... responses) {
        var pokemonUrls = new ArrayList<String>();
        for (var response : responses) {
            for (var pokemonData : response.get("results")) {
                pokemonUrls.add(pokemonData.get("url").asText());
            }
        }
        return pokemonUrls;
    }
}