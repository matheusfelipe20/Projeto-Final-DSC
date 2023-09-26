package br.ufpb.dcx.projetofinal.Repositorio;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import br.ufpb.dcx.projetofinal.Entidades.Campanha;
import br.ufpb.dcx.projetofinal.Entidades.Doacao;
import br.ufpb.dcx.projetofinal.Entidades.Usuario;



public interface DoacaoRepositorio extends JpaRepository<Doacao, Long> {
    List<Doacao> findByUsuarioDoador(Usuario usuario);
    List<Doacao> findByCampanha(Campanha campanha);
}
