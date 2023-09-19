package br.ufpb.dcx.projetofinal.DTO;

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
}
