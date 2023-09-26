package br.ufpb.dcx.projetofinal.Entidades;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class Doacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuarioDoador;

    @ManyToOne
    @JoinColumn(name = "campanha_id")
    private Campanha campanha;

    private Double valorDoado;
    private String dataDoacao;

    public Doacao(Usuario usuarioDoador, Campanha campanha, Double valorDoado, String dataDoacao) {
        this.usuarioDoador = usuarioDoador;
        this.campanha = campanha;
        this.valorDoado = valorDoado;
        this.dataDoacao = dataDoacao;
    }
}
