package br.ufpb.dcx.projetofinal.Servicos;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufpb.dcx.projetofinal.DTO.CampanhaRequestDTO;
import br.ufpb.dcx.projetofinal.DTO.CampanhaResponseDTO;
import br.ufpb.dcx.projetofinal.Entidades.Campanha;
import br.ufpb.dcx.projetofinal.Excecoes.CampaignAlreadyExistsException;
import br.ufpb.dcx.projetofinal.Excecoes.InvalidCampaignTitle;
import br.ufpb.dcx.projetofinal.Repositorio.CampanhaRepositorio;
import java.time.LocalDateTime; 

@Service
public class CampanhaServico {
    
    @Autowired
    CampanhaRepositorio campanhaRepositorio;

    @Autowired
    JwtService jwtService;

    public CampanhaResponseDTO CadastroCampanha(CampanhaRequestDTO campanhaRequestDTO) {

        Optional<Campanha> campanhaFound = campanhaRepositorio.findByTitulo(campanhaRequestDTO.getTitulo());

        if (campanhaFound.isPresent()) {
            throw new CampaignAlreadyExistsException("Erro usuário já existente", "Já existe um usuário com esse email");
        }

        String titulo = campanhaRequestDTO.getTitulo();

        if (titulo.length() >= 30) {
            throw new InvalidCampaignTitle("Erro título Invalido","O titulo da campnha deve ser curto! Verifique a quantidade de caracteres do título");
        }

        LocalDateTime dataInicial = LocalDateTime.now();
        LocalDateTime dataFinal = dataInicial.plusDays(30);
        String estado = "Disponivel";

        Campanha novCampanha = new Campanha(
                estado,
                titulo,
                campanhaRequestDTO.getDescricao(),
                campanhaRequestDTO.getMeta(),
                dataInicial,
                dataFinal
        );

        Campanha campanha = campanhaRepositorio.save(novCampanha);
        return CampanhaResponseDTO.from(campanha);
    }

}
