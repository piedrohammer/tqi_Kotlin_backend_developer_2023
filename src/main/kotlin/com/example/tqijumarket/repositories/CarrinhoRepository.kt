package com.example.tqijumarket.repositories

import com.example.tqijumarket.entities.Carrinho
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CarrinhoRepository : JpaRepository<Carrinho, Long>