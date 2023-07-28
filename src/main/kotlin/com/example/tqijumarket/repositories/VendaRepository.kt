package com.example.tqijumarket.repositories

import com.example.tqijumarket.entities.Venda
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VendaRepository : JpaRepository<Venda, Long>