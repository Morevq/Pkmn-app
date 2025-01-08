package su.morevq.pkmn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import su.morevq.pkmn.models.card.PokemonStage;
import su.morevq.pkmn.entity.CardEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<CardEntity, UUID> {
    Optional<CardEntity> findCardEntityByName(String name);
    Optional<CardEntity> findCardEntityById(UUID cardId);
    @Query("SELECT c FROM CardEntity c WHERE c.pokemonOwner.familyName = :familyName AND c.pokemonOwner.firstName = :firstName AND c.pokemonOwner.surName = :surName")
    Optional<CardEntity> findCardEntityByPokemonOwnerFullName(String familyName, String firstName, String surName);
    @Query("SELECT c FROM CardEntity c WHERE c.name = :name AND c.gameSet = :gameSet AND c.number = :number AND c.pokemonStage = :stage")
    Optional<CardEntity> findExactCardEntity(String name, String gameSet, String number, PokemonStage stage);

}