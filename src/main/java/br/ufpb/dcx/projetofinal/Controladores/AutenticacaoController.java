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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


@RestController
public class AutenticacaoController {
    @Autowired
    private Autenticacao autenticacao;

    @PostMapping("/api/login")
    @Operation(summary = "Autentica o usuário previamente cadastrado.",
            description = "É preciso informar o e-mail de um usuário cadastrado no sistema e a senha " +
                    "correspondente para que um usuário seja devidamente autenticado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário logado/autenticado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Login inválido, não foi possível realizar o login")
    })

    @ResponseStatus(code= HttpStatus.OK)
    public ResponseLoginDTO auth(@RequestBody LoginUsuarioDTO user) {
        return autenticacao.authenticate(user);
    }
}
