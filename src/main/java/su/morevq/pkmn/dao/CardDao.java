package su.morevq.pkmn.dao;

import su.morevq.pkmn.entity.CardEntity;
import su.morevq.pkmn.models.card.PokemonStage;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CardDao {
    CardEntity getCardById(UUID id);
    CardEntity getCardByName(String cardName);
    CardEntity getCardByOwnerName(String ownerFamilyName, String ownerFirstName, String ownerSurName);
    public Optional<CardEntity> getExactCard(String name, String gameSet, String number, PokemonStage stage);
    List<CardEntity> getAllCards();
    CardEntity saveCard(CardEntity card);
}
