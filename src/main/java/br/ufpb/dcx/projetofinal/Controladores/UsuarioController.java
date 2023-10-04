package br.ufpb.dcx.projetofinal.Controladores;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.ufpb.dcx.projetofinal.DTO.UsuarioRequestDTO;
import br.ufpb.dcx.projetofinal.DTO.UsuarioResponseDTO;
import br.ufpb.dcx.projetofinal.Servicos.UsuarioServico;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("")
public class UsuarioController {
    @Autowired
    UsuarioServico usuarioServico;

    @RequestMapping(value = "/api/usuarios/perfil/{email}", method = RequestMethod.GET, produces="application/json")
    @Operation(summary = "Recupera o perfil do usuário",
            description = "É possível recuperar os dados do usuário que está logado no sistema",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna os dados do usuário", content = @Content),
            @ApiResponse(responseCode = "403", description = "Usuário não tem permissão para acessar este recurso")
    })
    @GetMapping("/api/usuarios/perfil/{email}")
    @ResponseStatus(code= HttpStatus.OK)
    public UsuarioResponseDTO getUser(@PathVariable String email, @RequestHeader("Authorization") String header) {
        return usuarioServico.getUserByEmail(email, header);
    }

    //Editar informações do perfil de usuario
    @PatchMapping("/api/usuarios/perfil/{email}")
    public ResponseEntity<String> atualizarUsuario(@PathVariable String email, @RequestBody UsuarioRequestDTO usuarioRequestDTO, @RequestHeader("Authorization") String header) {
        return usuarioServico.AtualizarUsuario(email, usuarioRequestDTO.getNome(),usuarioRequestDTO.getEmail(), header);
    }

    @Operation(summary = "Cadastra o usuário no sistema",
            description = "Informe um email válido e único, para então " +
                    "o cadastro ser realizado com sucesso."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Credenciais incorretas, verifique seus dados")
    })
    @PostMapping("/api/cadastro")
    @ResponseStatus(code=HttpStatus.CREATED)
    public UsuarioResponseDTO CadastroUsuario(@RequestBody @Valid UsuarioRequestDTO userRequestDTO) {
        return usuarioServico.CadastroUsuario(userRequestDTO);
    }

    //Deletar usuario já cadastrado
    @DeleteMapping("/api/usuarios/perfil/{email}")
    @ResponseStatus(code=HttpStatus.OK)
    public UsuarioResponseDTO RemoverUsuario(@PathVariable String email, @RequestHeader("Authorization") String header) {
        return usuarioServico.RemoverUsuario(email, header);
    }
}
