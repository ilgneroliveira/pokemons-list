package com.pokemons.ilgner.listpokemons.Service;

import com.pokemons.ilgner.listpokemons.Model.ListPokemons;
import com.pokemons.ilgner.listpokemons.Model.Pokemon;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * ServiceGenerator
 *
 * @author Ilgner Fagundes <ilgner552@gmail.com>
 * @version 1.0
 */
public interface RetrofitService {
    @GET("pokemon/")
    Call<ListPokemons> getAllPokemons();

    @GET("pokemon/{id}/")
    Call<Pokemon> getPokemon(@Path("id") String id);
}
