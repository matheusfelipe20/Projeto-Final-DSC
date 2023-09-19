package br.ufpb.dcx.projetofinal.Servicos;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.ufpb.dcx.projetofinal.DTO.LoginUsuarioDTO;
import br.ufpb.dcx.projetofinal.DTO.UsuarioRequestDTO;
import br.ufpb.dcx.projetofinal.DTO.UsuarioResponseDTO;
import br.ufpb.dcx.projetofinal.Entidades.ClasseUser;
import br.ufpb.dcx.projetofinal.Entidades.RoleUser;
import br.ufpb.dcx.projetofinal.Entidades.Usuario;
import br.ufpb.dcx.projetofinal.Excecoes.InvalidDocumentException;
import br.ufpb.dcx.projetofinal.Excecoes.NotAuthorizedException;
import br.ufpb.dcx.projetofinal.Excecoes.NotFoundUserException;
import br.ufpb.dcx.projetofinal.Excecoes.UserAlreadyExistsException;
import br.ufpb.dcx.projetofinal.Repositorio.UsuarioRepositorio;

@Service
public class UsuarioServico {
    
    @Autowired
    UsuarioRepositorio usuarioRepositorio;

    @Autowired
    JwtService jwtService;

    public UsuarioResponseDTO CadastroUsuario(UsuarioRequestDTO usuarioRequestDTO) {

        Optional<Usuario> userFound = usuarioRepositorio.findByEmail(usuarioRequestDTO.getEmail());

        if (userFound.isPresent()) {
            throw new UserAlreadyExistsException("Erro usuário já existente", "Já existe um usuário com esse email");
        }

        ClasseUser classe = usuarioRequestDTO.getClasse();
        String documento = usuarioRequestDTO.getDocumento();

        documento = documento.replaceAll("[^0-9]", "");

        if (classe == ClasseUser.PessoaFisica) {
            if (!isValidCPF(documento)) {
                throw new InvalidDocumentException("Erro documento inválido", "CPF inválido - Verifique a quantidade de dígitos");
            }
            documento = formatCPF(documento);
        } else {
            if (!isValidCNPJ(documento)) {
                throw new InvalidDocumentException("Erro documento inválido", "CNPJ inválido - Verifique a quantidade de dígitos");
            }
            documento = formatCNPJ(documento);
        }

        Usuario novUsuario = new Usuario(
                usuarioRequestDTO.getEmail(),
                usuarioRequestDTO.getSenha(),
                usuarioRequestDTO.getNome(),
                usuarioRequestDTO.getTelefone(),
                usuarioRequestDTO.getClasse(),
                documento,
                usuarioRequestDTO.getRoleUser()
        );

        Usuario usuario = usuarioRepositorio.save(novUsuario);
        return UsuarioResponseDTO.from(usuario);
    }

    private boolean isValidCPF(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", "");
        return cpf.length() == 11;
    }

    private boolean isValidCNPJ(String cnpj) {
        cnpj = cnpj.replaceAll("[^0-9]", "");
    
        return cnpj.length() == 14;
    }

    public static String formatCPF(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", "");
    
        if (cpf.length() != 11) {
            throw new IllegalArgumentException("CPF inválido");
        }
    
        return String.format("%s.%s.%s-%s",
                cpf.substring(0, 3),
                cpf.substring(3, 6),
                cpf.substring(6, 9),
                cpf.substring(9));
    }

    public static String formatCNPJ(String cnpj) {
        cnpj = cnpj.replaceAll("[^0-9]", "");
    
        if (cnpj.length() != 14) {
            throw new IllegalArgumentException("CNPJ inválido");
        }
    
        return String.format("%s.%s.%s/%s-%s",
                cnpj.substring(0, 2),
                cnpj.substring(2, 5),
                cnpj.substring(5, 8),
                cnpj.substring(8, 12),
                cnpj.substring(12));
    }
    

    private Usuario getUser(String email) {
        Optional<Usuario> usuarioEncontrado = usuarioRepositorio.findByEmail(email);
        if (!usuarioEncontrado.isEmpty()) {
            return usuarioEncontrado.get();
        }
        throw new NotFoundUserException("Usuário não encontrado","Não existe um usuário com esse email");
    }

    public UsuarioResponseDTO getUserByEmail(String email, String authHeader) {
        Optional<Usuario> usuarioEncontrado = usuarioRepositorio.findByEmail(email);
        if (!usuarioEncontrado.isEmpty() && UsuarioTemPermissao(authHeader, email)) {
            return UsuarioResponseDTO.from(usuarioEncontrado.get());
        }
        throw new NotAuthorizedException("Erro de Autorização","Esse usuário não tem permissão para acessar esse conteúdo");
    }

    public boolean ValidandoSenhaUsuario(LoginUsuarioDTO usuario) {
        Optional<Usuario> usuarioEncontrado = usuarioRepositorio.findByEmail(usuario.getEmail());
        if (usuarioEncontrado.isPresent() && usuarioEncontrado.get().getSenha().equals(usuario.getSenha()))
            return true;
        return false;
    }

    private boolean UsuarioTemPermissao(String authorizationHeader, String email) {
        String subject = jwtService.getTokenSubject(authorizationHeader);
        Optional<Usuario> usuarioEncontrado = usuarioRepositorio.findByEmail(subject);
        return usuarioEncontrado.isPresent() && usuarioEncontrado.get().getEmail().equals(email);
    }

    public ResponseEntity<String> AtualizarUsuario (String email, String novoNome, String authHeader) {
        Usuario usuario = usuarioRepositorio.findByEmail(email)
            .orElseThrow(() -> new NotFoundUserException("Erro de requisição - Email não encontrado", "Não foi possível realizar a ação, pois o email não está correto"));

        Usuario usuarioLogado = this.getUser(jwtService.getTokenSubject(authHeader));
        if(usuarioLogado.getRoleUser().equals(RoleUser.USER) || usuarioLogado.getRoleUser().equals(RoleUser.ADMIN)) {
            // if (usuarioRepositorio.existsByEmail(novoEmail)) {
            //     throw new DuplicateEmailException("Erro de Requisição", "O email de usuário já está em uso");
            // }
            //usuario.setEmail(novoEmail);

            usuario.setNome(novoNome);

            usuarioRepositorio.save(usuario);

            return ResponseEntity.ok("usuario atualizada com sucesso.");
        }
        else  throw new NotAuthorizedException("Não Autorizado","Esse usuário não tem permissão para essa função");
    }

    public UsuarioResponseDTO RemoverUsuario(String email, String authHeader) {
        Usuario usuarioLogado = this.getUser(jwtService.getTokenSubject(authHeader));
    
        if (usuarioLogado.getEmail().equals(email)) {
            usuarioRepositorio.delete(usuarioLogado);
            return UsuarioResponseDTO.from(usuarioLogado);
        } else {
            throw new NotAuthorizedException("Erro de Autorização","Esse usuário não tem permissão para acessar esse conteúdo");
        }
    }
}
