package com.example.tqijumarket.dtos

import com.example.tqijumarket.entities.Categoria

//(Data Transfer Object) -> transfêrencia de dados entre camadas de codigo para o melhor tratamento do código
data class ProdutoDTO(

    val nome: String,
    val unidadeMedida: String,
    val precoUnitario: Double,
    val categoria: Categoria,
    val id: Long

)