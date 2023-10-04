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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

    @Operation(summary = "Recupera as informações das campanhas ativas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Retorna as campanhas que estão ativas, ordenando-as por data de cadastro.",
                    content = @Content)
    })
    @GetMapping("/api/campanha/data")
    public ResponseEntity<List<CampanhaResponseDTO>> listarCampanhasAtivasPorDataCadastro() {
        List<CampanhaResponseDTO> campanhas = campanhaServico.listarCampanhasAtivasPorDataCadastro();
        return ResponseEntity.ok(campanhas);
    }

    @Operation(summary = "Recupera as informações das campanhas ordenadas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Retorna as campanhas ordenadas a partir do menor título.",
                    content = @Content)
    })
    @GetMapping("/api/campanha/titulo")
    public ResponseEntity<List<CampanhaResponseDTO>> listarCampanhasAtivasPorTitulo() {
        List<CampanhaResponseDTO> campanhas = campanhaServico.listarCampanhasAtivasPorTitulo();
        return ResponseEntity.ok(campanhas);
    }

    @Operation(summary = "Recupera as informações das campanhas que foram ecerradas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Retorna as campanhas que foram encerradas.",
                    content = @Content)
    })
    @GetMapping("/api/campanha/encerrada")
    public ResponseEntity<List<CampanhaResponseDTO>> listarCampanhasEncerradasPorDataCadastro() {
        List<CampanhaResponseDTO> campanhas = campanhaServico.listarCampanhasEncerradasPorDataCadastro();
        return ResponseEntity.ok(campanhas);
    }

    @Operation(summary = "Recupera as informações das campanhas que completaram a meta.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Retorna as campanhas que arrecadaram o valor estabelecido na meta.",
                    content = @Content)
    })
    @GetMapping("/api/campanha/meta")
    public ResponseEntity<List<CampanhaResponseDTO>> listarCampanhasPorMetaConcluida() {
        List<CampanhaResponseDTO> campanhas = campanhaServico.listarCampanhasPorMetaConcluida();
        return ResponseEntity.ok(campanhas);
    }

    @Operation(summary = "Recupera as informações do histórico de doações.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Retorna o histórico completo de doações realizadas.",
                    content = @Content)
    })
    @GetMapping("/api/campanha/doacoes")
    public ResponseEntity<List<DoacaoResponseDTO>> listarHistoricoDoacoes() {
        List<DoacaoResponseDTO> doacoes = campanhaServico.listarHistoricoDoacoes();
        return ResponseEntity.ok(doacoes);
    }

    @Operation(summary = "Realizar uma doação em uma campanha",
            description = "É preciso saber o título da campanha para realizar a doação. " +
            "Apenas usuários autenticados podem realizar uma doação",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a doação atualizada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Usuário não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "400", description = "Valor invalido para a doação, deve ser maior que 0.0")
    })
    @PatchMapping("/api/campanha/{nomeCampanha}/doacao")
    public CampanhaResponseDTO doacaoCampanha(@PathVariable String nomeCampanha, @RequestBody DoacaoRequestDTO doacaoRequestDTO, @RequestHeader("Authorization") String authHeader) {
        return campanhaServico.realizarDoacao(nomeCampanha, doacaoRequestDTO, authHeader);
    }




}
