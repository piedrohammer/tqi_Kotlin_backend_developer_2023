package com.example.tqijumarket.repositories

import com.example.tqijumarket.entities.Produto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ProdutoRepository : JpaRepository<Produto, Long>{

    //Faz uma consulta personalizada para buscar um produto pelo nome
    @Query("SELECT c FROM Produto c WHERE c.nome = :nome")
    fun findByNome(nome: String): Produto?
}