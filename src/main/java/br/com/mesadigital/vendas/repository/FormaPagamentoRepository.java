package br.com.mesadigital.vendas.repository;

import br.com.mesadigital.vendas.model.FormaPagamento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FormaPagamentoRepository  {


    @PersistenceContext
    private EntityManager entityManager;


    public List<FormaPagamento> buscarFormasPagamentoComValores(List<String> nomes) {
        if (nomes.isEmpty()) {
            return List.of();
        }

        StringBuilder formasPagamentoNoPedido = new StringBuilder();

        for (int i = 0; i < nomes.size(); i++) {
            formasPagamentoNoPedido.append("('").append(nomes.get(i)).append("')");
            if (i < nomes.size() - 1) {
                formasPagamentoNoPedido.append(", ");
            }
        }

        String sql = String.format("""
            SELECT v.nome, f.id
            FROM (VALUES %s) AS v(nome)
            LEFT JOIN formaspagamento f ON f.nome = v.nome
            """, formasPagamentoNoPedido);

        return entityManager.createNativeQuery(sql, FormaPagamento.class).getResultList();
    }
}
