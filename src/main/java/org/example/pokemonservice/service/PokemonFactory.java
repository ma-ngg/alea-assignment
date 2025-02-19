package org.example.pokemonservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.example.pokemonservice.domain.Pokemon;

import static java.util.Optional.ofNullable;
import static org.example.pokemonservice.domain.Pokemon.pokemonBuilder;

public class PokemonFactory {

    public static Pokemon create(JsonNode node) {
        if (node == null) {
            throw new IllegalArgumentException("Node object must not be null");
        }
        return pokemonBuilder()
                .name(ofNullable(node.get("name")).map(JsonNode::asText).orElse(""))
                .id(ofNullable(node.get("id")).map(JsonNode::asInt).orElse(-1))
                .height(ofNullable(node.get("height")).map(JsonNode::asInt).orElse(-1))
                .weight(ofNullable(node.get("weight")).map(JsonNode::asInt).orElse(-1))
                .baseExperience(ofNullable(node.get("base_experience")).map(JsonNode::asInt).orElse(-1))
                .build();
    }
}
