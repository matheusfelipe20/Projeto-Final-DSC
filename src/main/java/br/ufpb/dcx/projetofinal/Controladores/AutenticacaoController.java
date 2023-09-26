package br.ufpb.dcx.projetofinal.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.ufpb.dcx.projetofinal.DTO.LoginUsuarioDTO;
import br.ufpb.dcx.projetofinal.DTO.ResponseLoginDTO;
import br.ufpb.dcx.projetofinal.Servicos.Autenticacao;

@RestController
public class AutenticacaoController {
    @Autowired
    private Autenticacao autenticacao;

    //Realizar login de um usuario já cadastrado
    @PostMapping("api/login")
    @ResponseStatus(code= HttpStatus.OK)
    public ResponseLoginDTO auth(@RequestBody LoginUsuarioDTO user) {
        return autenticacao.authenticate(user);
    }
}
