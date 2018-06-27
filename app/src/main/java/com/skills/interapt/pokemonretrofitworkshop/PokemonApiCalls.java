package com.skills.interapt.pokemonretrofitworkshop;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PokemonApiCalls {


    @GET("pokemon/{name}")
    Call<PokemonInfo> getPokemonInfo(@Path("name") String pokemonName);

    @GET("ability/{name}")
    Call<PokemonAbilityInfo> getPokemonAbilityInfo(@Path("name") String pokemonAbilityName);


    class PokemonInfo {

        @SerializedName("name")
        private String name;
        @SerializedName("sprites")
        private Sprites sprite;

        @SerializedName("abilities")
        private List<Abilities> pokemonAbilities;

        public String getName() {
            return name;
        }

        public Sprites getSprite() {
            return sprite;
        }

        public List<Abilities> getPokemonAbilities() {
            return pokemonAbilities;
        }

        class Sprites {

            @SerializedName("front_default")
            private String defaultImage;

            public String getDefaultImage() {
                return defaultImage;
            }
        }

        class Abilities {

            @SerializedName("ability")
            private PokemonAbilities pokemonAbilities;

            public PokemonAbilities getPokemonAbilities() {
                return pokemonAbilities;
            }

            class PokemonAbilities {

                @SerializedName("name")
                private String abilityName;

                public String getAbilityName() {
                    return abilityName;
                }
            }
        }
    }

    class PokemonAbilityInfo {

        @SerializedName("effect_entries")
        private List<PokemonEffects> pokemonEffectsList;

        public List<PokemonEffects> getPokemonEffectsList() {
            return pokemonEffectsList;
        }

        class PokemonEffects {

            @SerializedName("effect")
            private String effect;

            public String getEffect() {
                return effect;
            }
        }
    }


}
