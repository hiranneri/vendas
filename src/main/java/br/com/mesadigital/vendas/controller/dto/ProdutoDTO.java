package br.com.mesadigital.vendas.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;

public class ProdutoDTO {

    public Long id;

    @NotNull
    public String codigoBarras;

    public boolean controlarEstoque;

    @NotNull
    @NumberFormat
    public float estoqueAtual;
    public String marca;

    @NotBlank
    public String nome;

    @NumberFormat
    public BigDecimal precoVenda;

    @NumberFormat
    public BigDecimal precoCusto;

    @NotNull
    public String validade;

    public boolean status;

    public ProdutoDTO(Long id, String codigoBarras, boolean controlarEstoque, float estoqueAtual, String marca, String nome,
                      BigDecimal precoVenda, BigDecimal precoCusto, String validade, boolean status) {
        this.id = id;
        this.codigoBarras = codigoBarras;
        this.controlarEstoque = controlarEstoque;
        this.estoqueAtual = estoqueAtual;
        this.marca = marca;
        this.nome = nome;
        this.precoVenda = precoVenda;
        this.precoCusto = precoCusto;
        this.validade = validade;
        this.status = status;
    }

    public ProdutoDTO() {
    }


    public Long getId() {
        return id;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public boolean isControlarEstoque() {
        return controlarEstoque;
    }

    public float getEstoqueAtual() {
        return estoqueAtual;
    }

    public String getMarca() {
        return marca;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getPrecoVenda() {
        return precoVenda;
    }

    public BigDecimal getPrecoCusto() {
        return precoCusto;
    }

    public String getValidade() {
        return validade;
    }

    public boolean isStatus() {
        return status;
    }
}
