package com.challege.literalura.repository;

import com.challege.literalura.model.Autor;
import com.challege.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro,Long> {
    Libro findByTituloIgnoreCase(String nombreTitulo);
}
