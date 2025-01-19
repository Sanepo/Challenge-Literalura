package com.challege.literalura.repository;

import com.challege.literalura.model.Autor;
import com.challege.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface LibroRepository extends JpaRepository<Libro,Long> {
    Libro findByTituloIgnoreCase(String nombreTitulo);

    @Query("SELECT l FROM Libro l WHERE LOWER(l.idioma) = LOWER(:idioma)")
    List<Libro> busquedaDeLibrosPorIdioma(String idioma);
}
