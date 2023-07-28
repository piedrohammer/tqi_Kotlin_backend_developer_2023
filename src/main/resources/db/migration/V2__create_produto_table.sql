CREATE TABLE produto (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(100) NOT NULL,
  unidade_medida VARCHAR(50) NOT NULL,
  preco_unitario DECIMAL(10, 2) NOT NULL,
  categoria_id BIGINT NOT NULL,
  FOREIGN KEY (categoria_id) REFERENCES categoria (id)
);