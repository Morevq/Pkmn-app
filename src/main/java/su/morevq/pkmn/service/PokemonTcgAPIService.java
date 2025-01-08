package su.morevq.pkmn.service;

import su.morevq.pkmn.models.card.AttackSkill;

import java.io.IOException;
import java.util.List;

public interface PokemonTcgAPIService {
    public String getPokemonCardURL(String name, String number) throws IOException;
    public List<AttackSkill> getPokemonCardAttacks(String name, String number);
}
