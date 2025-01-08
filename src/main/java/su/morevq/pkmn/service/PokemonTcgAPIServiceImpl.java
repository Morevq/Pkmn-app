package su.morevq.pkmn.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import su.morevq.pkmn.Morozova.web.http.PokemonHttpClient;
import su.morevq.pkmn.models.card.AttackSkill;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PokemonTcgAPIServiceImpl implements PokemonTcgAPIService {

    private final PokemonHttpClient httpClient;

    @Override
    public String getPokemonCardURL(String name, String number){
        try {
            JsonNode node = httpClient.getPokemonCard(name, number);

            for (JsonNode item : node.findValues("images")) {
                if (item.findValue("large") != null) {
                    return item.findValue("large").asText();
                }
            }
            return null;
        }
        catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    public static ArrayList<AttackSkill> parseAttackSkillsFromJson(ArrayNode json) {
        ArrayList<AttackSkill> result = new ArrayList<>();
        return getAttackSkills(json, result);
    }

    private static ArrayList<AttackSkill> getAttackSkills(ArrayNode json, ArrayList<AttackSkill> result) {
        for(int i = 0; i < json.size(); i++){
            JsonNode ji = json.get(i);
            AttackSkill as = new AttackSkill();
            as.setDescription(ji.findValue("text").toString().replace("\"", ""));
            as.setCost(ji.findValue("cost").toString());
            as.setDamage((ji.get("damage").asInt()));
            as.setName(ji.findValue("name").toString());
            result.add(as);
        }
        return result;
    }

    @Override
    public ArrayList<AttackSkill> getPokemonCardAttacks(String name, String number) {
        try {
            return parseAttackSkillsFromJson((ArrayNode) httpClient.getPokemonCard(name, number).findValue("attacks"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to get attacks");
        }
    }
}
