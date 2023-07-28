package com.example.tqijumarket.entities

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.NotBlank
import org.jetbrains.annotations.NotNull

@Entity
data class Categoria(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)// -> ID gerado automaticamente
    @NotNull // -> Não pode ser nulo
    var id: Long = 0,

    @NotBlank // -> Não pode deixa em branco
    var nome: String


)
