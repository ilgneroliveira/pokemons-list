package com.pokemons.ilgner.listpokemons;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.pokemons.ilgner.listpokemons.Model.ListPokemon;
import com.pokemons.ilgner.listpokemons.Model.ListPokemons;
import com.pokemons.ilgner.listpokemons.Service.RetrofitService;
import com.pokemons.ilgner.listpokemons.Service.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * ListPokemonsActivity
 *
 * @author Ilgner Fagundes <ilgner552@gmail.com>
 * @version 1.0
 */
public class ListPokemonsActivity extends AppCompatActivity {
    ListView lvPokemons;
    ListPokemons pokemons;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pokemons);

        dialog = ProgressDialog.show(ListPokemonsActivity.this, "Aguarde", "Carregando a  dados...");

        lvPokemons = (ListView) findViewById(R.id.lvPokemons);
        requestPokemons();

        lvPokemons.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(ListPokemonsActivity.this, ShowPokemonActivity.class);
                intent.putExtra("url", pokemons.getResults().get(position).getUrl());
                startActivity(intent);

            }
        });
    }

    private void requestPokemons() {
        RetrofitService service = ServiceGenerator.createService(RetrofitService.class);

        Call<ListPokemons> call = service.getAllPokemons();

        call.enqueue(new Callback<ListPokemons>() {
            @Override
            public void onResponse(Call<ListPokemons> call, Response<ListPokemons> response) {

                if (response.isSuccessful()) {

                    pokemons = response.body();

                    //verifica aqui se o corpo da resposta não é nulo
                    if (pokemons != null) {
                        List<String> names = new ArrayList<String>();
                        for (ListPokemon pokemon : pokemons.getResults()) {
                            names.add(pokemon.getName());
                        }
                        ArrayAdapter<String> namesAdapter = new ArrayAdapter<String>(ListPokemonsActivity.this, android.R.layout.simple_list_item_1, names);
                        lvPokemons.setAdapter(namesAdapter);
                        dialog.dismiss();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Resposta nula do servidor", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Resposta não foi sucesso", Toast.LENGTH_SHORT).show();
                    // segura os erros de requisição
                    ResponseBody errorBody = response.errorBody();
                }

            }

            @Override
            public void onFailure(Call<ListPokemons> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "Erro na chamada ao servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
