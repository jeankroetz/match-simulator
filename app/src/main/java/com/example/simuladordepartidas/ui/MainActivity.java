package com.example.simuladordepartidas.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.simuladordepartidas.R;
import com.example.simuladordepartidas.data.MatchesAPI;
import com.example.simuladordepartidas.databinding.ActivityMainBinding;
import com.example.simuladordepartidas.domain.Match;
import com.example.simuladordepartidas.ui.adapter.MatchesAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends Activity {

    private ActivityMainBinding binding;
    private MatchesAPI matchesApi;
    private MatchesAdapter matchesAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // binding.texView.text = "By world"

        setupHttpClient();
        setupMatchesList();
        setupMatchesRefresh();
        setupFloatingActionButton();
    }

    private void setupHttpClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jeankroetz.github.io/matches-simulator-api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

       matchesApi = retrofit.create(MatchesAPI.class);
    }

    private void setupFloatingActionButton() {
        binding.floatingActionButtonSimular.setOnClickListener(view -> {
            view.animate().rotationBy(360).setDuration(500).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                Random random = new Random();
                for (int i = 0; i < matchesAdapter.getItemCount(); i++) {
                    Match match = matchesAdapter.getMatches().get(i);
                    match.getHomeTeam().setScore(random.nextInt(match.getHomeTeam().getStars() + 1));
                    match.getAwayTeam().setScore(random.nextInt(match.getAwayTeam().getStars() + 1));
                    matchesAdapter.notifyItemChanged(i);
                }
            }
        });
    });
}

    private void setupMatchesRefresh() {
        binding.swipeMatches.setOnRefreshListener(this::findMatchesFromAPI);
    }

    private void setupMatchesList() {
        //Diz que a lista tem um tamanho fixo, para dar uma melhorada na performance
        binding.recycleViewMatches.setHasFixedSize(true);

        //Setado um Layout para o recycleView
        binding.recycleViewMatches.setLayoutManager(new LinearLayoutManager(this));

        //("Listar as partidas usando nossa API");
        findMatchesFromAPI();
    }

    private void findMatchesFromAPI() {
        binding.swipeMatches.setRefreshing(true);
        matchesApi.getMatches().enqueue(new Callback<List<Match>>() {
            @Override
            public void onResponse(Call<List<Match>> call, Response<List<Match>> response) {
                if (response.isSuccessful()){
                    List<Match> matches = response.body();
                    Log.i("Simulator", "Deu certo! Partidas: " + matches.size());

                    //Impementamos esse adapter e ele recebe uma lista de partidas
                    matchesAdapter = new MatchesAdapter(matches);

                    //Aqui estamos instanciando o adapter e setando ele dentro do recycleView
                    binding.recycleViewMatches.setAdapter(matchesAdapter);
                }else{
                    showErrorMessage();
                }
                binding.swipeMatches.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Match>> call, Throwable t) {
                showErrorMessage();
                binding.swipeMatches.setRefreshing(false);
            }
        });
    }

    private void showErrorMessage() {
        Snackbar.make(binding.floatingActionButtonSimular, R.string.errorMessage, Snackbar.LENGTH_LONG).show();
    }

}