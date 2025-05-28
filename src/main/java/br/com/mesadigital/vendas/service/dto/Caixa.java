package br.com.mesadigital.vendas.service.dto;

import java.io.Serial;
import java.io.Serializable;

public record Caixa(Long idCaixa, String nomeUsuario) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
}
