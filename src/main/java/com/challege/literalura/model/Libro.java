package com.challege.literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String titulo;
    @ManyToOne
    private Autor autor;
    @Enumerated(EnumType.STRING)
    private Lenguaje idioma;
    private Integer descargas;

    public Libro(){

    }

    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
        this.idioma = Lenguaje.fromString(datosLibro.idioma().split(",")[0].trim());;
        this.descargas = datosLibro.numeroDescargas();
    }

    public String getTitulo() {
        return titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public Lenguaje getIdioma() {
        return idioma;
    }

    public Integer getDescargas() {
        return descargas;
    }

    @Override
    public String toString() {
        return
                "Titulo='" + titulo + '\n' +
                "Autor=" + (autor != null ? autor.getNombre() : "") + "\n" +
                "Lenguaje=" + idioma + "\n" +
                "Número de descargas" + descargas + "\n";
    }
}
