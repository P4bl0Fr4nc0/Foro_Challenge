package com.allura.Foro_Challenge.repository;

import com.allura.Foro_Challenge.topicos.Topico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicoRepository extends JpaRepository <Topico,Long> {
    boolean existsByTituloAndMensaje(String titulo, String mensaje);
    List<Topico> findByCursoAndAnio(String curso, int anio);
    boolean existsByTituloAndMensajeAndIdNot(String titulo, String mensaje, Long id);


}
