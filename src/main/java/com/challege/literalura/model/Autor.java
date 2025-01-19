package com.challege.literalura.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.OptionalInt;

@Entity
@Table(name = "Autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String nombre;
    private Integer fechaNacimiento;
    private Integer fechaMuerte;
    @OneToMany(mappedBy = "autor",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Libro> libros;

    public Autor(){

    }
    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.fechaNacimiento = OptionalInt.of(datosAutor.fechaNacimiento()).orElse(0);
        this.fechaMuerte = OptionalInt.of(datosAutor.fechaMuerte()).orElse(0);
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getFechaNacimiento() {
        return fechaNacimiento;
    }

    public Integer getFechaMuerte() {
        return fechaMuerte;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    @Override
    public String toString() {
        return
                "Autor='" + nombre + '\'' +
                ", FechaNacimiento=" + fechaNacimiento +
                ", FechaFallecimiento=" + fechaMuerte +
                ", Libros= [ " + libros + " ]";
    }
}
