package com.example.ApiGatewayExercicio.controllers;

import com.example.ApiGatewayExercicio.entitys.Pedido;
import com.example.ApiGatewayExercicio.entitys.ItemPedido;
import com.example.ApiGatewayExercicio.repositories.PedidoItemRepository;
import com.example.ApiGatewayExercicio.repositories.PedidoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class PedidoItemRestController {

    private final PedidoItemRepository pedidoItemRepository;
    private final PedidoRepository pedidoRepository;

    public PedidoItemRestController(
            PedidoItemRepository pedidoItemRepository,
            PedidoRepository pedidoRepository
    ) {
        this.pedidoItemRepository = pedidoItemRepository;
        this.pedidoRepository = pedidoRepository;
    }

    @PostMapping("/pedido/{pedidoId}/item")
    public ResponseEntity<ItemPedido> criarPedidoItem(
            @RequestBody ItemPedido itemPedido,
            @PathVariable Long pedidoId
    ) {
        itemPedido.setPedido(new Pedido(pedidoId));
        return ResponseEntity.ofNullable(pedidoItemRepository.save(itemPedido));
    }

    @GetMapping("/pedido/{pedidoId}/item")
    public ResponseEntity<Page<ItemPedido>> getPedidoItems(
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @PathVariable Long pedidoId
    ) {

        final PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);

        Optional<Pedido> pedido = pedidoRepository.findById(pedidoId);

        return pedido.map(itemPedido -> ResponseEntity.ofNullable(
                pedidoItemRepository.findAllByPedidoNumero(
                        pageRequest,
                        pedido.get().getNumero()
                )
        )).orElseGet(() -> ResponseEntity.noContent().build());
    }

}
