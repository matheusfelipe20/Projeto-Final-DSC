package br.ufpb.dcx.projetofinal.Servicos;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufpb.dcx.projetofinal.DTO.CampanhaRequestDTO;
import br.ufpb.dcx.projetofinal.DTO.CampanhaResponseDTO;
import br.ufpb.dcx.projetofinal.DTO.DoacaoRequestDTO;
import br.ufpb.dcx.projetofinal.DTO.DoacaoResponseDTO;
import br.ufpb.dcx.projetofinal.Entidades.Campanha;
import br.ufpb.dcx.projetofinal.Entidades.Doacao;
import br.ufpb.dcx.projetofinal.Entidades.RoleUser;
import br.ufpb.dcx.projetofinal.Entidades.Usuario;
import br.ufpb.dcx.projetofinal.Excecoes.CampaignAlreadyExistsException;
import br.ufpb.dcx.projetofinal.Excecoes.CampanhaNotFoundException;
import br.ufpb.dcx.projetofinal.Excecoes.InvalidCampaignDescription;
import br.ufpb.dcx.projetofinal.Excecoes.InvalidCampaignMeta;
import br.ufpb.dcx.projetofinal.Excecoes.InvalidCampaignTitle;
import br.ufpb.dcx.projetofinal.Excecoes.InvalidDonationAmountException;
import br.ufpb.dcx.projetofinal.Excecoes.NotAuthorizedException;
import br.ufpb.dcx.projetofinal.Excecoes.NotFoundUserException;
import br.ufpb.dcx.projetofinal.Repositorio.CampanhaRepositorio;
import br.ufpb.dcx.projetofinal.Repositorio.DoacaoRepositorio;
import br.ufpb.dcx.projetofinal.Repositorio.UsuarioRepositorio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    @Autowired
    DoacaoRepositorio doacaoRepositorio;

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
            Double saldo = 0.0;

            Campanha novCampanha = new Campanha(
                    estado,
                    titulo,
                    descricao,
                    meta,
                    dataInicioFormatada,
                    dataFinalFormatada,
                    saldo
            );

            novCampanha.setUsuario(usuarioLogado);

            Campanha campanha = campanhaRepositorio.save(novCampanha);
            return CampanhaResponseDTO.from(campanha);
        }
        else throw new NotAuthorizedException("Não Autorizado","Esse usuário não tem permissão para essa função");
    }

    public CampanhaResponseDTO AtualizarCampanha(String titulo, CampanhaRequestDTO campanhaRequestDTO, String authHeader) {
        Usuario usuarioLogado = this.getUser(jwtService.getTokenSubject(authHeader));
    
        Optional<Campanha> campanhaOptional = campanhaRepositorio.findByTitulo(titulo);
    
        if (campanhaOptional.isPresent()) {
            Campanha campanha = campanhaOptional.get();
            if (campanha.getUsuario().equals(usuarioLogado)) {
                if ("Ativa".equals(campanha.getEstado())) {
                    String novoTitulo = campanhaRequestDTO.getTitulo();
                    
                    Optional<Campanha> campanhaComNovoTitulo = campanhaRepositorio.findByTitulo(novoTitulo);
                    if (campanhaComNovoTitulo.isPresent() && !campanhaComNovoTitulo.get().equals(campanha)) {
                        throw new CampaignAlreadyExistsException("Título já existe", "Já existe uma campanha com o título fornecido.");
                    }
                    
                    campanha.setTitulo(novoTitulo);
                    campanha.setDescricao(campanhaRequestDTO.getDescricao());
                    campanha.setMeta(campanhaRequestDTO.getMeta());
    
                    Campanha campanhaAtualizada = campanhaRepositorio.save(campanha);
                    return CampanhaResponseDTO.from(campanhaAtualizada);
                } else {
                    throw new NotAuthorizedException("Não Autorizado", "Essa campanha está Encerrada");
                }
            } else {
                throw new NotAuthorizedException("Não Autorizado", "Você não tem permissão para atualizar esta campanha.");
            }
        } else {
            throw new CampanhaNotFoundException("Campanha não encontrada", "A campanha com o título fornecido não foi encontrada.");
        }
    }

    public CampanhaResponseDTO DeletarCampanha(String titulo, String authHeader) {
        Usuario usuarioLogado = this.getUser(jwtService.getTokenSubject(authHeader));

        Optional<Campanha> campanhaOptional = campanhaRepositorio.findByTitulo(titulo);

        if (campanhaOptional.isPresent()) {
            Campanha campanha = campanhaOptional.get();

            if (campanha.getUsuario().equals(usuarioLogado)) {
                if (campanha.getValorArrecadado() > 0) {
                    String estadoDeletado = "Encerrada";
                    campanha.setEstado(estadoDeletado);
                    Campanha campanhaAtualizada = campanhaRepositorio.save(campanha);
                    return CampanhaResponseDTO.from(campanhaAtualizada);
                }
                campanhaRepositorio.delete(campanha);
                return CampanhaResponseDTO.from(campanha);
            } else {
                throw new NotAuthorizedException("Não Autorizado", "Você não tem permissão para deletar esta campanha.");
            }
        } else {
            throw new CampanhaNotFoundException("Campanha não encontrada", "A campanha com o título fornecido não foi encontrada.");
        }
    }

    public CampanhaResponseDTO EncerrarCampanha(String titulo, String authHeader) {
        Usuario usuarioLogado = this.getUser(jwtService.getTokenSubject(authHeader));

        Optional<Campanha> campanhaOptional = campanhaRepositorio.findByTitulo(titulo);

        if (campanhaOptional.isPresent()) {
            Campanha campanha = campanhaOptional.get();

            if (campanha.getUsuario().equals(usuarioLogado)) {
                campanha.setEstado("Encerrada");
                Campanha campanhaAtualizada = campanhaRepositorio.save(campanha);

                return CampanhaResponseDTO.from(campanhaAtualizada);
            } else {
                throw new NotAuthorizedException("Não Autorizado", "Você não tem permissão para encerrar esta campanha.");
            }
        } else {
            throw new CampanhaNotFoundException("Campanha não encontrada", "A campanha com o título fornecido não foi encontrada.");
        }
    }

    public void AtualizarEstadoDasCampanhasEncerradas() {
        List<Campanha> campanhas = campanhaRepositorio.findByEstado("Ativa");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dataAtualTexto = dateFormat.format(new Date());
        
        for (Campanha campanha : campanhas) {
            String dataFinalTexto = campanha.getDataFinal();
            try {
                Date dataFinal = dateFormat.parse(dataFinalTexto);
                Date dataAtual = dateFormat.parse(dataAtualTexto);
                
                if (dataAtual.after(dataFinal)) {
                    campanha.setEstado("Encerrada");
                    campanhaRepositorio.save(campanha);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public List<CampanhaResponseDTO> listarCampanhasAtivasPorTitulo() {
        List<Campanha> campanhasAtivasPorTitulo = campanhaRepositorio.findByEstado("Ativa");
        campanhasAtivasPorTitulo.sort(Comparator.comparing(Campanha::getTitulo));
        return campanhasAtivasPorTitulo.stream()
            .map(CampanhaResponseDTO::from)
            .collect(Collectors.toList());
    }
    
    public List<CampanhaResponseDTO> listarCampanhasAtivasPorDataCadastro() {
        List<Campanha> campanhasAtivasPorDataCadastro = campanhaRepositorio.findByEstado("Ativa");
        campanhasAtivasPorDataCadastro.sort(Comparator.comparing(Campanha::getDataInicio));
        return campanhasAtivasPorDataCadastro.stream()
            .map(CampanhaResponseDTO::from)
            .collect(Collectors.toList());
    }
    
    public List<CampanhaResponseDTO> listarCampanhasEncerradasPorDataCadastro() {
        List<Campanha> campanhasEncerradasPorDataCadastro = campanhaRepositorio.findByEstado("Encerrada");
        campanhasEncerradasPorDataCadastro.sort(Comparator.comparing(Campanha::getDataInicio));
        return campanhasEncerradasPorDataCadastro.stream()
            .map(CampanhaResponseDTO::from)
            .collect(Collectors.toList());
    }

    public List<CampanhaResponseDTO> listarCampanhasPorMetaConcluida() {
        List<Campanha> campanhas = campanhaRepositorio.findAll();
        return campanhas.stream()
            .filter(campanha -> campanha.getValorArrecadado() >= campanha.getMeta())
            .map(CampanhaResponseDTO::from)
            .collect(Collectors.toList());
    }

    public List<DoacaoResponseDTO> listarHistoricoDoacoes() {
        List<Doacao> doacoes = doacaoRepositorio.findAll();

        //IMPORTANTE: Esse trecho organizar as doações em ordem de datas crescentes
        List<Doacao> doacoesOrdenadasPorData = doacoes.stream()
            .sorted(Comparator.comparing(Doacao::getDataDoacao))
            .collect(Collectors.toList());
        
        return doacoesOrdenadasPorData.stream()
            .map(DoacaoResponseDTO::from)
            .collect(Collectors.toList());
    }

    private Usuario getUser(String email) {
        Optional<Usuario> usuarioEncontrado = usuarioRepositorio.findByEmail(email);
        if (!usuarioEncontrado.isEmpty()) {
            return usuarioEncontrado.get();
        }
        throw new NotFoundUserException("Usuário não encontrado","Não existe um usuário com esse email");
    }

    public CampanhaResponseDTO realizarDoacao(String nomeCampanha, DoacaoRequestDTO doacaoRequestDTO, String authHeader) {
        Usuario usuarioDoador = this.getUser(jwtService.getTokenSubject(authHeader));
        List<Campanha> campanhas = campanhaRepositorio.findByTituloAndEstado(nomeCampanha, "Ativa");

        if (campanhas.isEmpty()) {
            throw new CampanhaNotFoundException("Campanha não encontrada ou não está ativa", "A campanha com o título fornecido não foi encontrada ou não está ativa.");
        }

        Campanha campanha = campanhas.get(0);

        double valor = doacaoRequestDTO.getValor();
        if (valor <= 0.0) {
            throw new InvalidDonationAmountException("Valor de doação inválido", "O valor da doação deve ser maior que zero.");
        }

        LocalDateTime dataDoacao = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataDoacaoFormatada = dataDoacao.format(formato);

        Doacao novDoacao = new Doacao(
            usuarioDoador,
            campanha,
            valor,
            dataDoacaoFormatada
        );
        doacaoRepositorio.save(novDoacao);

        double novoSaldo = campanha.getValorArrecadado() + valor;
        campanha.setValorArrecadado(novoSaldo);

        campanhaRepositorio.save(campanha);

        return CampanhaResponseDTO.from(campanha);
    }

    
    

}
