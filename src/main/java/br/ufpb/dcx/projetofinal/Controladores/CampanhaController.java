package br.ufpb.dcx.projetofinal.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.ufpb.dcx.projetofinal.DTO.CampanhaRequestDTO;
import br.ufpb.dcx.projetofinal.DTO.CampanhaResponseDTO;
import br.ufpb.dcx.projetofinal.Servicos.CampanhaServico;
import jakarta.validation.Valid;

@RestController
@RequestMapping("")
public class CampanhaController {
    @Autowired
    CampanhaServico campanhaServico;

    @PostMapping("/api/cadastro/campanha")
    @ResponseStatus(code=HttpStatus.CREATED)
    public CampanhaResponseDTO CadastroCampanha(@RequestBody @Valid CampanhaRequestDTO campanhaRequestDTO, @RequestHeader("Authorization") String header) {
        return campanhaServico.CadastroCampanha(campanhaRequestDTO, header);
    }

    @PatchMapping("/api/campanha/{titulo}")
    public CampanhaResponseDTO atualizarCampanha(@PathVariable String titulo, @RequestBody CampanhaRequestDTO campanhaRequestDTO, @RequestHeader("Authorization") String header) {
        return campanhaServico.AtualizarCampanha(titulo, campanhaRequestDTO, header);
    }

    @DeleteMapping("/api/campanha/{titulo}")
    public CampanhaResponseDTO deletarCampanha(@PathVariable String titulo, @RequestHeader("Authorization") String authHeader) {
        return campanhaServico.DeletarCampanha(titulo, authHeader);
    }

    @PatchMapping("api/campanha/Encerrar/{titulo}")
    public CampanhaResponseDTO EncerrarCampanha(@PathVariable String titulo, @RequestHeader("Authorization") String authHeader) {
        return campanhaServico.EncerrarCampanha(titulo, authHeader);
    }
}
