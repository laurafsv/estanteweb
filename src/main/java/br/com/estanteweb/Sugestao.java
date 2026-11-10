package br.com.estanteweb;

import java.util.Map;

public class Sugestao {
    
    private long id;
    private String nomeCliente;
    private String livroSugerido;

    public Sugestao() {
    }

    public Sugestao(String nomeCliente, String livroSugerido) {
        this.nomeCliente = nomeCliente;
        this.livroSugerido = livroSugerido;
    }

    public static Sugestao deMap(Map<String, Object> linha) {
        Sugestao sugestao = new Sugestao();
        if (linha.get("id") != null) {
            sugestao.setId(((Number) linha.get("id")).longValue());
        }
        if (linha.get("nome_cliente") != null) {
            sugestao.setNomeCliente((String) linha.get("nome_cliente"));
        } else if (linha.get("nomeCliente") != null) {
            sugestao.setNomeCliente((String) linha.get("nomeCliente"));
        }
        if (linha.get("livro_sugerido") != null) {
            sugestao.setLivroSugerido((String) linha.get("livro_sugerido"));
        } else if (linha.get("livroSugerido") != null) {
            sugestao.setLivroSugerido((String) linha.get("livroSugerido"));
        }
        return sugestao;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getLivroSugerido() {
        return livroSugerido;
    }

    public void setLivroSugerido(String livroSugerido) {
        this.livroSugerido = livroSugerido;
    }
}
