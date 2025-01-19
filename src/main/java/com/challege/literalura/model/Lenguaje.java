package com.challege.literalura.model;

public enum Lenguaje {
    Español("es", "español"),
    Inglés("en", "inglés"),
    Francés("fr", "francés"),
    Portugués("pt", "portugués");


    private String idiomaAPIGutendex;
    private String idiomaEspanol;

    Lenguaje(String idiomaAPIGutendex,String idiomaEspanol){
        this.idiomaAPIGutendex = idiomaAPIGutendex;
        this.idiomaEspanol = idiomaEspanol;
    }

    //Este metodo tranforma el valor tipo String(que viene de la API) y la transforma a un valor tipo Categoria para poderlo asociar al ENUM
    public static Lenguaje fromString(String text) {
        for  (Lenguaje idioma : Lenguaje.values()) {
            if (idioma.idiomaAPIGutendex.equalsIgnoreCase(text)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("Ningún lenguaje encontrado: " + text);
    }

    //Este metodo tranforma el valor tipo String(que viene de la API) y la transforma a un valor tipo String para poderlo asociar al ENUM
    public static Lenguaje fromEspanol(String text) {
        for  (Lenguaje idioma : Lenguaje.values()) {
            if (idioma.idiomaEspanol.equalsIgnoreCase(text)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("Ningún lenguaje encontrado: " + text);
    }

}
