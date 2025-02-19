package org.example.pokemonservice.api.dto;

public class PokemonDto {

    private String name;
    private int height;
    private int weight;
    private int baseExperience;

    public PokemonDto() {}

    public PokemonDto(String name, int height, int weight, int baseExperience) {
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.baseExperience = baseExperience;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setBaseExperience(int baseExperience) {
        this.baseExperience = baseExperience;
    }

    public String getName() {
        return name;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public int getBaseExperience() {
        return baseExperience;
    }

    @Override
    public boolean equals(Object oth) {
        if (oth == null) {
            return false;
        }

        if (oth instanceof PokemonDto otherPokemon) {
            return this.name.equals(otherPokemon.name)
                    && this.height == otherPokemon.height
                    && this.weight == otherPokemon.weight
                    && this.baseExperience == otherPokemon.baseExperience;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.name.hashCode() + Integer.hashCode(this.height)
                + Integer.hashCode(this.weight) + Integer.hashCode(this.baseExperience);
    }
}
