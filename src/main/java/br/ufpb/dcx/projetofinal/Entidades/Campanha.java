package br.ufpb.dcx.projetofinal.Entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "campanha")
public class Campanha {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String estado;
    private String titulo;
    private String descricao;
    private Double meta;
    private String dataInicio;
    private String dataFinal;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Campanha(String estado, String titulo, String descricao, Double meta, String dataInicio, String dataFinal) {
        this.estado = estado;
        this.titulo = titulo;
        this.descricao = descricao;
        this.meta = meta;
        this.dataInicio = dataInicio;
        this.dataFinal = dataFinal;
    }
}
