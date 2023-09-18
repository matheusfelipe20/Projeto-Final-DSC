package br.ufpb.dcx.projetofinal.Filtro;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.PrematureJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.GenericFilterBean;

import br.ufpb.dcx.projetofinal.Servicos.JwtService;

import java.io.IOException;

public class FiltroTokenJWT extends GenericFilterBean {
    public final static int TOKEN_INDEX = 7;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        if (!req.getMethod().equals("GET")) {
            String header = req.getHeader("Authorization");

            if (header == null || !header.startsWith("Bearer ")) {
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED,
                        "Token inexistente");
                return;
            }

            String token = header.substring(TOKEN_INDEX);
            try {
                Jwts.parser().setSigningKey(JwtService.TOKEN_KEY).parseClaimsJws(token).getBody();
            } catch (SignatureException | ExpiredJwtException | MalformedJwtException | PrematureJwtException
                     | UnsupportedJwtException | IllegalArgumentException e) {
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
