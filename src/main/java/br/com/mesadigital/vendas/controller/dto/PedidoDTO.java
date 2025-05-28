package br.com.mesadigital.vendas.controller.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class PedidoDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long clienteId;

    @NotEmpty
    @Valid
    private List<ItemDTO> itens;

    @NotNull
    private LocalDateTime dataHora;

    @NotNull
    private BigDecimal total = new BigDecimal("0");

    @NotNull
    private List<FormaPagamentoEnum> formasPagamento;

    @NotNull
    private BigDecimal troco = new BigDecimal("0");

    private Long caixaId;

    @NotBlank
    private String nomeUsuario;

    private String status = "CRIADO";

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public @NotEmpty @Valid List<ItemDTO> getItens() {
        return itens;
    }

    public void setItens(@NotEmpty @Valid List<ItemDTO> itens) {
        this.itens = itens;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public @NotNull BigDecimal getTotal() {
        return total;
    }

    public void setTotal(@NotNull BigDecimal total) {
        this.total = total;
    }

    public @NotNull List<FormaPagamentoEnum> getFormasPagamento() {
        return formasPagamento;
    }

    public void setFormasPagamento(@NotNull List<FormaPagamentoEnum> formasPagamento) {
        this.formasPagamento = formasPagamento;
    }

    public @NotNull BigDecimal getTroco() {
        return troco;
    }

    public void setTroco(@NotNull BigDecimal troco) {
        this.troco = troco;
    }

    public Long getCaixaId() {
        return caixaId;
    }

    public void setCaixaId(@NotNull Long caixaId) {
        this.caixaId = caixaId;
    }

    public @NotBlank String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(@NotBlank String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PedidoDTO{" +
                "id=" + id +
                ", clienteId=" + clienteId +
                ", itens=" + itens +
                ", dataHora=" + dataHora +
                ", total=" + total +
                ", formasPagamento=" + formasPagamento +
                ", troco=" + troco +
                ", caixaId=" + caixaId +
                ", nomeUsuario='" + nomeUsuario + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

