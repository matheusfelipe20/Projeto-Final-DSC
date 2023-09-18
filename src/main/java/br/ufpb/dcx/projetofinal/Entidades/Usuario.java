package br.ufpb.dcx.projetofinal.Entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private String senha;
    private String nome;
    private String telefone;
    private String classe;
    private String documento;
    private RoleUser roleUser;

    public Usuario (String email, String senha, String nome, String telefone, String classe, String documento, RoleUser roleUser) {
        this.email = email;
        this.senha = senha;
        this.nome = nome;
        this.telefone = telefone;
        this.classe = classe;
        this.documento = documento;
        this.roleUser = roleUser;
    }
}
