package com.example.tqijumarket.repositories

import com.example.tqijumarket.entities.Categoria
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CategoriaRepository : JpaRepository<Categoria, Long> {

    // Faz uma consulta personalizada para buscar categoria pelo nome
    @Query("SELECT c FROM Categoria c WHERE c.nome = :nome")
    fun findByNome(nome: String): Categoria?
}
