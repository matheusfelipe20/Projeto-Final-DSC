package br.ufpb.dcx.projetofinal.Servicos;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufpb.dcx.projetofinal.DTO.LoginUsuarioDTO;
import br.ufpb.dcx.projetofinal.DTO.UsuarioRequestDTO;
import br.ufpb.dcx.projetofinal.DTO.UsuarioResponseDTO;
import br.ufpb.dcx.projetofinal.Entidades.RoleUser;
import br.ufpb.dcx.projetofinal.Entidades.Usuario;
import br.ufpb.dcx.projetofinal.Excecoes.NotAuthorizedException;
import br.ufpb.dcx.projetofinal.Excecoes.NotFoundUserException;
import br.ufpb.dcx.projetofinal.Excecoes.UserAlreadyExistsException;
import br.ufpb.dcx.projetofinal.Repositorio.UsuarioRepositorio;

@Service
public class UsuarioServico {
    
    @Autowired
    UsuarioRepositorio usuarioRepositorio;

    @Autowired
    JwtService jwtService;

    public UsuarioResponseDTO CadastroUsuario(UsuarioRequestDTO usuarioRequestDTO) {

        Optional<Usuario> userFound = usuarioRepositorio.findByEmail(usuarioRequestDTO.getEmail());

        if (userFound.isPresent()) {
            throw new UserAlreadyExistsException("Erro usuário já existente", "Já existe um usuário com esse email");
        }

        Usuario novUsuario = new Usuario(
                usuarioRequestDTO.getEmail(),
                usuarioRequestDTO.getSenha(),
                usuarioRequestDTO.getNome(),
                usuarioRequestDTO.getTelefone(),
                usuarioRequestDTO.getClasse(),
                usuarioRequestDTO.getDocumento(),
                usuarioRequestDTO.getRoleUser()
        );
        Usuario usuario = usuarioRepositorio.save(novUsuario);
        return UsuarioResponseDTO.from(usuario);
    }

    private Usuario getUser(String email) {
        Optional<Usuario> usuarioEncontrado = usuarioRepositorio.findByEmail(email);
        if (!usuarioEncontrado.isEmpty()) {
            return usuarioEncontrado.get();
        }
        throw new NotFoundUserException("Usuário não encontrado","Não existe um usuário com esse email");
    }

    public UsuarioResponseDTO getUserByEmail(String email, String authHeader) {
        Optional<Usuario> usuarioEncontrado = usuarioRepositorio.findByEmail(email);
        if (!usuarioEncontrado.isEmpty() && UsuarioTemPermissao(authHeader, email)) {
            return UsuarioResponseDTO.from(usuarioEncontrado.get());
        }
        throw new NotAuthorizedException("Erro de Autorização","Esse usuário não tem permissão para acessar esse conteúdo");
    }

    public boolean ValidandoSenhaUsuario(LoginUsuarioDTO usuario) {
        Optional<Usuario> usuarioEncontrado = usuarioRepositorio.findByEmail(usuario.getEmail());
        if (usuarioEncontrado.isPresent() && usuarioEncontrado.get().getSenha().equals(usuario.getSenha()))
            return true;
        return false;
    }

    private boolean UsuarioTemPermissao(String authorizationHeader, String email) {
        String subject = jwtService.getTokenSubject(authorizationHeader);
        Optional<Usuario> usuarioEncontrado = usuarioRepositorio.findByEmail(subject);
        return usuarioEncontrado.isPresent() && usuarioEncontrado.get().getEmail().equals(email);
    }

    public UsuarioResponseDTO RemoverUsuario(String email, String authHeader) {
        Usuario userToBeDeleted = this.getUser(email);
        Usuario userLogged = this.getUser(jwtService.getTokenSubject(authHeader));
        if(userLogged.getRoleUser().equals(RoleUser.ADMIN) ||
                userLogged.getEmail().equals(userToBeDeleted.getEmail()))
            usuarioRepositorio.delete(userToBeDeleted);
        else  throw new NotAuthorizedException("Erro de Autorização","Esse usuário não tem permissão para acessar esse conteúdo");
        return UsuarioResponseDTO.from(userToBeDeleted);
    }
}
