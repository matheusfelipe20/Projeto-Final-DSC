package br.ufpb.dcx.projetofinal.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CampanhaRequestDTO {
    
    @JsonProperty("titulo_campanha")
    @NotNull
    private String titulo;

    @JsonProperty("descricao_campanha")
    @NotNull
    private String descricao;

    @JsonProperty("meta_campanha")
    @NotNull
    private double meta;
}
