package com.challege.literalura.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;

@Entity
@Table(name = "Autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String nombre;
    private Integer fechaNacimiento;
    private Integer fechaMuerte;
    @OneToMany(mappedBy = "autor",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Libro> libros;

    public Autor(){

    }
    public Autor(DatosLibro datosLibro) {
        this.nombre = datosLibro.autor().getFirst().nombre();
        //this.fechaNacimiento = OptionalInt.of(datosLibro.autor().getFirst().fechaNacimiento()).orElse(0);
        //this.fechaMuerte = OptionalInt.of(datosLibro.autor().getFirst().fechaMuerte()).orElse(0);
        this.fechaNacimiento = datosLibro.autor().getFirst().fechaNacimiento();
        this.fechaMuerte = datosLibro.autor().getFirst().fechaMuerte();
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

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {
        return  "********* AUTOR ********* \n" +
                "Nombre= " + nombre + '\n' +
                "FechaNacimiento= " + fechaNacimiento + "\n"+
                "FechaFallecimiento= " + fechaMuerte + "\n"+
                "Libros= [ " + libros.stream()
                        .map(Libro::getTitulo)
                        .collect(Collectors.joining(", ")) + " ]" +"\n"
                +"************************ \n";
    }
}
