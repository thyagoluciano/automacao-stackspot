// template path: adapter/bloqueiodesbloqueioduplicata/database/repository/BloqueiodesbloqueioduplicataOperationRepository.java
package br.com.nuclea.dupcanalparticipanteapi.adapter.bloqueiodesbloqueioduplicata.database.repository;

import br.com.nuclea.dupcanalparticipanteapi.adapter.bloqueiodesbloqueioduplicata.database.entity.BloqueiodesbloqueioduplicataOperationEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BloqueiodesbloqueioduplicataOperationRepository extends JpaRepository<BloqueiodesbloqueioduplicataOperationEntity, UUID> {
}