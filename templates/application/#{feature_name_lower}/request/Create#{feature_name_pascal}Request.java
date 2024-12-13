// template path: application/#{feature_name_lower}/request/Create#{feature_name_pascal}Request.java
package br.com.nuclea.dupcanalparticipanteapi.application.#{feature_name_lower}.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Create#{feature_name_pascal}Request {

    #{payload_fields}

    public String toJson() {
        try {
            return new ObjectMapper().findAndRegisterModules().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }
}