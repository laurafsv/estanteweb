package br.com.estanteweb;

import java.util.Map;

public class Livro {

    private long id;
    private String titulo;
    private String autor;
    private int ano;
    private double preco;

    public Livro() {
    }

    public static Livro deMap(Map<String, Object> linha) {
        Livro livro = new Livro();
        if (linha.get("id") != null) {
            livro.setId(((Number) linha.get("id")).longValue());
        }
        if (linha.get("titulo") != null) {
            livro.setTitulo((String) linha.get("titulo"));
        }
        if (linha.get("autor") != null) {
            livro.setAutor((String) linha.get("autor"));
        }
        if (linha.get("ano") != null) {
            livro.setAno(((Number) linha.get("ano")).intValue());
        }
        if (linha.get("preco") != null) {
            livro.setPreco(((Number) linha.get("preco")).doubleValue());
        }
        return livro;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
}