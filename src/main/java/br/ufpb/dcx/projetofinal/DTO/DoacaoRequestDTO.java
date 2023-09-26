package br.ufpb.dcx.projetofinal.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoacaoRequestDTO {

    @JsonProperty("valor_doacao")
    @NotNull
    private Double valor;
}
