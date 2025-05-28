package br.com.mesadigital.vendas.controller.dto;

import jakarta.validation.constraints.Min;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ItemDTO {

    @Min(1)
    private Long idProduto;

    private String nome;

    @Min(1)
    private BigInteger quantidade = new BigInteger("0");

    @Min(0) // Possível promoção
    private BigDecimal valorTotalItem = new BigDecimal("0");

    public @Min(1) Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(@Min(1) Long idProduto) {
        this.idProduto = idProduto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Min(1)
    public BigInteger getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(@Min(1) BigInteger quantidade) {
        this.quantidade = quantidade;
    }

    @Min(0)
    public BigDecimal getValorTotalItem() {
        return valorTotalItem;
    }

    public void setValorTotalItem(@Min(0) BigDecimal valorTotalItem) {
        if(valorTotalItem == null) {
            valorTotalItem = new BigDecimal("0");
        }
        this.valorTotalItem = valorTotalItem;
    }

    public ItemDTO(Long idProduto, String nome, BigInteger quantidade, BigDecimal valorTotalItem) {
        this.idProduto = idProduto;
        this.nome = nome;
        this.quantidade = quantidade;

        if(valorTotalItem == null) {
            valorTotalItem = new BigDecimal("0");
        }

        this.valorTotalItem = valorTotalItem;
    }

    public ItemDTO(Long idProduto, String nome, BigInteger quantidade) {
        this.idProduto = idProduto;
        this.nome = nome;
        this.quantidade = quantidade;
    }

    public ItemDTO() {
    }
}
