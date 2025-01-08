package su.morevq.pkmn.dto;

import lombok.*;
import su.morevq.pkmn.models.card.AttackSkill;
import su.morevq.pkmn.models.card.Card;
import su.morevq.pkmn.models.card.EnergyType;
import su.morevq.pkmn.models.card.PokemonStage;
import su.morevq.pkmn.models.student.Student;
import su.morevq.pkmn.service.CardService;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CardDto {

    private String imageLink;
    private String name;
    private String number;
    private String retreatCost;
    private String gameSet;

    private PokemonStage pokemonStage;

    private EnergyType pokemonType;
    private EnergyType weaknessType;
    private EnergyType resistanceType;

    private char regulationMark;
    private short hp;

    private Student pokemonOwner;

    private List<AttackSkill> skills;

    private CardDto evolvesFrom;

    public static CardDto fromModel(Card card, CardService service)
    {
        CardDtoBuilder builder = CardDto.builder()
                .name(card.getName())
                .number(card.getNumber())
                .retreatCost(card.getRetreatCost())
                .gameSet(card.getGameSet())
                .pokemonStage(card.getPokemonStage())
                .pokemonType(card.getPokemonType())
                .weaknessType(card.getWeaknessType())
                .resistanceType(card.getResistanceType())
                .regulationMark(card.getRegulationMark())
                .hp(card.getHp())
                .pokemonOwner(card.getPokemonOwner())
                .skills(card.getSkills());
        if (card.getEvolvesFrom() != null)
        {
            builder.evolvesFrom(fromModel(card.getEvolvesFrom(), service));
        }
        builder.imageLink(service.getCardImageLink(card));
        return builder.build();
    }
}
