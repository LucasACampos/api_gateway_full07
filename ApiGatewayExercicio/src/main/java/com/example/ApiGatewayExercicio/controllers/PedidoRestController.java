package com.example.ApiGatewayExercicio.controllers;

import com.example.ApiGatewayExercicio.entitys.Pedido;
import com.example.ApiGatewayExercicio.repositories.PedidoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class PedidoRestController {

    private final PedidoRepository pedidoRepository;

    public PedidoRestController(
            PedidoRepository pedidoRepository
    ) {
        this.pedidoRepository = pedidoRepository;
    }

    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<Pedido> getPedido(
            @PathVariable Long pedidoId
    ){
        Optional<Pedido> optionalPedido;
        try {
             optionalPedido = pedidoRepository.findById(pedidoId);
        }catch (Exception e){
            optionalPedido = Optional.empty();
        }

        return ResponseEntity.of(optionalPedido);
    }

    @PostMapping("/pedido")
    public ResponseEntity<Pedido> getPedido(
            @RequestBody Pedido pedido
    ){

        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        return ResponseEntity.of(Optional.of(pedidoSalvo));
    }

    @GetMapping("/pedido")
    public ResponseEntity<Page<Pedido>> getPedidoList(
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize
    ){

        PageRequest pageRequest = PageRequest.of(
                pageNumber,
                pageSize
        );

        Page<Pedido> pedidoPage = pedidoRepository.findAll(pageRequest);

        return ResponseEntity.ofNullable(pedidoPage);
    }

}
