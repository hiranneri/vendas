package br.com.mesadigital.vendas.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String codigoBarras;

    @Column(nullable = false)
    private String nome;

    private String marca;

    @Column(nullable = false)
    private BigDecimal precoVenda;

    private BigDecimal precoCusto;

    @Column(nullable = false)
    private boolean controlarEstoque;

    private float estoqueAtual;

    @Column(nullable = false)
    private String validade;

    @OneToMany(mappedBy = "produto")
    private List<ItemPedido> itemPedido = new ArrayList<>();

    private boolean status;

    public Produto() {
    }

    public Produto(Long id, String codigoBarras, String marca, BigDecimal precoVenda, BigDecimal precoCusto,
                   boolean controlarEstoque, float estoqueAtual, float limiteEstoque, String validade, boolean status) {
        this.id = id;
        this.codigoBarras = codigoBarras;
        this.marca = marca;
        this.precoVenda = precoVenda;
        this.precoCusto = precoCusto;
        this.controlarEstoque = controlarEstoque;
        this.estoqueAtual = estoqueAtual;
        this.validade = validade;
        this.status = status;
    }

    @PrePersist
    public void prePersist(){
        setStatus(true);

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public BigDecimal getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(BigDecimal precoVenda) {
        this.precoVenda = precoVenda;
    }

    public BigDecimal getPrecoCusto() {
        return precoCusto;
    }

    public void setPrecoCusto(BigDecimal precoCusto) {
        this.precoCusto = precoCusto;
    }

    public boolean isControlarEstoque() {
        return controlarEstoque;
    }

    public void setControlarEstoque(boolean controlarEstoque) {
        this.controlarEstoque = controlarEstoque;
    }

    public float getEstoqueAtual() {
        return estoqueAtual;
    }

    public void setEstoqueAtual(float estoqueAtual) {
        this.estoqueAtual = estoqueAtual;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getValidade() {
        return validade;
    }

    public void setValidade(String validade) {
        this.validade = validade;
    }

    public List<ItemPedido> getItemPedido() {
        return itemPedido;
    }

    public void setItemPedido(List<ItemPedido> itemPedido) {
        this.itemPedido = itemPedido;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return controlarEstoque == produto.controlarEstoque &&
                Float.compare(estoqueAtual, produto.estoqueAtual) == 0 &&
                status == produto.status && Objects.equals(id, produto.id) &&
                Objects.equals(codigoBarras, produto.codigoBarras) &&
                Objects.equals(marca, produto.marca) &&
                Objects.equals(precoVenda, produto.precoVenda) &&
                Objects.equals(precoCusto, produto.precoCusto) &&
                Objects.equals(validade, produto.validade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, codigoBarras, marca, precoVenda, precoCusto, controlarEstoque, estoqueAtual, validade, status);
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", codigoBarras='" + codigoBarras + '\'' +
                ", marca='" + marca + '\'' +
                ", precoVenda=" + precoVenda +
                ", precoCusto=" + precoCusto +
                ", controlarEstoque=" + controlarEstoque +
                ", estoqueAtual=" + estoqueAtual +
                ", validade='" + validade + '\'' +
                ", status=" + status +
                '}';
    }
}
