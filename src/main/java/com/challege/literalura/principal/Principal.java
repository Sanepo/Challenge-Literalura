package com.challege.literalura.principal;

import com.challege.literalura.model.*;
import com.challege.literalura.repository.AutorRepository;
import com.challege.literalura.repository.LibroRepository;
import com.challege.literalura.service.ConsumoAPI;
import com.challege.literalura.service.ConvierteDatos;

import java.util.*;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConvierteDatos conversor = new ConvierteDatos();
    private List<DatosAutor> datosAutor = new ArrayList<>();
    private AutorRepository repositorioAutor;
    private LibroRepository repositorioLibro;
    private List<Autor> autores;
    private List<Libro> libros;
    //private List<Serie> series;
    //private Optional<Serie> serieBuscada;

    public Principal(AutorRepository autorRepository, LibroRepository libroRepository) {
        this.repositorioAutor = autorRepository;
        this.repositorioLibro = libroRepository;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    ***********************************************
                            !BIENVENIDO A LITERALURA¡
                            
                    1 - Buscar libros por título
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idíoma
                    
                    0 - Salir
                    ***********************************************
                    
                    Elija la opción a través de su número:
                    """;
            System.out.println(menu);
            try {
                opcion = teclado.nextInt();
                teclado.nextLine();
            } catch (Exception e) {
                System.out.println("Elección incorrecta, por favor seleccionar una opción válida");
                opcion = -1;
                teclado.nextLine();
            }

            switch (opcion) {
                case 1:
                    buscarLibrosPorTitulo();
                    break;
                case 2:
                    mostrarLibrosRegistrados();
                    break;
                case 3:
                    mostrarAutoresRegistrados();
                    break;
                case 4:
                    mostrarAutoresVivosPorAno();
                    break;
                case 5:
                    mostrarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    opcion = -1;
            }
        }

    }

    private void buscarLibrosPorTitulo() {
        System.out.println("Escribe el nombre del libro que deseas buscar");
        var nombreLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreLibro.replace(" ", "%20"));
        var datosGeneralesLibro = conversor.obtenerDatos(json, DatosGenerales.class);
        Optional<DatosLibro> tituloBuscado = datosGeneralesLibro.resultadoLibros().stream()
                .filter(l -> l.titulo().toLowerCase().contains(nombreLibro.toLowerCase()))
                .findFirst();
        if(tituloBuscado.isPresent()){
            guardarLibro(tituloBuscado.get());
        } else {
            guardarLibro(null);
        }

    }
    public void guardarLibro(DatosLibro libroBuscado){
        if (libroBuscado == null) {
            System.out.println("No hemos encontrado un libro con ese titulo.");
            return;
        }

        Autor autor = repositorioAutor.findByNombreIgnoreCase(libroBuscado.autor().getFirst().nombre())
                .orElseGet(() -> {
                    Autor nuevoAutor = new Autor(libroBuscado);
                    repositorioAutor.save(nuevoAutor);
                    return nuevoAutor;
                });

        Libro libroExistente = repositorioLibro.findByTituloIgnoreCase(libroBuscado.titulo());

        if(libroExistente != null){
            System.out.println("El libro ya se encuentra en la base de datos \n");
            System.out.println(libroExistente);
            return;
        }

        Libro libroNuevo = new Libro(libroBuscado);
        libroNuevo.setAutor(autor);

        if (autor.getLibros() == null) {
            autor.setLibros(new ArrayList<>());
        }

        autor.getLibros().add(libroNuevo);

        repositorioLibro.save(libroNuevo);
        System.out.println(libroNuevo);
    }
    private void mostrarLibrosRegistrados() {
        libros = repositorioLibro.findAll();

        libros.stream()
                .sorted(Comparator.comparing(Libro::getIdioma))
                .forEach(System.out::println);
    }

    private void mostrarAutoresRegistrados() {
        autores = repositorioAutor.findAll();
        autores.stream()
                .sorted(Comparator.comparing(Autor::getNombre))
                .forEach(System.out::println);
    }
    private void mostrarAutoresVivosPorAno() {
        System.out.println("Seleccione un año de su autor: ");
        var fechaSeleccionada = 0;
        try {
            fechaSeleccionada = teclado.nextInt();
            teclado.nextLine();
        } catch (Exception e) {
            teclado.nextLine();
            System.out.println("Seleccione una fecha valida");
            return;
        }
        List<Autor> autorEnFecha = repositorioAutor.consultarAutoresVivosEnFechaSeleccionada(fechaSeleccionada);
        if (autorEnFecha.isEmpty()) {
            System.out.println("No hay autores de ese año en la base de datos.");
        } else {
            autorEnFecha.stream()
                    .sorted(Comparator.comparing(Autor::getNombre))
                    .forEach(System.out::println);
        }


    }

    private void mostrarLibrosPorIdioma() {
        System.out.println("Seleccione un idioma con el cual quiere filtrar su busqueda: ");
        String idiomaSeleccionado;
        try {
            idiomaSeleccionado = teclado.nextLine();
        } catch (Exception e) {
            teclado.nextLine();
            System.out.println("Seleccione una opción valida");
            return;
        }

        List<Libro> libroPorIdioma = repositorioLibro.busquedaDeLibrosPorIdioma(idiomaSeleccionado);

        if (libroPorIdioma.isEmpty()) {
            System.out.println("No hay resultados con esa opción.");
        } else {
            libroPorIdioma.stream()
                    .sorted(Comparator.comparing(Libro::getIdioma))
                    .forEach(System.out::println);
        }
    }
}
