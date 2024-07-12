package com.allura.Foro_Challenge.security;

import com.allura.Foro_Challenge.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter  extends OncePerRequestFilter {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var authHeader = request.getHeader("Authorization");
        System.out.println("Authorization Header: " + authHeader);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            System.out.println("Validación de token no null");
            var token = authHeader.replace("Bearer ", "");
            var subject = tokenService.getSubject(token);
            System.out.println("Token Subject: " + subject);
            if (subject != null) {
                var usuario = usuarioRepository.findByLogin(subject);
                if (usuario != null) {
                    var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    System.out.println("Usuario no existe");
                }
            } else {
                System.out.println("Error token");
            }
        } else {
            System.out.println("Formatro incorrecto ");
        }
        filterChain.doFilter(request, response);




        /*

        Codigo alternativo
        var authHeader = request.getHeader("Authorization");

        if (authHeader != null ) {
            var token = authHeader.replace("Bearer ", "");
            var nombreUsuario = tokenService.getSubject(token);

            if (nombreUsuario != null) {
                var usuario = usuarioRepository.findByLogin(nombreUsuario);

                if (usuario != null) {
                    var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
        filterChain.doFilter(request, response);
    }
}
*/
}

}



