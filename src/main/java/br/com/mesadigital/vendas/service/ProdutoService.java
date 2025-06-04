package br.com.mesadigital.vendas.service;


import br.com.mesadigital.vendas.controller.dto.ProdutoDTO;
import br.com.mesadigital.vendas.model.Produto;
import br.com.mesadigital.vendas.repository.ProdutoRepository;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ProdutoService {
    
    @Autowired
    ProdutoRepository produtoRepository;

    private static final boolean STATUS_ATIVO = true;
    private static final boolean STATUS_INATIVO = false;

    @Transactional
    public ProdutoDTO criar(ProdutoDTO produtoDTO) {
        Produto produto = new Produto();
        BeanUtils.copyProperties(produtoDTO,produto);
        produtoRepository.save(produto);

        produtoDTO.id = produto.getId();
        produtoDTO.status = produto.isStatus();
        return produtoDTO;
    }

    @Transactional
    public void desativar(Long id) throws BadRequestException {
        try {
            Produto produto = pesquisarProdutoPeloID(id);
            produto.setStatus(STATUS_INATIVO);
            produtoRepository.save(produto);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    public Produto pesquisarProdutoPeloID(Long id) throws BadRequestException {
        Produto produto;
        produto = produtoRepository.findByIdAndStatus(id, STATUS_ATIVO)
                .orElseThrow(() -> new BadRequestException("Produto não encontrado"));
        return produto;
    }

    public ProdutoDTO editar(ProdutoDTO produtoDTO) throws BadRequestException {
        try {
            Produto produtoLocalizado = pesquisarProdutoPeloID(produtoDTO.id);
            BeanUtils.copyProperties(produtoDTO, produtoLocalizado);
            produtoRepository.save(produtoLocalizado);

            return produtoDTO;
        } catch(Exception e) {
            throw new BadRequestException(e.getMessage());
        }

    }

    public ProdutoDTO buscar(@Valid Long idProduto) throws BadRequestException {
        try {
            Produto produtoLocalizado = pesquisarProdutoPeloID(idProduto);
            return new ProdutoDTO(
                    produtoLocalizado.getId(),
                    produtoLocalizado.getCodigoBarras(),
                    produtoLocalizado.isControlarEstoque(),
                    produtoLocalizado.getEstoqueAtual(),
                    produtoLocalizado.getMarca(),
                    produtoLocalizado.getNome(),
                    produtoLocalizado.getPrecoVenda(),
                    produtoLocalizado.getPrecoCusto(),
                    produtoLocalizado.getValidade(),
                    produtoLocalizado.isStatus()
            );
        }catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }

    }

}
