// template path: adapter/#{feature_name_lower}/database/entity/#{feature_name_pascal}OperationEntity.java
package br.com.nuclea.dupcanalparticipanteapi.adapter.#{feature_name_lower}.database.entity;

import br.com.nuclea.dupcanalparticipanteapi.adapter.generic.database.OperationEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "dup_#{table_name}_op")
public class #{feature_name_pascal}OperationEntity extends OperationEntity {

}