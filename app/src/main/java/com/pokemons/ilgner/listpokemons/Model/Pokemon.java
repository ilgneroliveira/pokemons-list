package com.pokemons.ilgner.listpokemons.Model;

/**
 * Pokemon
 *
 * @author Ilgner Fagundes <ilgner552@gmail.com>
 * @version 1.0
 */
public class Pokemon {
    private int id;
    private String name;
    private int weight;
    private int height;
    private Sprites sprites;

    public Pokemon() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Sprites getSprites() {
        return sprites;
    }

    public void setSprites(Sprites sprites) {
        this.sprites = sprites;
    }
}
