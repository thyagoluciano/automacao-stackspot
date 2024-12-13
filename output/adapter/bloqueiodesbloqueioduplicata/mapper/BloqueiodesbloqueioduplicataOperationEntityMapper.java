// template path: adapter/bloqueiodesbloqueioduplicata/mapper/BloqueiodesbloqueioduplicataOperationEntityMapper.java
package br.com.nuclea.dupcanalparticipanteapi.adapter.bloqueiodesbloqueioduplicata.mapper;

import br.com.nuclea.dupcanalparticipanteapi.adapter.bloqueiodesbloqueioduplicata.database.entity.BloqueiodesbloqueioduplicataOperationEntity;
import br.com.nuclea.dupcanalparticipanteapi.domain.common.model.Operation;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface BloqueiodesbloqueioduplicataOperationEntityMapper {
    BloqueiodesbloqueioduplicataOperationEntity toEntity(Operation operation);

    Operation toModel(BloqueiodesbloqueioduplicataOperationEntity entity);
}