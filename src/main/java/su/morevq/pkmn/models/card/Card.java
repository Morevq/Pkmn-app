package su.morevq.pkmn.models.card;

import lombok.*;
import lombok.experimental.FieldDefaults;
import su.morevq.pkmn.models.student.Student;
import su.morevq.pkmn.entity.CardEntity;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Card implements Serializable {
    public static final long serialVersionUID = 1L;
    PokemonStage pokemonStage;
    String name;
    short hp;
    EnergyType pokemonType;
    Card evolvesFrom;
    List<AttackSkill> skills;
    EnergyType weaknessType;
    EnergyType resistanceType;
    String retreatCost;
    String gameSet;
    char regulationMark;
    Student pokemonOwner;
    String number;

    public static Card fromEntity(CardEntity entity)
    {
        CardBuilder cardBuilder = Card.builder()
                .name(entity.getName())
                .hp(entity.getHp())
                .number(entity.getNumber())
                .retreatCost(entity.getRetreatCost())
                .gameSet(entity.getGameSet())
                .pokemonStage(entity.getPokemonStage())
                .pokemonType(entity.getPokemonType())
                .weaknessType(entity.getWeaknessType())
                .resistanceType(entity.getResistanceType())
                .regulationMark(entity.getRegulationMark())
                .skills(entity.getSkills());
        if (entity.getEvolvesFrom() != null)
        {
            cardBuilder.evolvesFrom(fromEntity(entity.getEvolvesFrom()));
        }
        if(entity.getPokemonOwner() != null)
        {
            cardBuilder.pokemonOwner(Student.fromEntity(entity.getPokemonOwner()));
        }
        return cardBuilder.build();
    }
}
