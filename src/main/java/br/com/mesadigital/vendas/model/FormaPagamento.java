package br.com.mesadigital.vendas.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "formaspagamento")
public class FormaPagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @ManyToMany(mappedBy = "formasPagamento")
    private Set<Pedido> pedidos;


    public FormaPagamento() {
    }

    public FormaPagamento(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
