package com.allura.Foro_Challenge.controller;

import com.allura.Foro_Challenge.security.DatosJWTtoken;
import com.allura.Foro_Challenge.security.TokenService;
import com.allura.Foro_Challenge.usuario.DatosAutentificacionUsuario;
import com.allura.Foro_Challenge.usuario.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity autentificarUsuario(@RequestBody @Valid DatosAutentificacionUsuario datosAutentificacionUsuario){
        Authentication authenticationToken =new UsernamePasswordAuthenticationToken(datosAutentificacionUsuario.login()
                ,datosAutentificacionUsuario.clave());
        var usuarioAutenticado = authenticationManager.authenticate(authenticationToken);
        var JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
        System.out.println(JWTtoken);
        return ResponseEntity.ok(new DatosJWTtoken(JWTtoken));
    }
}
