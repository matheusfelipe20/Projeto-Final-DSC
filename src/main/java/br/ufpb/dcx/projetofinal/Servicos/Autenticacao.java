package br.ufpb.dcx.projetofinal.Servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufpb.dcx.projetofinal.DTO.LoginUsuarioDTO;
import br.ufpb.dcx.projetofinal.DTO.ResponseLoginDTO;
import br.ufpb.dcx.projetofinal.Excecoes.InvalidLoginException;

@Service
public class Autenticacao {
    @Autowired
    private UsuarioServico usuarioServico;

    @Autowired
    private JwtService jwtService;

    public ResponseLoginDTO authenticate(LoginUsuarioDTO usuario) {
        if (!usuarioServico.ValidandoSenhaUsuario(usuario)) {
            throw new InvalidLoginException("Login Invalido", "Não foi possível realizar o login");
        }

        String token = jwtService.generateToken(usuario.getEmail());
        return new ResponseLoginDTO(token);
    }
}
