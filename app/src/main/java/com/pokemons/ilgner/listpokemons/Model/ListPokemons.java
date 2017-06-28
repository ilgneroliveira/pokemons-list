package com.pokemons.ilgner.listpokemons.Model;

import java.util.List;

/**
 * ListPokemons
 *
 * @author Ilgner Fagundes <ilgner552@gmail.com>
 * @version 1.0
 */
public class ListPokemons {
    private int count;
    private String previous;
    private List<ListPokemon> results;
    private String next;

    public ListPokemons() {}

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<ListPokemon> getResults() {
        return results;
    }

    public void setResults(List<ListPokemon> results) {
        this.results = results;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }
}
