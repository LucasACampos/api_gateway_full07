package com.example.ApiGatewayExercicio.entitys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "ITEM_PEDIDO")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemPedido {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ITEM_PEDIDO_SEQ")
    @SequenceGenerator(name = "ITEM_PEDIDO_SEQ", allocationSize = 1)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @JoinColumn(
            name = "PEDIDO_NUMERO",
            referencedColumnName = "NUMERO"
    )
    @ManyToOne
    @JsonIgnore
    private Pedido pedido;

    @Column(name = "INDICE")
    private Long indice;

    @Column(name = "SKU")
    private String sku;

    @Column(name = "PRODUTO")
    private String produto;

    @Column(name = "PRECO")
    private BigDecimal preco;

    @Column(name = "QUANTIDADE")
    private Integer quantidade;

}
