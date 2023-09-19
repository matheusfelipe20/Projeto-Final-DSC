package br.ufpb.dcx.projetofinal.Repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufpb.dcx.projetofinal.Entidades.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
}
