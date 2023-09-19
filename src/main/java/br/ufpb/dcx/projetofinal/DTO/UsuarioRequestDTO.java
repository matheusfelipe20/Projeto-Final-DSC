package br.ufpb.dcx.projetofinal.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import br.ufpb.dcx.projetofinal.Entidades.ClasseUser;
import br.ufpb.dcx.projetofinal.Entidades.RoleUser;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsuarioRequestDTO {
    
    @JsonProperty("email")
    @NotNull
    private String email;

    @JsonProperty("senha")
    @NotNull
    private String senha;

    @JsonProperty("nome_usuario")
    @NotNull
    private String nome;

    @JsonProperty("telefone_usuario")
    @NotNull
    private String telefone;

    @JsonProperty("classe_usuario")
    @NotNull
    private ClasseUser classe;

    @JsonProperty("documento_usuario")
    @NotNull
    private String documento;

    @JsonProperty("roleUser")
    @NotNull
    private RoleUser roleUser;
}