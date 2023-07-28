package com.example.tqijumarket.dtos

//(Data Transfer Object) -> transfêrencia de dados entre camadas de codigo para o melhor tratamento do código
data class CarrinhoDTO(

    val produtoId: Long,
    val quantidade: Int,
    val precoVenda: Double

)
