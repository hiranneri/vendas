package br.com.mesadigital.vendas.controller.dto;

import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.NumberFormat;

public class PedidoPutDTO extends PedidoDTO {

    @NotBlank
    @NumberFormat
    Long id;

    public @NotBlank Long getId() {
        return id;
    }

    public void setId(@NotBlank Long id) {
        this.id = id;
    }
}
