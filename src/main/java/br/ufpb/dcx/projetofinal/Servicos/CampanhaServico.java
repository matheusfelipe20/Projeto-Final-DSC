package br.ufpb.dcx.projetofinal.Servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufpb.dcx.projetofinal.Repositorio.CampanhaRepositorio;

@Service
public class CampanhaServico {
    
    @Autowired
    CampanhaRepositorio campanhaRepositorio;

    @Autowired
    JwtService jwtService;

}
