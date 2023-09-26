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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.ufpb.dcx.projetofinal.DTO.UsuarioRequestDTO;
import br.ufpb.dcx.projetofinal.DTO.UsuarioResponseDTO;
import br.ufpb.dcx.projetofinal.Servicos.UsuarioServico;
import jakarta.validation.Valid;

@RestController
@RequestMapping("")
public class UsuarioController {
    @Autowired
    UsuarioServico usuarioServico;

    //Listar o perfil de informação daquele usuario
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

    //Cadastrar novo usuario
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
