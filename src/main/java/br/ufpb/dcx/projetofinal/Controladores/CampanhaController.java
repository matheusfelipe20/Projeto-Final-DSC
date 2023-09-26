package br.ufpb.dcx.projetofinal.Controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
import br.ufpb.dcx.projetofinal.DTO.DoacaoRequestDTO;
import br.ufpb.dcx.projetofinal.DTO.DoacaoResponseDTO;
import br.ufpb.dcx.projetofinal.Servicos.CampanhaServico;
import jakarta.validation.Valid;

@RestController
@RequestMapping("")
public class CampanhaController {
    @Autowired
    CampanhaServico campanhaServico;

    //Cadastrar Campanha
    @PostMapping("/api/cadastro/campanha")
    @ResponseStatus(code=HttpStatus.CREATED)
    public CampanhaResponseDTO CadastroCampanha(@RequestBody @Valid CampanhaRequestDTO campanhaRequestDTO, @RequestHeader("Authorization") String header) {
        return campanhaServico.CadastroCampanha(campanhaRequestDTO, header);
    }

    //Editar Campanha já cadastrada
    @PatchMapping("/api/campanha/{titulo}")
    public CampanhaResponseDTO atualizarCampanha(@PathVariable String titulo, @RequestBody CampanhaRequestDTO campanhaRequestDTO, @RequestHeader("Authorization") String header) {
        return campanhaServico.AtualizarCampanha(titulo, campanhaRequestDTO, header);
    }

    //Deletar Campanha já cadastrada
    @DeleteMapping("/api/campanha/{titulo}")
    public CampanhaResponseDTO deletarCampanha(@PathVariable String titulo, @RequestHeader("Authorization") String authHeader) {
        return campanhaServico.DeletarCampanha(titulo, authHeader);
    }

    //Encerrar Campanha já cadastrada
    @PatchMapping("/api/campanha/{titulo}/Encerrar")
    public CampanhaResponseDTO EncerrarCampanha(@PathVariable String titulo, @RequestHeader("Authorization") String authHeader) {
        return campanhaServico.EncerrarCampanha(titulo, authHeader);
    }

    //Listar Campanha a partir do filtro de data cadastrada
    @GetMapping("/api/campanha/data")
    public ResponseEntity<List<CampanhaResponseDTO>> listarCampanhasAtivasPorDataCadastro() {
        List<CampanhaResponseDTO> campanhas = campanhaServico.listarCampanhasAtivasPorDataCadastro();
        return ResponseEntity.ok(campanhas);
    }

    //Listar campanha a partir do menor titulo
    @GetMapping("/api/campanha/titulo")
    public ResponseEntity<List<CampanhaResponseDTO>> listarCampanhasAtivasPorTitulo() {
        List<CampanhaResponseDTO> campanhas = campanhaServico.listarCampanhasAtivasPorTitulo();
        return ResponseEntity.ok(campanhas);
    }

    //Listar campanhas que foram encerrada
    @GetMapping("/api/campanha/encerrada")
    public ResponseEntity<List<CampanhaResponseDTO>> listarCampanhasEncerradasPorDataCadastro() {
        List<CampanhaResponseDTO> campanhas = campanhaServico.listarCampanhasEncerradasPorDataCadastro();
        return ResponseEntity.ok(campanhas);
    }

    //Listar campanhas que completaram a meta
    @GetMapping("/api/campanha/meta")
    public ResponseEntity<List<CampanhaResponseDTO>> listarCampanhasPorMetaConcluida() {
        List<CampanhaResponseDTO> campanhas = campanhaServico.listarCampanhasPorMetaConcluida();
        return ResponseEntity.ok(campanhas);
    }

    //Listar o histórico de doações realizadas
    @GetMapping("/api/campanha/doacoes")
    public ResponseEntity<List<DoacaoResponseDTO>> listarHistoricoDoacoes() {
        List<DoacaoResponseDTO> doacoes = campanhaServico.listarHistoricoDoacoes();
        return ResponseEntity.ok(doacoes);
    }

    //Realizar uma doação a uma campanha especifica
    @PatchMapping("/api/campanha/{nomeCampanha}/doacao")
    public CampanhaResponseDTO doacaoCampanha(@PathVariable String nomeCampanha, @RequestBody DoacaoRequestDTO doacaoRequestDTO, @RequestHeader("Authorization") String authHeader) {
        return campanhaServico.realizarDoacao(nomeCampanha, doacaoRequestDTO, authHeader);
    }




}
