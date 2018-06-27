package com.skills.interapt.pokemonretrofitworkshop;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.pokemon_input_edittext)
    protected TextInputEditText pokemonName;

    public static final String POKEMON_NAME = "pokemon_name";

    private PokemonInfoFragment pokemonInfoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.capture_button)
    protected void captureClicked() {
        if(pokemonName.getText().toString().isEmpty()) {

            Toast.makeText(this, "You must enter a name!", Toast.LENGTH_SHORT).show();
        } else {

            //Create new instance of fragment
            pokemonInfoFragment  = PokemonInfoFragment.newInstance();

            //Bundle to send pokemon name to fragment for API call
            Bundle bundle = new Bundle();
            bundle.putString(POKEMON_NAME, pokemonName.getText().toString().toLowerCase());
            pokemonInfoFragment.setArguments(bundle);

            //Bring fragment into the view
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, pokemonInfoFragment).commit();


        }

    }
}
