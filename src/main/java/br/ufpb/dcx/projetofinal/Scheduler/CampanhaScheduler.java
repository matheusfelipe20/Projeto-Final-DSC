package br.ufpb.dcx.projetofinal.Scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.ufpb.dcx.projetofinal.Servicos.CampanhaServico;

@Component
public class CampanhaScheduler {
    
    @Autowired
    private CampanhaServico campanhaServico;

    @Scheduled(cron = "0 * * * * *")
    public void verificarCampanhasParaEncerrar() {
        campanhaServico.AtualizarEstadoDasCampanhasEncerradas();
    }
}
