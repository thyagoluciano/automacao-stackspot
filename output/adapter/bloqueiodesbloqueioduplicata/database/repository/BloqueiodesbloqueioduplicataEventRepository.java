// template path: adapter/bloqueiodesbloqueioduplicata/database/repository/BloqueiodesbloqueioduplicataEventRepository.java
package br.com.nuclea.dupcanalparticipanteapi.adapter.bloqueiodesbloqueioduplicata.database.repository;

import br.com.nuclea.dupcanalparticipanteapi.adapter.bloqueiodesbloqueioduplicata.database.entity.BloqueiodesbloqueioduplicataEventEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BloqueiodesbloqueioduplicataEventRepository extends JpaRepository<BloqueiodesbloqueioduplicataEventEntity, UUID> {
    BloqueiodesbloqueioduplicataEventEntity findByOperationId(UUID operationId);
}