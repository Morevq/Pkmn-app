package su.morevq.pkmn.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import su.morevq.pkmn.dto.CardDto;
import su.morevq.pkmn.models.card.Card;
import su.morevq.pkmn.service.CardService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static su.morevq.pkmn.dto.CardDto.fromModel;


@RestController
@RequestMapping("/api/v1/card")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @GetMapping("")
    public ResponseEntity<List<CardDto>> getAllCards() {
        List<Card> cards = cardService.getAllCards();
        return ResponseEntity.ok(cards.stream().map(card -> fromModel(card, cardService)).collect(Collectors.toList()));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<CardDto> getCardById(@PathVariable UUID id) {
        Card card = cardService.getCardById(id);
        return ResponseEntity.ok(fromModel(card, cardService));
    }

    @GetMapping("/{name}")
    public ResponseEntity<CardDto> getCardByName(@PathVariable String name) {
        Card card = cardService.getCardByName(name);
        return ResponseEntity.ok(fromModel(card, cardService));
    }

    @PostMapping("")
    public ResponseEntity<CardDto> saveCard(@RequestBody Card card) {
        Card savedCard = Card.fromEntity(cardService.saveCard(card));
        return ResponseEntity.ok(fromModel(savedCard, cardService));
    }

}
