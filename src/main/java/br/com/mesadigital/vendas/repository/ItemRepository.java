package br.com.mesadigital.vendas.repository;

import br.com.mesadigital.vendas.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<ItemPedido,Long> {




}
