// template path: adapter/#{feature_name_lower}/database/entity/#{feature_name_pascal}EventEntity.java
package br.com.nuclea.dupcanalparticipanteapi.adapter.#{feature_name_lower}.database.entity;

import br.com.nuclea.dupcanalparticipanteapi.adapter.generic.database.EventEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Table(name = "dup_#{table_name}_evt")
@Entity
public class #{feature_name_pascal}EventEntity extends EventEntity {
}