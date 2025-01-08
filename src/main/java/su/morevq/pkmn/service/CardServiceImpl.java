package su.morevq.pkmn.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import su.morevq.pkmn.models.card.Card;
import su.morevq.pkmn.dao.CardDao;
import su.morevq.pkmn.entity.CardEntity;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService{
    private final CardDao cardDao;
    private final StudentService studentService;
    private final PokemonTcgAPIService pokemonTcgAPIService;

    @Override
    public Card getCardByName(String name) {
        return Card.fromEntity(cardDao.getCardByName(name));
    }

    @Override
    public Card getCardByOwnerName(String ownerFamilyName, String ownerFirstName, String ownerSurName) {
        return Card.fromEntity(cardDao.getCardByOwnerName(ownerFamilyName, ownerFirstName, ownerSurName));
    }

    @Override
    public Card getCardById(UUID id) {
        return Card.fromEntity(cardDao.getCardById(id));
    }

    @Override
    public List<Card> getAllCards() {
        List<CardEntity> entities = cardDao.getAllCards();
        if (entities.isEmpty()){
            throw new RuntimeException("No cards found");
        }
        return entities.stream().map(Card::fromEntity).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public CardEntity saveCard(Card card) {
        cardDao.getExactCard(card.getName(), card.getGameSet(), card.getNumber(), card.getPokemonStage())
                .ifPresent(existingCard -> {
                    throw new RuntimeException("Card already exists");
                });

        CardEntity entity = CardEntity.fromModel(card);

        if (card.getPokemonOwner() != null) {
            entity.setPokemonOwner(
                    studentService.getExactStudent(
                            card.getPokemonOwner().getFirstName(),
                            card.getPokemonOwner().getPatronicName(),
                            card.getPokemonOwner().getSurName(),
                            card.getPokemonOwner().getGroup()
                    ).orElseGet(() -> studentService.saveStudent(card.getPokemonOwner()))
            );
        }

        if (card.getEvolvesFrom() != null) {
            entity.setEvolvesFrom(saveCard(card.getEvolvesFrom()));
        }

        return cardDao.saveCard(entity);
    }

    @Override
    public String getCardImageLink(Card card) {
        try {
            return pokemonTcgAPIService.getPokemonCardURL(card.getName(), card.getNumber());
        } catch (IOException e) {
            throw new RuntimeException("Error while getting card image");
        }
    }
}
