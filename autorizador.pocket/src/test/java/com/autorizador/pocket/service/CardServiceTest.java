package com.autorizador.pocket.service;

import com.autorizador.pocket.mapper.CardMapper;
import com.autorizador.pocket.model.Card;
import com.autorizador.pocket.repository.CardRepository;
import com.autorizador.pocket.request.CardRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {
    @Mock
    private CardRepository repository;

    @Spy
    private final CardMapper mapper = Mappers.getMapper(CardMapper.class);

    @InjectMocks
    private CardService service;

    @Test
    @DisplayName("Should create new card and return 201 status")
    void shouldCreateNewCard() {
        var request = mockRequest();
        when(repository.findById(anyString())).thenReturn(Optional.empty());

        var cardResponseResponseEntity = service.create(request);

        verify(repository, times(1)).save(any());
        assertEquals(cardResponseResponseEntity.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    @DisplayName("Should not create new card and return 422 status")
    void shouldNotCreateNewCard() {
        var request = mockRequest();
        var card = new Card();
        when(repository.findById(anyString())).thenReturn(Optional.of(card));

        var cardResponseResponseEntity = service.create(request);

        verify(repository, never()).save(any());
        assertEquals(cardResponseResponseEntity.getStatusCode(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    CardRequest mockRequest(){
        return CardRequest.builder()
            .cardNumber("1234567890")
            .cpf("78931227899")
            .password("1234")
            .name("Meryl Streep")
            .build();
    }
}