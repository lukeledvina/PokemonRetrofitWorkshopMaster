package com.skills.interapt.pokemonretrofitworkshop;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.skills.interapt.pokemonretrofitworkshop.MainActivity.POKEMON_NAME;

public class PokemonInfoFragment extends Fragment {


    @BindView(R.id.pokemon_name_textview)
    protected TextView displayPokemonName;
    @BindView(R.id.pokemon_imageview)
    protected ImageView pokemonImage;

    @BindView(R.id.details_textview)
    protected TextView pokemonAbilityDetails;


    private Retrofit retrofit;
    private String baseUrl = "https://pokeapi.co/api/v2/";

    private PokemonApiCalls pokemonApiCalls;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pokemon_info, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    public static PokemonInfoFragment newInstance() {

        Bundle args = new Bundle();

        PokemonInfoFragment fragment = new PokemonInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();

        String pokemonName = getArguments().getString(POKEMON_NAME);

        buildRetrofit();

        makeApiCalls(pokemonName);
    }

    private void makeApiCalls(String pokemonName) {

        pokemonApiCalls.getPokemonInfo(pokemonName).enqueue(new Callback<PokemonApiCalls.PokemonInfo>() {
            @Override
            public void onResponse(Call<PokemonApiCalls.PokemonInfo> call, Response<PokemonApiCalls.PokemonInfo> response) {

                if(response.isSuccessful()) {
                    displayPokemonName.setText(response.body().getName());
                    Glide.with(getContext()).load(response.body().getSprite().getDefaultImage()).into(pokemonImage);
                    makeSecondApiCall(response.body().getPokemonAbilities().get(0).getPokemonAbilities().getAbilityName());

                } else {
                    //TODO Handle call not being made properly
                    Toast.makeText(getActivity(), "API Responded but was not successful", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<PokemonApiCalls.PokemonInfo> call, Throwable t) {

                Toast.makeText(getActivity(), "Call Failed", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void makeSecondApiCall(String abilityName) {
        pokemonApiCalls.getPokemonAbilityInfo(abilityName).enqueue(new Callback<PokemonApiCalls.PokemonAbilityInfo>() {
            @Override
            public void onResponse(Call<PokemonApiCalls.PokemonAbilityInfo> call, Response<PokemonApiCalls.PokemonAbilityInfo> response) {
                if(response.isSuccessful()) {
                    //Get Effect details to show in the screen
                    pokemonAbilityDetails.setText(response.body().getPokemonEffectsList().get(0).getEffect());

                } else {

                    Toast.makeText(getActivity(), "API responded to 2nd call but was not successful.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PokemonApiCalls.PokemonAbilityInfo> call, Throwable t) {

                Toast.makeText(getActivity(), "2nd call failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void buildRetrofit() {

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        pokemonApiCalls = retrofit.create(PokemonApiCalls.class);
    }
}
