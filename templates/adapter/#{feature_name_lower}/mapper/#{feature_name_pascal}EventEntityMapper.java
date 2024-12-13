// template path: adapter/#{feature_name_lower}/mapper/#{feature_name_pascal}EventEntityMapper.java
package br.com.nuclea.dupcanalparticipanteapi.adapter.#{feature_name_lower}.mapper;

import br.com.nuclea.dupcanalparticipanteapi.adapter.#{feature_name_lower}.database.entity.#{feature_name_pascal}EventEntity;
import br.com.nuclea.dupcanalparticipanteapi.domain.generic.model.Event;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface #{feature_name_pascal}EventEntityMapper {
    #{feature_name_pascal}EventEntity toEntity(Event model);

    Event toModel(#{feature_name_pascal}EventEntity entity);
}