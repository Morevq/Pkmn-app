package su.morevq.pkmn.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import su.morevq.pkmn.entity.CardEntity;
import su.morevq.pkmn.models.card.PokemonStage;
import su.morevq.pkmn.repository.CardRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CardDaoImpl implements CardDao {
    private final CardRepository cardRepository;

    @Override
    public CardEntity getCardById(UUID id) {
        return cardRepository.findCardEntityById(id).orElseThrow(
                () -> new RuntimeException("Card with id " + id + " not found")
        );
    }

    @Override
    public CardEntity getCardByName(String cardName) {
        return cardRepository.findCardEntityByName(cardName).orElseThrow(
                () -> new RuntimeException("Card with name " + cardName + " not found")
        );
    }

    @Override
    public CardEntity getCardByOwnerName(String ownerFamilyName, String ownerFirstName, String ownerSurName) {
        return cardRepository.findCardEntityByPokemonOwnerFullName(ownerFamilyName, ownerFirstName, ownerSurName).orElseThrow(
                () -> new RuntimeException("Card with owner name " + ownerFamilyName + " " + ownerFirstName + " " + ownerSurName + " not found")
        );
    }

    @Override
    public Optional<CardEntity> getExactCard(String name, String gameSet, String number, PokemonStage stage) {
        return cardRepository.findExactCardEntity(name, gameSet, number, stage);
    }

    @Override
    public List<CardEntity> getAllCards() {
        return cardRepository.findAll();
    }

    @Override
    public CardEntity saveCard(CardEntity card) {
        return cardRepository.save(card);
    }
}