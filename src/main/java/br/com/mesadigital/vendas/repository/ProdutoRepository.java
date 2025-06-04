package br.com.mesadigital.vendas.repository;

import br.com.mesadigital.vendas.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByIdIn(List<Long> id);

    Optional<Produto> findByIdAndStatus(Long id, boolean status);

}
