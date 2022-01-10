package com.grundfos.futurelab.athariflowbox.athariiotservice.card;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;

    public Collection<Card> getAllCards() {
        return cardRepository.findAll();
    }

    public Card createFromDto(CardDto dto) {
        Card card = Card.fromDto(dto);
        return cardRepository.saveAndFlush(card);
    }

    public Optional<Card> findCardById(String cardId) {
        return cardRepository.findById(cardId);
    }

    public void deleteCardById(String cardId) {
        cardRepository.deleteById(cardId);
    }

    public void saveCard(Card card) {
        cardRepository.saveAndFlush(card);
    }
}
