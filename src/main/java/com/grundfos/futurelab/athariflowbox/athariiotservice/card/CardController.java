package com.grundfos.futurelab.athariflowbox.athariiotservice.card;

import com.grundfos.futurelab.athariflowbox.athariiotservice.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Tag(name = "Cards", description = "Card Management")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/cards")
public class CardController {

    private final CardService cardService;

    @GetMapping
    @Operation(summary = "Get all cards")
    public ResponseEntity<ApiResponse<Collection<CardDomain>>> getAll() {
        List<CardDomain> domains = cardService.getAllCards()
                .stream().map(CardDomain::mapEntityToDomain)
                .collect(Collectors.toList());
        ApiResponse<Collection<CardDomain>> apiResponse = new ApiResponse<>(Boolean.TRUE, null, domains);

        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping
    @Operation(summary = "Create a new card")
    public ResponseEntity<ApiResponse<CardDomain>> create(@RequestBody CardDto dto) {
        Card card = cardService.createFromDto(dto);
        CardDomain domain = CardDomain.mapEntityToDomain(card);
        ApiResponse<CardDomain> apiResponse = new ApiResponse<>(Boolean.TRUE, null, domain);
        URI uri = MvcUriComponentsBuilder.fromController(getClass()).path("/{cardId}").buildAndExpand(card.getId()).toUri();

        return ResponseEntity.created(uri).body(apiResponse);
    }

    @GetMapping("/{cardId}")
    @Operation(summary = "Get a card by ID")
    public ResponseEntity<ApiResponse<CardDomain>> getCard(@PathVariable("cardId") String cardId) {
        ApiResponse<CardDomain> apiResponse = new ApiResponse<>();
        Optional<Card> optionalCard = cardService.findCardById(cardId);
        if (!optionalCard.isPresent()) {
            apiResponse.setSuccess(Boolean.FALSE);
            apiResponse.setMessage("Card not found!");
            apiResponse.setData(null);

            return ResponseEntity.notFound().build();
        }

        CardDomain domain = CardDomain.mapEntityToDomain(optionalCard.get());
        apiResponse.setData(domain);
        apiResponse.setSuccess(Boolean.TRUE);
        apiResponse.setMessage(null);

        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{cardId}")
    @Operation(summary = "Delete a card by ID")
    public ResponseEntity<ApiResponse<Void>> deleteCard(@PathVariable("cardId") String cardId) {
        ApiResponse<Void> apiResponse = new ApiResponse<Void>();

        try {
            cardService.deleteCardById(cardId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            apiResponse.setSuccess(Boolean.FALSE);
            apiResponse.setMessage(e.getMessage());
            apiResponse.setData(null);

            return ResponseEntity.internalServerError().body(apiResponse);
        }
    }

    @PutMapping("/{cardId}")
    @Operation(summary = "Update a card by ID")
    public ResponseEntity<ApiResponse<CardDomain>> updateCard(@PathVariable("cardId") String cardId, @RequestBody CardDto dto) {
        Optional<Card> optionalCard = cardService.findCardById(cardId);
        ApiResponse<CardDomain> apiResponse = new ApiResponse<>();
        if (!optionalCard.isPresent()) {
            apiResponse.setData(null);
            apiResponse.setMessage("Card not found with ID: " + cardId);
            return ResponseEntity.notFound().build();
        }
        Card card = optionalCard.get();
        card.setHolder(dto.getHolder());
        card.setDisabledAt(dto.getDisabledAt());
        card.setReason(dto.getReason());
        card.setSerialNumber(dto.getSerialNumber());

        cardService.saveCard(card);
        CardDomain domain = CardDomain.mapEntityToDomain(card);
        apiResponse.setData(domain);
        apiResponse.setSuccess(Boolean.TRUE);

        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{cardId}/disable")
    @Operation(summary = "Disable a card by ID")
    public ResponseEntity<ApiResponse<CardDomain>> disableCard(@PathVariable("cardId") String cardId, @RequestBody CardDisableDto dto) {
        ApiResponse<CardDomain> apiResponse = new ApiResponse<>();
        Optional<Card> optionalCard = cardService.findCardById(cardId);
        if (!optionalCard.isPresent()) {
            apiResponse.setData(null);
            apiResponse.setMessage("Card not found with ID: " + cardId);
            return ResponseEntity.notFound().build();
        }
        Card card = optionalCard.get();
        card.setReason(dto.getReason());
        card.setDisabledAt(LocalDateTime.now());
        cardService.saveCard(card);
        CardDomain domain = CardDomain.mapEntityToDomain(card);
        apiResponse.setData(domain);
        apiResponse.setSuccess(Boolean.TRUE);

        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{cardId}/enable")
    @Operation(summary = "Enable a card by ID")
    public ResponseEntity<ApiResponse<CardDomain>> enableCard(@PathVariable("cardId") String cardId) {
        ApiResponse<CardDomain> apiResponse = new ApiResponse<>();
        Optional<Card> optionalCard = cardService.findCardById(cardId);
        if (!optionalCard.isPresent()) {
            apiResponse.setSuccess(Boolean.FALSE);
            apiResponse.setData(null);
            apiResponse.setMessage("Card not found with ID: " + cardId);
            return ResponseEntity.notFound().build();
        }
        Card card = optionalCard.get();
        card.setReason(null);
        card.setDisabledAt(null);
        cardService.saveCard(card);
        CardDomain domain = CardDomain.mapEntityToDomain(card);
        apiResponse.setData(domain);
        apiResponse.setSuccess(Boolean.TRUE);

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{cardId}/isEnabled")
    @Operation(summary = "Check if a card is enabled")
    public ResponseEntity<ApiResponse<Boolean>> isCardEnabled(@PathVariable("cardId") String cardId) {
        ApiResponse<Boolean> apiResponse = new ApiResponse<Boolean>();
        Optional<Card> optionalCard = cardService.findCardById(cardId);
        if (!optionalCard.isPresent()) {
            apiResponse.setSuccess(Boolean.FALSE);
            apiResponse.setData(Boolean.FALSE);
            apiResponse.setMessage("Card not found with ID: " + cardId);
            return ResponseEntity.notFound().build();
        }
        Card card = optionalCard.get();
        apiResponse.setSuccess(Boolean.TRUE);
        apiResponse.setData(card.getIsEnabled());

        return ResponseEntity.ok(apiResponse);
    }
}
