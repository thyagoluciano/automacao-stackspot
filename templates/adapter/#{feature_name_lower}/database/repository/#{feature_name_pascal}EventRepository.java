// template path: adapter/#{feature_name_lower}/database/repository/#{feature_name_pascal}EventRepository.java
package br.com.nuclea.dupcanalparticipanteapi.adapter.#{feature_name_lower}.database.repository;

import br.com.nuclea.dupcanalparticipanteapi.adapter.#{feature_name_lower}.database.entity.#{feature_name_pascal}EventEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface #{feature_name_pascal}EventRepository extends JpaRepository<#{feature_name_pascal}EventEntity, UUID> {
    #{feature_name_pascal}EventEntity findByOperationId(UUID operationId);
}