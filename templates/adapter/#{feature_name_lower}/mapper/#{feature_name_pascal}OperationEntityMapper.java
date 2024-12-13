// template path: adapter/#{feature_name_lower}/mapper/#{feature_name_pascal}OperationEntityMapper.java
package br.com.nuclea.dupcanalparticipanteapi.adapter.#{feature_name_lower}.mapper;

import br.com.nuclea.dupcanalparticipanteapi.adapter.#{feature_name_lower}.database.entity.#{feature_name_pascal}OperationEntity;
import br.com.nuclea.dupcanalparticipanteapi.domain.common.model.Operation;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface #{feature_name_pascal}OperationEntityMapper {
    #{feature_name_pascal}OperationEntity toEntity(Operation operation);

    Operation toModel(#{feature_name_pascal}OperationEntity entity);
}