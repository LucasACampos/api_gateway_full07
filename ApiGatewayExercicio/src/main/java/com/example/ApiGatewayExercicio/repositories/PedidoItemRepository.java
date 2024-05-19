package com.example.ApiGatewayExercicio.repositories;

import com.example.ApiGatewayExercicio.entitys.PedidoItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoItemRepository extends JpaRepository<PedidoItem, Long> {
}
