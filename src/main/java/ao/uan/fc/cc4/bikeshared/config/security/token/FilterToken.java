/*package ao.uan.fc.cc4.bikeshared.config.security.token;

import ao.uan.fc.cc4.bikeshared.bd.user.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Component
public class FilterToken implements Filter {

    @Autowired
    private Token token;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(httpRequest);

        // Ler o corpo da requisição
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(wrappedRequest.getInputStream(), StandardCharsets.UTF_8))) {
            char[] charBuffer = new char[128];
            int bytesRead = -1;
            while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                stringBuilder.append(charBuffer, 0, bytesRead);
            }
        } catch (IOException ex) {
            throw new ServletException("Não foi possível ler o corpo da requisição", ex);
        }

        String requestBody = stringBuilder.toString();

        // Ignorar o endpoint de addUser
        if (requestURI.endsWith("/api/user.wsdl") && requestBody.contains("UserRequest")) {
            System.out.println("OKKKsssssKKK => " + requestURI + ": " + requestBody+ "fala baixinho");
            chain.doFilter(request, response);
            System.out.println("okkkkkBenvindo");
            return;
        }

        String authorizationHeader = httpRequest.getHeader("Authorization");

        String email = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            Claims claims = token.extractClaims(jwt);
            email = claims.getSubject();
        }

        if (email != null && token.validateToken(jwt, email)) {
            httpRequest.setAttribute("email", email);
        } else {
            throw new ServletException("Token inválido ou expirado");
        }

        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

}
*/