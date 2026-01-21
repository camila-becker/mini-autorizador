package com.vr.miniautorizador.application.mapper;

import com.vr.miniautorizador.domain.model.entities.Cartao;
import com.vr.miniautorizador.interfaces.dto.CartaoResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartaoMapper {

    CartaoResponse toResponse(Cartao cartao);

}
