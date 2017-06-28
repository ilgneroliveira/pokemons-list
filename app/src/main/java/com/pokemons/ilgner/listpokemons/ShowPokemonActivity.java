package com.pokemons.ilgner.listpokemons;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pokemons.ilgner.listpokemons.Model.Pokemon;
import com.pokemons.ilgner.listpokemons.Service.RetrofitService;
import com.pokemons.ilgner.listpokemons.Service.ServiceGenerator;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * ShowPokemonActivity
 *
 * @author Ilgner Fagundes <ilgner552@gmail.com>
 * @version 1.0
 */
public class ShowPokemonActivity extends AppCompatActivity {
    TextView tvName;
    TextView tvWeight;
    TextView tvHeight;
    ImageView image;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pokemon);

        dialog = ProgressDialog.show(ShowPokemonActivity.this, "Aguarde", "Carregando a  dados...");

        tvName = (TextView) findViewById(R.id.tvName);
        tvWeight = (TextView) findViewById(R.id.tvWeight);
        tvHeight = (TextView) findViewById(R.id.tvHeight);
        image = (ImageView) findViewById(R.id.image);

        Intent intent = getIntent();
        String  url = intent.getStringExtra("url");

        String[] result = url.split(":");
        result = result[1].split("/");
        requestPokemon(result[6]);
    }

    private void requestPokemon(String id) {
        RetrofitService service = ServiceGenerator.createService(RetrofitService.class);

        Call<Pokemon> call = service.getPokemon(id);

        call.enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {

                if (response.isSuccessful()) {

                    Pokemon pokemon = response.body();

                    //verifica aqui se o corpo da resposta não é nulo
                    if (pokemon != null) {
                        tvName.setText(pokemon.getName());
                        tvWeight.setText(String.format("%d", pokemon.getWeight()));
                        tvHeight.setText(String.format("%d", pokemon.getHeight()));

                        new DownloadImagemAsyncTask().execute(pokemon.getSprites().getFrontDefault());
                    } else {
                        Toast.makeText(getApplicationContext(), "Resposta nula do servidor", Toast.LENGTH_SHORT).show();
                    }

                } else {

                    Toast.makeText(getApplicationContext(), "Resposta não foi sucesso", Toast.LENGTH_SHORT).show();
                    // segura os erros de requisição
                    ResponseBody errorBody = response.errorBody();
                }

            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "Erro na chamada ao servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }
    class DownloadImagemAsyncTask extends AsyncTask<String, Void, Bitmap>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String urlString = params[0];

            try {
                URL url = new URL(urlString);
                HttpURLConnection conexao = (HttpURLConnection)
                        url.openConnection();
                conexao.setRequestMethod("GET");
                conexao.setDoInput(true);
                conexao.connect();

                InputStream is = conexao.getInputStream();
                Bitmap imagem = BitmapFactory.decodeStream(is);
                return imagem;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            dialog.dismiss();
            if (result != null){
                ImageView img = (ImageView)findViewById(R.id.image);
                img.setImageBitmap(result);
            } else {
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(ShowPokemonActivity.this).
                                setTitle("Erro").
                                setMessage("Não foi possivel carregar imagem, tente novamente mais tarde!").
                                setPositiveButton("OK", null);
                builder.create().show();
            }
        }
    }
}
