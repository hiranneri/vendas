package br.com.mesadigital.vendas.controller;

import br.com.mesadigital.vendas.controller.dto.PedidoDTO;
import br.com.mesadigital.vendas.controller.dto.PedidoPutDTO;
import br.com.mesadigital.vendas.service.PedidoService;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pedidos")
public class PedidoController {

    @Autowired
    PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoDTO> criar(@RequestBody @Valid PedidoDTO pedidoDTO) throws BadRequestException {
        return new ResponseEntity<PedidoDTO>(pedidoService.realizar(pedidoDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> buscar(@PathVariable @Valid Long id) {
        return new ResponseEntity<PedidoDTO>(pedidoService.buscar(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoDTO> alterar(@PathVariable @Valid Long id, @RequestBody @Valid PedidoPutDTO pedidoDTO) {
        pedidoDTO.setId(id);
        return new ResponseEntity<PedidoDTO>(pedidoService.alterar(pedidoDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PedidoDTO> desativar(@PathVariable @Valid Long id) {
        pedidoService.desativar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
