package com.allura.Foro_Challenge.controller;


import com.allura.Foro_Challenge.topicos.Topico;
import com.allura.Foro_Challenge.repository.TopicoRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
@Validated
public class TopicoController {

    private TopicoRepository topicoRepository;

    //Crear topico
    @PostMapping
    public ResponseEntity<?> crearTopico(@Valid @RequestBody Topico topico) {
        if (topicoRepository.existsByTituloAndMensaje(topico.getTitulo(), topico.getMensaje())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ya existe topico con el titulo ó mensaje, favor de revisarlo");
        }
        Topico nuevoTopico = topicoRepository.save(topico);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoTopico);
    }

    //Listar topicos existentes
    @GetMapping
    public ResponseEntity<List<Topico>> listarTopicos() {
        List<Topico> topicos = topicoRepository.findAll();
        return ResponseEntity.ok(topicos);
    }

    //Obtener topico especifico
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerTopico(@PathVariable Long id) {
        Optional<Topico> optionalTopico = topicoRepository.findById(id);
        if (optionalTopico.isPresent()) {
            Topico topico = optionalTopico.get();
            return ResponseEntity.ok(topico);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe topico buscado " + id);
        }
    }

    //Actualizar topico
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarTopico(
            @PathVariable Long id,
            @Valid @RequestBody Topico topicoActualizado) {
        Optional<Topico> optionalTopico = topicoRepository.findById(id);

        if (optionalTopico.isPresent()) {
            if (!topicoRepository.existsByTituloAndMensajeAndIdNot(
                    topicoActualizado.getTitulo(),
                    topicoActualizado.getMensaje(),
                    id)) {
               //Actualizar datos
                Topico topicoExistente = optionalTopico.get();
                topicoExistente.setTitulo(topicoActualizado.getTitulo());
                topicoExistente.setMensaje(topicoActualizado.getMensaje());
                topicoExistente.setStatus(topicoActualizado.getStatus());
                topicoExistente.setAutor(topicoActualizado.getAutor());
                topicoExistente.setCurso(topicoActualizado.getCurso());
                topicoExistente.setAnio(topicoActualizado.getAnio());

                Topico actualizacionTopico = topicoRepository.save(topicoExistente);
                return ResponseEntity.ok(actualizacionTopico);
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Ya existe el topico, favor de buscarlo y revisarlo");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //Eliminar Topico
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarTopico(@PathVariable Long id) {
        Optional<Topico> optionalTopico = topicoRepository.findById(id);
        if (optionalTopico.isPresent()) {
            topicoRepository.deleteById(id);
            return ResponseEntity.ok("El topico: " + id + " se ha elimiunado.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Topico no encontrado, verifica ID");
        }
    }
}
