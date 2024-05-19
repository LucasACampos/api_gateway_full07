create sequence PEDIDO_SEQ
START WITH 1
  INCREMENT BY 1
  MINVALUE 1;

create table pedido(
    id int8 primary key,
    numero int8 unique,
    cliente_id int8
);

create sequence PEDIDO_ITEM_SEQ
START WITH 1
  INCREMENT BY 1
  MINVALUE 1;

create table item_pedido(
    id int8 primary key,
    pedido_numero int8,
    indice int8,
    sku varchar(255),
    produto varchar(255),
    preco decimal(10, 2),
    quantidade int4,
    foreign key (pedido_numero) references pedido(numero)
);