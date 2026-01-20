package com.vr.miniautorizador.application.mapper;

import com.vr.miniautorizador.domain.model.entities.Cartao;
import com.vr.miniautorizador.interfaces.dto.CartaoRequest;
import com.vr.miniautorizador.interfaces.dto.CartaoResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartaoMapper {

    Cartao toEntity(CartaoRequest request);
    CartaoResponse toResponse(Cartao cartao);

}
