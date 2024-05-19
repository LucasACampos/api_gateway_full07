package com.example.ApiGatewayExercicio.repositories;

import com.example.ApiGatewayExercicio.entitys.ItemPedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoItemRepository extends JpaRepository<ItemPedido, Long> {

    Page<ItemPedido> findAllByPedidoNumero(Pageable pageable, Long pedidoNumero);
}
