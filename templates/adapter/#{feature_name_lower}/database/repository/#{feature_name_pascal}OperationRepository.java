// template path: adapter/#{feature_name_lower}/database/repository/#{feature_name_pascal}OperationRepository.java
package br.com.nuclea.dupcanalparticipanteapi.adapter.#{feature_name_lower}.database.repository;

import br.com.nuclea.dupcanalparticipanteapi.adapter.#{feature_name_lower}.database.entity.#{feature_name_pascal}OperationEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface #{feature_name_pascal}OperationRepository extends JpaRepository<#{feature_name_pascal}OperationEntity, UUID> {
}