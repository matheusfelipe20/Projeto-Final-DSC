package br.ufpb.dcx.projetofinal.DTO;

import java.util.List;

import br.ufpb.dcx.projetofinal.Entidades.Campanha;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CampanhaResponseDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    String estado;
    String titulo;
    String descricao;
    Double meta;
    String dataInicio;
    String dataFinal;

    public static CampanhaResponseDTO from(Campanha campanha) {
        return CampanhaResponseDTO.builder()
                .id(campanha.getId())
                .estado(campanha.getEstado())
                .titulo(campanha.getTitulo())
                .descricao(campanha.getDescricao())
                .meta(campanha.getMeta())
                .dataInicio(campanha.getDataInicio())
                .dataFinal(campanha.getDataFinal())
                .build();
    }

    public static List<CampanhaResponseDTO> fromAll(List<Campanha> listOfCampanhas) {
        return listOfCampanhas.stream().map(CampanhaResponseDTO::from).toList();
    }
}
