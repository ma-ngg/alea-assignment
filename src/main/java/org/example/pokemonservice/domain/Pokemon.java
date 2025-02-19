package org.example.pokemonservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Pokemon {
    private String name;

    @JsonIgnore
    private int id;

    private int height;
    private int weight;
    private int baseExperience;

    private Pokemon(String name, int id, int height, int weight, int baseExperience) {
        this.name = name;
        this.id = id;
        this.height = height;
        this.weight = weight;
        this.baseExperience = baseExperience;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getId() {
        return id;
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

        if (oth instanceof Pokemon otherPokemon) {
            return this.id == otherPokemon.id;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.name.hashCode() + Integer.hashCode(this.id) + Integer.hashCode(this.height) +
                Integer.hashCode(this.weight) + Integer.hashCode(this.baseExperience);
    }

    public static Builder pokemonBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private int id;
        private int height;
        private int weight;
        private int baseExperience;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder height(int height) {
            this.height = height;
            return this;
        }

        public Builder weight(int weight) {
            this.weight = weight;
            return this;
        }

        public Builder baseExperience(int baseExperience) {
            this.baseExperience = baseExperience;
            return this;
        }

        public Pokemon build() {
            return new Pokemon(name, id, height, weight, baseExperience);
        }
    }
}
