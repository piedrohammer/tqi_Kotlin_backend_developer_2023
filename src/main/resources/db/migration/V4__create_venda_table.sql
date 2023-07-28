CREATE TABLE venda (
    id SERIAL PRIMARY KEY,
    preco_total DOUBLE NOT NULL,
    forma_pagamento VARCHAR(20) NOT NULL
);

ALTER TABLE carrinho ADD COLUMN venda_id BIGINT;