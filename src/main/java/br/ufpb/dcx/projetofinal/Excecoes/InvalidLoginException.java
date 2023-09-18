package br.ufpb.dcx.projetofinal.Excecoes;

public class InvalidLoginException extends RuntimeException{
    private String title;
	private String details;

	public InvalidLoginException(String title, String details) {
		super();
		this.details = details;
		this.title = title;
	}

	public String gettitle() {
		return title;
	}

	public String getdetails() {
		return details;
	}
}
