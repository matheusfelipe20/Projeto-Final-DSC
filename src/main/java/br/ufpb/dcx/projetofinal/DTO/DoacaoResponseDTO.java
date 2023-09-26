package br.ufpb.dcx.projetofinal.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.ufpb.dcx.projetofinal.Entidades.Doacao;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class DoacaoResponseDTO {

    private long id;

    @JsonProperty("nome_campanha")
    private String nomeCampanha;

    @JsonProperty("valor_doacao")
    private Double valor;

    @JsonProperty("data_doacao")
    private String dataDoacao;


    public static DoacaoResponseDTO from(Doacao doacao) {
        return DoacaoResponseDTO.builder()
                .id(doacao.getId())
                .nomeCampanha(doacao.getCampanha().getTitulo())
                .valor(doacao.getValorDoado())
                .dataDoacao(doacao.getDataDoacao())
                .build();
    }
}