package br.ufpb.dcx.projetofinal.Servicos;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufpb.dcx.projetofinal.DTO.CampanhaRequestDTO;
import br.ufpb.dcx.projetofinal.DTO.CampanhaResponseDTO;
import br.ufpb.dcx.projetofinal.Entidades.Campanha;
import br.ufpb.dcx.projetofinal.Entidades.RoleUser;
import br.ufpb.dcx.projetofinal.Entidades.Usuario;
import br.ufpb.dcx.projetofinal.Excecoes.CampaignAlreadyExistsException;
import br.ufpb.dcx.projetofinal.Excecoes.InvalidCampaignDescription;
import br.ufpb.dcx.projetofinal.Excecoes.InvalidCampaignMeta;
import br.ufpb.dcx.projetofinal.Excecoes.InvalidCampaignTitle;
import br.ufpb.dcx.projetofinal.Excecoes.NotAuthorizedException;
import br.ufpb.dcx.projetofinal.Excecoes.NotFoundUserException;
import br.ufpb.dcx.projetofinal.Repositorio.CampanhaRepositorio;
import br.ufpb.dcx.projetofinal.Repositorio.UsuarioRepositorio;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter; 

@Service
public class CampanhaServico {
    
    @Autowired
    CampanhaRepositorio campanhaRepositorio;

    @Autowired
    UsuarioRepositorio usuarioRepositorio;

    @Autowired
    JwtService jwtService;

    public CampanhaResponseDTO CadastroCampanha(CampanhaRequestDTO campanhaRequestDTO, String authHeader) {
        Usuario usuarioLogado = this.getUser(jwtService.getTokenSubject(authHeader));

        if (usuarioLogado.getRoleUser().equals(RoleUser.ADMIN) || usuarioLogado.getRoleUser().equals(RoleUser.USER)) {
            Optional<Campanha> campanhaFound = campanhaRepositorio.findByTitulo(campanhaRequestDTO.getTitulo());

            if (campanhaFound.isPresent()) {
                throw new CampaignAlreadyExistsException("Erro Campanha já existente", "Já existe uma campanha com esse mesmo título");
            }

            String titulo = campanhaRequestDTO.getTitulo();
            String descricao = campanhaRequestDTO.getDescricao();
            Double meta = campanhaRequestDTO.getMeta();

            if (titulo.length() > 100) {
                throw new InvalidCampaignTitle("Erro título Invalido","Limite de caracteres do título ultrapassado. Por favor verifique a quantidade máxima de 100 caracteres.");
            }

            if (descricao.length() > 1000) {
                throw new InvalidCampaignDescription("Erro Descrição Invalida","Limite de caracteres da descrição ultrapassado. Por favor verifique a quantidade máxima de 1000 caracteres.");
            }

            if (meta == 0.0) {
                throw new InvalidCampaignMeta("Erro Valor da Meta Invalido", "O valor da meta não pode ser ZERO | 0.0");
            }

            LocalDateTime dataInicial = LocalDateTime.now();
            LocalDateTime dataFinal = dataInicial.plusDays(30);
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dataInicioFormatada = dataInicial.format(formato);
            String dataFinalFormatada = dataFinal.format(formato);
            String estado = "Ativa";

            Campanha novCampanha = new Campanha(
                    estado,
                    titulo,
                    descricao,
                    meta,
                    dataInicioFormatada,
                    dataFinalFormatada
            );

            novCampanha.setUsuario(usuarioLogado);

            Campanha campanha = campanhaRepositorio.save(novCampanha);
            return CampanhaResponseDTO.from(campanha);
        }
        else throw new NotAuthorizedException("Não Autorizado","Esse usuário não tem permissão para essa função");
    }

    private Usuario getUser(String email) {
        Optional<Usuario> usuarioEncontrado = usuarioRepositorio.findByEmail(email);
        if (!usuarioEncontrado.isEmpty()) {
            return usuarioEncontrado.get();
        }
        throw new NotFoundUserException("Usuário não encontrado","Não existe um usuário com esse email");
    }
}
