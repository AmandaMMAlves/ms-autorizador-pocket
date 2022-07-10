package com.autorizador.pocket.mapper;

import com.autorizador.pocket.model.Card;
import com.autorizador.pocket.request.CardRequest;
import com.autorizador.pocket.response.CardResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CardMapper {

    Card requestToModel(CardRequest request);
    CardResponse modelToResponse(Card model);
}
