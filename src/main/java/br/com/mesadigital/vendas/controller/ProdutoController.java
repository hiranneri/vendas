package br.com.mesadigital.vendas.controller;

import br.com.mesadigital.vendas.controller.dto.ProdutoDTO;
import br.com.mesadigital.vendas.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("produtos")
public class ProdutoController {

    ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public ResponseEntity<ProdutoDTO> criar(@RequestBody @Valid ProdutoDTO produtoDTO) {
        return new ResponseEntity<ProdutoDTO>(produtoService.criar(produtoDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> buscar(@PathVariable @Valid Long id) {
        return new ResponseEntity<ProdutoDTO>(produtoService.buscar(id), HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<ProdutoDTO> editar(@RequestBody @Valid ProdutoDTO produtoDTO) {
        return new ResponseEntity<ProdutoDTO>(produtoService.editar(produtoDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProdutoDTO> desativar(@PathVariable @Valid Long id) {
        produtoService.desativar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
