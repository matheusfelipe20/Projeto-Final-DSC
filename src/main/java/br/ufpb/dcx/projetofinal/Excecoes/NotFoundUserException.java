package br.ufpb.dcx.projetofinal.Excecoes;

public class NotFoundUserException extends RuntimeException{
    private String title;
	private String details;

	public NotFoundUserException(String title, String details) {
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