package su.morevq.pkmn.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;
import su.morevq.pkmn.models.card.AttackSkill;
import su.morevq.pkmn.models.card.Card;
import su.morevq.pkmn.models.card.EnergyType;
import su.morevq.pkmn.SkillDeserializer;
import su.morevq.pkmn.models.card.PokemonStage;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "cards")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardEntity {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column
    private String name;

    @Column(columnDefinition = "smallint")
    private short hp;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "evolves_from_id")
    private CardEntity evolvesFrom;

    @Column(name = "game_set")
    private String gameSet;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pokemon_owner_id")
    private StudentEntity pokemonOwner;

    @Column(name = "retreat_cost")
    private String retreatCost;

    @Enumerated(EnumType.STRING)
    @Column(name="stage")
    private PokemonStage pokemonStage;

    @Enumerated(EnumType.STRING)
    @Column(name = "weakness_type")
    private EnergyType weaknessType;

    @Enumerated(EnumType.STRING)
    @Column(name = "resistance_type")
    private EnergyType resistanceType;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "attack_skills")
    @JsonDeserialize(using = SkillDeserializer.class)
    private List<AttackSkill> skills;

    @Enumerated(EnumType.STRING)
    @Column(name = "pokemon_type")
    private EnergyType pokemonType;

    @Column(name = "regulation_mark")
    private char regulationMark;

    @Column(name = "card_number")
    private String number;

    public static CardEntity fromModel(Card card) {
        CardEntityBuilder builder = CardEntity.builder()
                .name(card.getName())
                .hp(card.getHp())
                .number(card.getNumber())
                .retreatCost(card.getRetreatCost())
                .pokemonStage(card.getPokemonStage())
                .pokemonType(card.getPokemonType())
                .weaknessType(card.getWeaknessType())
                .resistanceType(card.getResistanceType())
                .gameSet(card.getGameSet())
                .regulationMark(card.getRegulationMark())
                .skills(card.getSkills());
        if(card.getEvolvesFrom() != null) {
            builder.evolvesFrom(fromModel(card.getEvolvesFrom()));
        }
        if(card.getPokemonOwner() != null){
            builder.pokemonOwner(StudentEntity.fromModel(card.getPokemonOwner()));
        }
        return builder.build();
    }
}