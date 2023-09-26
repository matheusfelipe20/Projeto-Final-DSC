package br.ufpb.dcx.projetofinal.Excecoes;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.ufpb.dcx.projetofinal.DTO.ProblemaDetalhado;

@RestControllerAdvice
public class HandlerExceptions {

	private static String DISCIPLINE_URI = "http://servidor:8080/api";

	@ExceptionHandler(TokenInvalidException.class)
	public ResponseEntity<ProblemaDetalhado> TokenInvalidException(TokenInvalidException pyr) {
		ProblemaDetalhado problem = ProblemaDetalhado
				.builder()
				.status(HttpStatus.BAD_REQUEST.value())
				.tipo(DISCIPLINE_URI)
				.titulo(pyr.gettitle())
				.detalhe(pyr.getdetails())
				.build();
		return new ResponseEntity<>(problem, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidLoginException.class)
	public ResponseEntity<ProblemaDetalhado> InvalidLoginException(InvalidLoginException pyr) {
		ProblemaDetalhado problem = ProblemaDetalhado
				.builder()
				.status(HttpStatus.BAD_REQUEST.value())
				.tipo(DISCIPLINE_URI)
				.titulo(pyr.gettitle())
				.detalhe(pyr.getdetails())
				.build();
		return new ResponseEntity<>(problem, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<ProblemaDetalhado> UserAlreadyExistsException(UserAlreadyExistsException pyr) {
		ProblemaDetalhado problem = ProblemaDetalhado
				.builder()
				.status(HttpStatus.BAD_REQUEST.value())
				.tipo(DISCIPLINE_URI)
				.titulo(pyr.gettitle())
				.detalhe(pyr.getdetails())
				.build();
		return new ResponseEntity<>(problem, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NotFoundUserException.class)
	public ResponseEntity<ProblemaDetalhado> NotFoundUserException(NotFoundUserException pyr) {
		ProblemaDetalhado problem = ProblemaDetalhado
				.builder()
				.status(HttpStatus.BAD_REQUEST.value())
				.tipo(DISCIPLINE_URI)
				.titulo(pyr.gettitle())
				.detalhe(pyr.getdetails())
				.build();
		return new ResponseEntity<>(problem, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(NotAuthorizedException.class)
	public ResponseEntity<ProblemaDetalhado> NotAuthorizedException(NotAuthorizedException pyr) {
		ProblemaDetalhado problem = ProblemaDetalhado
				.builder()
				.status(HttpStatus.BAD_REQUEST.value())
				.tipo(DISCIPLINE_URI)
				.titulo(pyr.gettitle())
				.detalhe(pyr.getdetails())
				.build();
		return new ResponseEntity<>(problem, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DuplicateEmailException.class)
	public ResponseEntity<ProblemaDetalhado> DuplicateEmailException(DuplicateEmailException pyr) {
		ProblemaDetalhado problem = ProblemaDetalhado
				.builder()
				.status(HttpStatus.BAD_REQUEST.value())
				.tipo(DISCIPLINE_URI)
				.titulo(pyr.gettitle())
				.detalhe(pyr.getdetails())
				.build();
		return new ResponseEntity<>(problem, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidDocumentException.class)
	public ResponseEntity<ProblemaDetalhado> InvalidDocumentException(InvalidDocumentException pyr) {
		ProblemaDetalhado problem = ProblemaDetalhado
				.builder()
				.status(HttpStatus.BAD_REQUEST.value())
				.tipo(DISCIPLINE_URI)
				.titulo(pyr.gettitle())
				.detalhe(pyr.getdetails())
				.build();
		return new ResponseEntity<>(problem, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(CampaignAlreadyExistsException.class)
	public ResponseEntity<ProblemaDetalhado> CampanhaAlreadyExistsException(CampaignAlreadyExistsException pyr) {
		ProblemaDetalhado problem = ProblemaDetalhado
				.builder()
				.status(HttpStatus.BAD_REQUEST.value())
				.tipo(DISCIPLINE_URI)
				.titulo(pyr.gettitle())
				.detalhe(pyr.getdetails())
				.build();
		return new ResponseEntity<>(problem, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InvalidCampaignTitle.class)
	public ResponseEntity<ProblemaDetalhado> InvalidCampaignTitle(InvalidCampaignTitle pyr) {
		ProblemaDetalhado problem = ProblemaDetalhado
				.builder()
				.status(HttpStatus.BAD_REQUEST.value())
				.tipo(DISCIPLINE_URI)
				.titulo(pyr.gettitle())
				.detalhe(pyr.getdetails())
				.build();
		return new ResponseEntity<>(problem, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidCampaignDescription.class)
	public ResponseEntity<ProblemaDetalhado> InvalidCampaignDescription(InvalidCampaignDescription pyr) {
		ProblemaDetalhado problem = ProblemaDetalhado
				.builder()
				.status(HttpStatus.BAD_REQUEST.value())
				.tipo(DISCIPLINE_URI)
				.titulo(pyr.gettitle())
				.detalhe(pyr.getdetails())
				.build();
		return new ResponseEntity<>(problem, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidCampaignMeta.class)
	public ResponseEntity<ProblemaDetalhado> InvalidCampaignMeta(InvalidCampaignMeta pyr) {
		ProblemaDetalhado problem = ProblemaDetalhado
				.builder()
				.status(HttpStatus.BAD_REQUEST.value())
				.tipo(DISCIPLINE_URI)
				.titulo(pyr.gettitle())
				.detalhe(pyr.getdetails())
				.build();
		return new ResponseEntity<>(problem, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidEmailException.class)
	public ResponseEntity<ProblemaDetalhado> InvalidEmailException(InvalidEmailException pyr) {
		ProblemaDetalhado problem = ProblemaDetalhado
				.builder()
				.status(HttpStatus.BAD_REQUEST.value())
				.tipo(DISCIPLINE_URI)
				.titulo(pyr.gettitle())
				.detalhe(pyr.getdetails())
				.build();
		return new ResponseEntity<>(problem, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(CampanhaNotFoundException.class)
	public ResponseEntity<ProblemaDetalhado> CampanhaNotFoundException(CampanhaNotFoundException pyr) {
		ProblemaDetalhado problem = ProblemaDetalhado
				.builder()
				.status(HttpStatus.BAD_REQUEST.value())
				.tipo(DISCIPLINE_URI)
				.titulo(pyr.gettitle())
				.detalhe(pyr.getdetails())
				.build();
		return new ResponseEntity<>(problem, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InvalidDonationAmountException.class)
	public ResponseEntity<ProblemaDetalhado> InvalidDonationAmountException(InvalidDonationAmountException pyr) {
		ProblemaDetalhado problem = ProblemaDetalhado
				.builder()
				.status(HttpStatus.BAD_REQUEST.value())
				.tipo(DISCIPLINE_URI)
				.titulo(pyr.gettitle())
				.detalhe(pyr.getdetails())
				.build();
		return new ResponseEntity<>(problem, HttpStatus.BAD_REQUEST);
	}
}