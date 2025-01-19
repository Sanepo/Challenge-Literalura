package com.challege.literalura.repository;

import com.challege.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor,Long> {
    Optional<Autor> findByNombreIgnoreCase(String nombreAutor);

    @Query("SELECT a FROM Autor a WHERE a.fechaMuerte >= :fechaSeleccionada AND a.fechaNacimiento <= :fechaSeleccionada ")
    List<Autor> consultarAutoresVivosEnFechaSeleccionada(int fechaSeleccionada);

}
