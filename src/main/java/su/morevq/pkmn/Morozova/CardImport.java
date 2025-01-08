package su.morevq.pkmn.Morozova;

import lombok.Getter;
import su.morevq.pkmn.models.card.AttackSkill;
import su.morevq.pkmn.models.card.Card;
import su.morevq.pkmn.models.card.EnergyType;
import su.morevq.pkmn.models.card.PokemonStage;
import su.morevq.pkmn.models.student.Student;

import java.io.*;
import java.util.*;

@Getter
public class CardImport {
    private Card card;
    public CardImport(String filename) throws IOException {
        try {
            String[] lines = readLines(filename);
            card = new Card();

            card = setAll(lines);
        }
        catch(Error e){
            System.out.println("Error " + e.toString());
        }
    }

    private String[] readLines(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader( new FileInputStream(filename), "UTF-8"));
        String line = reader.readLine();

        String[] lines = new String[20];
        int i = 0;
        lines[i] = line;
        while (line != null) {
            line = reader.readLine();

            ++i;
            lines[i] = line;
        }
        return lines;
    }

    private Card setAll(String[] lines) throws IOException {
        card.setPokemonStage(stage(lines[0]));
        card.setName(lines[1]);
        card.setHp(Short.parseShort(lines[2]));
        card.setPokemonType(type(lines[3]));

        if(lines[4].equals("-")) card.setEvolvesFrom(null);
        else{
            CardImport cardEvolves = new CardImport(lines[4]);
            card.setEvolvesFrom(cardEvolves.getCard());
        }

        AttackSkill attackSkill;
        ArrayList<AttackSkill> skill = new ArrayList<AttackSkill>();
        String[] skills = lines[5].split(" / ");
        for(int i = 0; i < skills.length; i += 3){
            attackSkill = new AttackSkill();

            attackSkill.setCost(skills[i]);
            attackSkill.setName(skills[i+1]);
            attackSkill.setDamage(Integer.parseInt(skills[i+2]));

            skill.add(i/3, attackSkill);
        }
        card.setSkills(skill);

        card.setWeaknessType(type(lines[6]));
        card.setResistanceType(type(lines[7]));
        card.setRetreatCost(lines[8]);
        card.setGameSet(lines[9]);
        card.setRegulationMark(lines[10].charAt(0));

        String[] ownerStrings = lines[11].split(" / ");
        Student owner = new Student(ownerStrings[0], ownerStrings[1], ownerStrings[2], ownerStrings[3]);

        card.setNumber(lines[12]);

        card.setPokemonOwner(owner);
        return card;
    }

    private PokemonStage stage(String s){
        return PokemonStage.valueOf(s);
    }

    private EnergyType type(String s){
        return EnergyType.valueOf(s);
    }
}
