package br.com.mesadigital.vendas.repository;

import br.com.mesadigital.vendas.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    Optional<Pedido> findByIdAndStatus(Long id, boolean status);

    Optional<Pedido> findBydataHoraAndNomeUsuario(LocalDateTime dataHora, String usuario);

    List<Pedido> findByNomeUsuario(String nomeUsuario);
}
