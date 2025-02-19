package org.example.pokemonservice.unit;

import org.example.pokemonservice.domain.Pokemon;

import static org.example.pokemonservice.domain.Pokemon.pokemonBuilder;

public class PokemonTestFactory {

    public static Pokemon createBulbasaur() {
        return pokemonBuilder()
                .name("bulbasaur")
                .id(1)
                .height(101)
                .weight(54)
                .baseExperience(952)
                .build();
    }

    public static Pokemon createIvysaur() {
        return pokemonBuilder()
                .name("ivysaur")
                .id(2)
                .height(95)
                .weight(87)
                .baseExperience(300)
                .build();
    }

    public static Pokemon createVenusaur() {
        return pokemonBuilder()
                .name("venusaur")
                .id(3)
                .height(102)
                .weight(99)
                .baseExperience(547)
                .build();
    }

    public static Pokemon createSquirtle() {
        return pokemonBuilder()
                .name("squirtle")
                .id(4)
                .height(67)
                .weight(42)
                .baseExperience(400)
                .build();
    }

    public static Pokemon createWeedle() {
        return pokemonBuilder()
                .name("weedle")
                .id(5)
                .height(97)
                .weight(100)
                .baseExperience(1045)
                .build();
    }

    public static Pokemon createPidgey() {
        return pokemonBuilder()
                .name("pidgey")
                .id(6)
                .height(98)
                .weight(65)
                .baseExperience(741)
                .build();
    }

    public static Pokemon createPidgeotto() {
        return pokemonBuilder()
                .name("pidgeotto")
                .id(7)
                .height(150)
                .weight(80)
                .baseExperience(945)
                .build();
    }
}
