package br.ufpb.dcx.projetofinal.DTO;

import lombok.Builder;

@Builder
public class ProblemaDetalhado {
	private int status;
	private String tipo;
	private String titulo;
	private String detalhe;

	public ProblemaDetalhado() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProblemaDetalhado(int status, String tipo, String titulo, String detalhe) {
		super();
		this.status = status;
		this.tipo = tipo;
		this.titulo = titulo;
		this.detalhe = detalhe;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String gettipo() {
		return tipo;
	}

	public void settipo(String tipo) {
		this.tipo = tipo;
	}

	public String gettitulo() {
		return titulo;
	}

	public void settitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getdetalhe() {
		return detalhe;
	}

	public void setdetalhe(String detalhe) {
		this.detalhe = detalhe;
	}
}