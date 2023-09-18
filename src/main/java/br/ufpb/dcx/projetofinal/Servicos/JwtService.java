package br.ufpb.dcx.projetofinal.Servicos;

import java.security.Key;

import org.springframework.stereotype.Service;

import br.ufpb.dcx.projetofinal.Excecoes.TokenInvalidException;
import br.ufpb.dcx.projetofinal.Filtro.FiltroTokenJWT;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.util.Date;

@Service
public class JwtService {
    public static final Key TOKEN_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);


    public String generateToken(String email) {
        return Jwts.builder().setHeaderParam("typ", "JWT")
                .setSubject(email)
                .signWith(TOKEN_KEY, SignatureAlgorithm.HS512)
                .setExpiration(new Date(System.currentTimeMillis() + 3 * 60 * 2000)).compact();
    }

    public String getTokenSubject(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new TokenInvalidException("Erro Token Invalid", "Erro Token inspirado ou invalido. Tente novamente com outro Token!");
        }

        String token = authorizationHeader.substring(FiltroTokenJWT.TOKEN_INDEX);

        String subject = null;
        try {
            JwtParser parser = Jwts.parserBuilder().setSigningKey(TOKEN_KEY).build();
            subject = parser.parseClaimsJws(token).getBody().getSubject();
        } catch (SignatureException e) {
            throw new TokenInvalidException("Erro Token Invalid", "Erro Token inspirado ou invalido. Tente novamente com outro Token!");
        }
        return subject;
    }
}
