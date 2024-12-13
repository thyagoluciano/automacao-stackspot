// template path: adapter/bloqueiodesbloqueioduplicata/mapper/BloqueiodesbloqueioduplicataEventEntityMapper.java
package br.com.nuclea.dupcanalparticipanteapi.adapter.bloqueiodesbloqueioduplicata.mapper;

import br.com.nuclea.dupcanalparticipanteapi.adapter.bloqueiodesbloqueioduplicata.database.entity.BloqueiodesbloqueioduplicataEventEntity;
import br.com.nuclea.dupcanalparticipanteapi.domain.generic.model.Event;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface BloqueiodesbloqueioduplicataEventEntityMapper {
    BloqueiodesbloqueioduplicataEventEntity toEntity(Event model);

    Event toModel(BloqueiodesbloqueioduplicataEventEntity entity);
}