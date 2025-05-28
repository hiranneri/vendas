package br.com.mesadigital.vendas.model;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long clienteId;

    @Column(nullable = false)
    private LocalDateTime dataHora;

    @Column(nullable = false)
    private BigDecimal total;

    @Column(nullable = false)
    private BigDecimal troco;

    @OneToMany(mappedBy = "pedido")
    private List<ItemPedido> itemPedido = new ArrayList<>();

    public List<ItemPedido> getItemPedido() {
        return itemPedido;
    }

    public void setItensPedido(List<ItemPedido> itemPedido) {
        this.itemPedido = itemPedido;
    }

    private boolean status;

    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "pedido_id")
    )
    private List<FormaPagamento> formasPagamento = new ArrayList<>();

    @Column(nullable = false)
    private String nomeUsuario;

    @Column(nullable = false)
    @ColumnDefault("0")
    private Long caixaId;

    public Pedido() {
    }

    public Pedido(Long id, Long clienteId, LocalDateTime dataHora, BigDecimal total,
                  BigDecimal troco, boolean status, Produto produto, List<FormaPagamento> formasPagamento, List<ItemPedido> itens,
                String nomeUsuario) {
        this.id = id;
        this.clienteId = clienteId;
        this.dataHora = dataHora;
        this.total = total;
        this.troco = troco;
        this.status = status;
        this.formasPagamento = formasPagamento;
        this.itemPedido = itens;
        this.nomeUsuario = nomeUsuario;
    }

    @PrePersist
    public void prePersist() {
        this.status = true;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getTroco() {
        return troco;
    }

    public void setTroco(BigDecimal troco) {
        this.troco = troco;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<FormaPagamento> getFormasPagamento() {
        return formasPagamento;
    }

    public void setFormasPagamento(List<FormaPagamento> formasPagamento) {
        this.formasPagamento = formasPagamento;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public void setItemPedido(List<ItemPedido> itemPedido) {
        this.itemPedido = itemPedido;
    }

    public Long getCaixaId() {
        return caixaId;
    }

    public void setCaixaId(Long caixaId) {
        this.caixaId = caixaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return status == pedido.status && Objects.equals(id, pedido.id) && Objects.equals(clienteId, pedido.clienteId)  &&
                Objects.equals(dataHora, pedido.dataHora) &&
                Objects.equals(total, pedido.total) && Objects.equals(troco, pedido.troco) &&
                Objects.equals(formasPagamento, pedido.formasPagamento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, clienteId, dataHora, total, troco, status, formasPagamento);
    }

    @Override
    public String toString() {
        return "Venda{" +
                "id=" + id +
                ", clienteId=" + clienteId +
                ", itensId=" + itemPedido +
                ", dataHora=" + dataHora +
                ", total=" + total +
                ", troco=" + troco +
                ", status=" + status +
                ", formasPagamento=" + formasPagamento +
                '}';
    }
}

