CREATE TABLE carrinho (
    id SERIAL PRIMARY KEY,
    produto_id BIGINT NOT NULL,
    quantidade INTEGER NOT NULL,
    preco_venda DOUBLE,
    FOREIGN KEY (produto_id) REFERENCES produto(id)
);