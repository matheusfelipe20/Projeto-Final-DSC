package br.ufpb.dcx.projetofinal.Repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ufpb.dcx.projetofinal.Entidades.Campanha;

public interface CampanhaRepositorio extends JpaRepository<Campanha, Long> {
    Optional<Campanha> findByTitulo(String titulo);
    boolean existsByTitulo(String titulo);
}
