package su.morevq.pkmn.models.card.service;

import su.morevq.pkmn.models.card.Card;
import su.morevq.pkmn.entity.CardEntity;

import java.util.List;
import java.util.UUID;

public interface CardService {
    Card getCardByName(String name);
    Card getCardById(UUID id);
    Card getCardByOwnerName(String ownerFamilyName, String ownerFirstName, String ownerSurName);
    public List<Card> getAllCards();

    public CardEntity saveCard(Card card);

    public String getCardImageLink(Card card);
}
