package br.ufpb.dcx.projetofinal.DTO;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import br.ufpb.dcx.projetofinal.Entidades.RoleUser;
import br.ufpb.dcx.projetofinal.Entidades.Usuario;

@Setter
@Getter
@Builder
public class UsuarioResponseDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    String email;
    String senha;
    String nome;
    String telefone;
    String classe;
    String documento;

    RoleUser roleUser;

    public static UsuarioResponseDTO from(Usuario usuario) {
        return UsuarioResponseDTO.builder()
                .id(usuario.getId())
                .senha(usuario.getSenha())
                .email(usuario.getEmail())
                .nome(usuario.getNome())
                .telefone(usuario.getTelefone())
                .classe(usuario.getClasse())
                .documento(usuario.getDocumento())
                .roleUser(usuario.getRoleUser())
                .build();
    }

    public static List<UsuarioResponseDTO> fromAll(List<Usuario> listOfUser) {
        return listOfUser.stream().map(UsuarioResponseDTO::from).toList();
    }
}